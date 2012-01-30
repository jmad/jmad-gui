// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on

package cern.accsoft.steering.jmad.kernel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.JMadException;
import cern.accsoft.steering.jmad.bin.MadxBin;
import cern.accsoft.steering.jmad.domain.result.Result;
import cern.accsoft.steering.jmad.domain.result.ResultType;
import cern.accsoft.steering.jmad.io.DynapOutputParser;
import cern.accsoft.steering.jmad.io.MatchOutputParser;
import cern.accsoft.steering.jmad.io.StrengthFileParser;
import cern.accsoft.steering.jmad.io.TfsFileParser;
import cern.accsoft.steering.jmad.io.TrackOutputParser;
import cern.accsoft.steering.jmad.util.FileMonitor;
import cern.accsoft.steering.jmad.util.JMadPreferences;
import cern.accsoft.steering.jmad.util.ProcTools;
import cern.accsoft.steering.jmad.util.ProcessTerminationMonitor;
import cern.accsoft.steering.jmad.util.StreamLogger;
import cern.accsoft.steering.jmad.util.TempFileUtil;

/**
 * this is the implementation of the {@link JMadKernel} which controls one MadX-Process.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class JMadKernelImpl implements JMadKernel, JMadKernelConfig {

    /** the logger for the class */
    private static final Logger LOGGER = Logger.getLogger(JMadKernelImpl.class);

    /**
     * This value is returned by the waitUntilReady - method. It is only used internally.
     */
    private static final int EXIT_VALUE_DESTROYED = -9999;

    /** the command which is used to stop madx. */
    private static final String CMD_STOP = "stop;";

    /*
     * various file names which are used to communicate with MadX and for logging
     */
    private static final String FILENAME_READY = "madx-ready.out";
    private static final String FILENAME_RESULT = "madx-result.out";
    private static final String FILENAME_LOG_IN = "madx-input.log";
    private static final String FILENAME_LOG_OUT = "madx-output.log";
    private static final String FILENAME_LOG_ERROR = "madx-error.log";

    private File readyFile = null;
    private File resultFile = null;

    /** wait this amount of ms, when deletion failed and retry */
    private static final int RETRY_DELAY = 100;

    /** how often to retry deleting a file before throwing an exception? */
    private static final int RETRY_ATTEMPTS = 3;

    /**
     * the (optional) timeout in ms which the kernel waits until Madx finishes the command/task. If the timeout is set
     * to null, then the kernel waits forever. Null is the default.
     */
    private Long timeout = null;

    /** the process for madx */
    private Process process = null;

    /** the stream for providing madx with input */
    private PrintWriter input = null;

    /** the logger, where the inputs to madx are logged */
    private BufferedWriter inputLogWriter = null;

    /** The preferences to be injected */
    private JMadPreferences preferences;

    /** the file util to be injected */
    private TempFileUtil fileUtil;

    /** the class which takes care of the madx-binary */
    private MadxBin madxBin;

    /**
     * does not delete the outputFile after executing a command/task. Keeping the output-file is especially useful for
     * debugging.
     */
    private boolean keepOutputFile = true;

    /**
     * if true, then the temp dirs are deleted when cleaning up, if false they are kept. If this is null (default) then
     * the settings are taken from the preferences.
     */
    private Boolean cleanupDirs = null;

    /** the listeners to the kernel */
    private final List<JMadKernelListener> listeners = new ArrayList<JMadKernelListener>();

    @Override
    public void start() throws JMadException {

        if (getFileUtil() == null) {
            throw new JMadException("File util not configured. Cannot proceed.");
        }
        /*
         * First we have to get temp-files related to this kernel
         */
        readyFile = getFileUtil().getOutputFile(this, FILENAME_READY);
        resultFile = getFileUtil().getOutputFile(this, FILENAME_RESULT);
        File madxInputLogFile = getFileUtil().getOutputFile(this, FILENAME_LOG_IN);
        File madxOutputLogFile = getFileUtil().getOutputFile(this, FILENAME_LOG_OUT);
        File madxErrorLogFile = getFileUtil().getOutputFile(this, FILENAME_LOG_ERROR);

        if (!readyFile.delete()) {
            /* ignore, may not exist */
        }

        if (!madxInputLogFile.delete()) {
            /* ignore, may not exist */
        }

        try {
            MadxBin madxBinary = getMadxBin();
            process = madxBinary.execute();

            StreamLogger outputLogger = new StreamLogger(process.getInputStream(), madxOutputLogFile);
            outputLogger.start();

            StreamLogger errorLogger = new StreamLogger(process.getErrorStream(), madxErrorLogFile);
            errorLogger.start();

            input = new PrintWriter(process.getOutputStream());
            inputLogWriter = new BufferedWriter(new FileWriter(madxInputLogFile));
            fireStartedKernel();
        } catch (IOException e) {
            throw new JMadException("Error while executing madx.", e);
        }
    }

    @Override
    public int stop() throws JMadException {
        int exitValue = 0;
        writeCommand(CMD_STOP);
        try {
            if (timeout == null) {
                LOGGER.debug("No timeout set. Waiting until madx-process terminates.");
                exitValue = process.waitFor();

            } else {
                ProcessTerminationMonitor processTerminationMonitor = new ProcessTerminationMonitor(process);
                processTerminationMonitor.start();
                processTerminationMonitor.join(timeout);
                if (processTerminationMonitor.isAlive()) {
                    processTerminationMonitor.interrupt();
                    process.destroy();
                    exitValue = EXIT_VALUE_DESTROYED;
                    LOGGER.warn("Waiting for terminating madx timed out! (timeout=" + timeout + "ms).");
                } else {
                    exitValue = process.exitValue();
                }
            }

            if (exitValue == 0) {
                LOGGER.debug("madx terminated correctly with exit-value " + exitValue + ".");
            } else if (exitValue == EXIT_VALUE_DESTROYED) {
                LOGGER.debug("Tried to destroy madx-process. -> set exitValue to " + exitValue);
            } else {
                LOGGER.warn("madx terminated with exit-value " + exitValue + ".");
            }

            if (!readyFile.delete()) {
                /* ignore, may not exist. */
            }
            inputLogWriter.flush();
            inputLogWriter.close();
        } catch (InterruptedException e) {
            throw new JMadException("Error while trying to stop MadX", e);
        } catch (IOException e) {
            throw new JMadException("Error while logging commands.", e);
        }

        /* delete the dir corresponding to the kernel. */
        if (isCleanupDirs() && (getFileUtil() != null)) {
            getFileUtil().cleanup(this);
        }

        fireStoppedKernel();
        return exitValue;
    }

    @Override
    public Result execute(JMadExecutable executable) throws JMadException {
        if (!resultFile.delete()) {
            /* ignore, may not exist. */
        }
        executable.setOutputFile(resultFile);

        /* execute the commands and wait. */
        writeCommand(executable.compose());
        boolean timedout = waitUntilReady();

        if (timedout) {
            throw new JMadException("Waiting for madx timed out!");
        }

        /* parse result */
        Result result = null;
        if ((executable.getResultType() != null) && (ResultType.NO_RESULT != executable.getResultType())) {

            LOGGER.debug("parsing madx output-file (" + resultFile.getAbsolutePath() + ")");

            // TODO associate a parser to a type, then the all if else become
            // Parser parser = executable.getResultType().getParser().parse(executable.getOutputFile());
            // result = parser.getResult();
            // ???

            if (ResultType.TFS_RESULT == executable.getResultType()) {
                TfsFileParser parser = new TfsFileParser(resultFile);
                parser.parse();
                result = parser.getResult();
            } else if (ResultType.VALUES_RESULT == executable.getResultType()) {
                StrengthFileParser parser = new StrengthFileParser(resultFile);
                parser.parse();
                result = parser.getResult();
            } else if (ResultType.MATCH_RESULT == executable.getResultType()) {
                MatchOutputParser parser = new MatchOutputParser(executable.getOutputFile());
                parser.parse();
                result = parser.getResult();
            } else if (ResultType.TRACK_RESULT == executable.getResultType()) {
                TrackOutputParser parser = new TrackOutputParser(executable.getOutputFile());
                parser.parse();
                result = parser.getResult();
            } else if (ResultType.DYNAP_RESULT == executable.getResultType()) {
                DynapOutputParser parser = new DynapOutputParser(executable.getOutputFile());
                parser.parse();
                result = parser.getResult();
            }

            if (!this.keepOutputFile) {
                if (!resultFile.delete()) {
                    throw new JMadException("Could not delet Result-file '" + resultFile.getAbsolutePath());
                }
                LOGGER.debug(("deleted madx output-file (" + resultFile.getAbsolutePath() + ")"));
            }
        }

        return result;
    }

    /**
     * writes the command(s) as String to MadX-input. This method does not wait for the end of the execution and does
     * not return any result. Use with care!
     * 
     * @param command the command to be executed by madx
     * @throws JMadException
     */
    /* package visibility for testing! */
    void writeCommand(String command) throws JMadException { // NOPMD by kaifox on 6/25/10 4:01 PM
        String commandString = command + "\n";
        if (!isMadxRunning()) {
            throw new JMadException("MadX is not running -> cannot write commands.");
        }

        LOGGER.debug("writing command(s) to madx:\n" + commandString);
        input.println(commandString);
        input.flush();

        /* also log in separate file for simple executing in madx */
        try {
            inputLogWriter.write(commandString);
        } catch (IOException e) {
            LOGGER.warn("Error while logging commands!", e);
        }
    }

    @Override
    public boolean isMadxRunning() {
        return ProcTools.isRunning(process);
    }

    /**
     * writes a file through madx and waits until it exists or reaching timeout (if set).
     * 
     * @return <code>true</code>, if waiting timed out, false if not
     * @see #getTimeout()
     * @see #setTimeout(Long)
     * @throws JMadException
     */
    /* package visibility for testing! */
    boolean waitUntilReady() throws JMadException { // NOPMD by kaifox on 6/25/10 4:01 PM
        boolean timedout = false;
        if (!isMadxRunning()) {
            LOGGER.warn("MadX is not running!");
            return timedout;
        }

        writeCommand("\nsystem, \"echo > " + readyFile.getAbsolutePath() + "\"; // wait until ready\n");

        /* wait for the file, which tells us, that madx finished */
        FileMonitor fileMonitor = new FileMonitor(readyFile, process);
        boolean fileCreated = fileMonitor.waitForFile(timeout);

        if (!fileCreated) {
            timedout = true;
            LOGGER.warn("Madx-command timed out! (timeout=" + timeout + "ms).");
        }

        if ((!readyFile.delete()) && (!timedout)) {
            boolean deleted = false;
            for (int i = 0; i < RETRY_ATTEMPTS; i++) {
                LOGGER.warn("deletion of file '" + readyFile.getAbsolutePath() + "' failed. Retrying again in "
                        + RETRY_DELAY + "ms");
                try {
                    Thread.sleep(RETRY_DELAY);
                } catch (InterruptedException e) {
                    LOGGER.error("Error while waiting for retry ...", e);
                }
                if (readyFile.delete()) {
                    deleted = true;
                    break;
                }
            }
            if (!deleted) {
                throw new JMadException("error while deleting file '" + readyFile.getAbsolutePath() + "'");
            }
        }
        return timedout;
    }

    /**
     * When the class is destroyed it takes care, that MadX is closed in a proper way.
     * 
     * @throws Throwable if the finalization fails
     */
    @Override
    protected void finalize() throws Throwable {
        LOGGER.debug(this.getClass() + " was garbage-collected.");
        if (isMadxRunning()) {
            LOGGER.warn("Madx is still running! - trying to stop...");
            try {
                stop();
            } catch (JMadException e) {
                LOGGER.error("Error while trying to stop MadX.", e);
            }
        }
        super.finalize();
    }

    /*
     * (non-Javadoc)
     * 
     * @see cern.accsoft.steering.jmad.kernel.JMadKernel#addListener(cern.accsoft
     * .steering.jmad.kernel.MadXKernelListener)
     */
    @Override
    public void addListener(JMadKernelListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void removeListener(JMadKernelListener listener) {
        this.listeners.remove(listener);
    }

    /**
     * notifies the listeners, that the kernel has started
     */
    private void fireStartedKernel() {
        for (JMadKernelListener listener : this.listeners) {
            listener.startedKernel(this.process);
        }
    }

    /**
     * notifies all listeners, that the kernel has stopped.
     */
    private void fireStoppedKernel() {
        for (JMadKernelListener listener : this.listeners) {
            listener.stoppedKernel();
        }
    }

    //
    // Methods which allow special configuration
    //

    /**
     * @return the actual set timeout
     */
    @Override
    public Long getTimeout() {
        return timeout;
    }

    /**
     * sets the actual timeout in ms. If null then the kernel waits forever for Madx.
     * 
     * @param timeout the timeout to set
     */
    @Override
    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    /**
     * sets the flag, if the output-file shall be kept or deleted after task/command execution.
     * 
     * @param keepOutputFile true if the output-file shall be kept, false otherwise
     */
    @Override
    public void setKeepOutputFile(boolean keepOutputFile) {
        this.keepOutputFile = keepOutputFile;
    }

    /**
     * @return true, if the outputfile shall be kept or false if it shall be deleted after command/task execution.
     */
    @Override
    public boolean isKeepOutputFile() {
        return keepOutputFile;
    }

    @Override
    public void setCleanupDirs(boolean cleanupDirs) {
        this.cleanupDirs = cleanupDirs;
    }

    @Override
    public boolean isCleanupDirs() {
        if (this.cleanupDirs != null) {
            return cleanupDirs;
        }
        return getPreferences().isCleanupKernelFiles();
    }

    @Override
    public File getOutputFile() {
        return this.resultFile;
    }

    public void setPreferences(JMadPreferences preferences) {
        this.preferences = preferences;
    }

    private JMadPreferences getPreferences() {
        if (this.preferences == null) {
            LOGGER.warn("Preferences not set. Maybe config error.");
        }
        return preferences;
    }

    public void setFileUtil(TempFileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    private TempFileUtil getFileUtil() {
        if (this.fileUtil == null) {
            LOGGER.warn("FileUtil not set. Maybe config error.");
        }
        return fileUtil;
    }

    public void setMadxBin(MadxBin madxBin) {
        this.madxBin = madxBin;
    }

    private MadxBin getMadxBin() {
        if (this.madxBin == null) {
            LOGGER.warn("madxBin not set. Maybe config error.");
        }
        return madxBin;
    }
}

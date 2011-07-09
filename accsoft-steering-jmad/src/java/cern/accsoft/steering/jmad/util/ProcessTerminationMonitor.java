package cern.accsoft.steering.jmad.util;

import org.apache.log4j.Logger;

public class ProcessTerminationMonitor extends Thread {
    private static final Logger LOGGER = Logger.getLogger(ProcessTerminationMonitor.class);
    private static final long POLL_INTERVALL = 10;

    private final Process process;

    public ProcessTerminationMonitor(Process process) {
        this.process = process;
    }

    @Override
    public void run() {
        while (ProcTools.isRunning(process)) {
            try {
                sleep(POLL_INTERVALL);
            } catch (InterruptedException e) {
                LOGGER.warn("Waiting for terminating process '" + process.toString() + "' was interrupted.", e);
            }
        }
    }

}

/**
 * 
 */
package cern.accsoft.steering.jmad.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

/**
 * @author kfuchsbe
 */
public final class StreamUtil {

    /** The logger for the class */
    private static final Logger LOGGER = Logger.getLogger(StreamUtil.class);

    /**
     * private constructor to prevent instantiation
     */
    private StreamUtil() {
        /* Only static methods */
    }

    /**
     * copies all data from the input stream to the output stream. The output stream is flushed afterwards. None of the
     * streams is opened or closed.
     * 
     * @param inputStream the stream from which to copy the data
     * @param outputStream the stream to copy the data to
     * @throws IOException if something goes wrong.
     */
    public static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        BufferedInputStream instream = new BufferedInputStream(inputStream);
        BufferedOutputStream outstream = new BufferedOutputStream(outputStream);
        while (true) {
            int readValue = instream.read();
            if (readValue == -1) {
                break;
            }
            outstream.write(readValue);
        }
        outstream.flush();
    }

    /**
     * ensures, that the given input stream is extracted to the given file. If the file already exists it is
     * overwritten!
     * 
     * @param inputStream the stream from which to read
     * @param destinationFile the file where the resource shall be extracted to
     * @return true if successful, false if not
     */
    public static final boolean toFile(InputStream inputStream, File destinationFile) {

        if ((destinationFile == null) || (inputStream == null)) {
            return false;
        }

        /* make sure, that we use the actual file */
        boolean existed = destinationFile.exists();
        boolean deleted = destinationFile.delete();
        if (existed && (!deleted)) {
            LOGGER.debug("File '" + destinationFile.getAbsolutePath()
                    + "' already exists, but cannot be deleted. The existing version will be kept.");
            return true;
        }

        boolean success = false;

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(destinationFile);
            StreamUtil.copy(inputStream, outputStream);
            success = true;
        } catch (IOException e) {
            LOGGER.error("Error while copying stream to  file '" + destinationFile.getAbsolutePath() + "'.", e);
        } finally {
            try {
                inputStream.close();
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                LOGGER.error("Error while closing streams after copying stream to file.", e);
                success = false;
            }
        }
        return success;
    }

}

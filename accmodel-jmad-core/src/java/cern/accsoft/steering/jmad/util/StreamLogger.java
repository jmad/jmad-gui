package cern.accsoft.steering.jmad.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class StreamLogger extends Thread {
    private static final Logger LOGGER = Logger.getLogger(StreamLogger.class);

    private final InputStream inputStream;
    private final File file;

    public StreamLogger(InputStream inputStream, File file) {
        this.inputStream = inputStream;
        this.file = file;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                writer.write(line + "\n");
            }

            writer.flush();
            writer.close();

        } catch (IOException e) {
            LOGGER.error("Error while logging Stream.", e);
        }
    }
}

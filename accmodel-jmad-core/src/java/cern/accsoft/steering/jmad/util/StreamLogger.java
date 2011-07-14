// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 * 
 * JMad is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JMad is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JMad.  If not, see <http://www.gnu.org/licenses/>.
 * 
 ******************************************************************************/
// @formatter:on
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

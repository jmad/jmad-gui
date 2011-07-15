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

package cern.accsoft.steering.jmad.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TestFile {
    private File file = null;

    public TestFile(String fileName) {
        this.file = new File(fileName);
    }

    public void write(String text) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(text);
            bufferedWriter.flush();
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getFile() {
        return file;
    }

    public void delete() {
        if ((file != null) && (!file.delete())) {
            ; /* ignore, may not exist */
        }
    }
}

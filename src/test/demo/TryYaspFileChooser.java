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

package demo;

import java.io.File;

import javax.swing.JFileChooser;

import cern.accsoft.steering.util.meas.yasp.browse.YaspFileChooser;

public class TryYaspFileChooser {

    /**
     * use args[0] as the current path
     * 
     * @param args
     */
    public static void main(String[] args) {
        JFileChooser fileChooser = new YaspFileChooser();

        if (args.length > 0) {
            fileChooser.setCurrentDirectory(new File(args[0]));
        }
        fileChooser.setMultiSelectionEnabled(true);

        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File[] files = fileChooser.getSelectedFiles();
            System.out.println("Selected files:");
            System.out.println("---------------");
            for (File file : files) {
                System.out.println(file.getAbsolutePath());
            }
        } else {
            System.out.println("File selection aborted.");
        }

    }

}

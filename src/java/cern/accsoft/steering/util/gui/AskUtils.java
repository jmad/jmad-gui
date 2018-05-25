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

package cern.accsoft.steering.util.gui;

import javax.swing.JOptionPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Some util-methods for simple user interaction.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class AskUtils {
    private final static Logger logger = LoggerFactory.getLogger(AskUtils.class);

    /**
     * private constructor, no instantiation
     */
    private AskUtils() {
        // only static methods
    }

    /**
     * a rather simple method to ask the user for a double value
     * 
     * @param message
     * @param initValue
     * @return
     */
    public final static Double askDouble(String message, Double initValue) {
        String input = JOptionPane.showInputDialog(message, initValue);
        try {
            Double doubleValue = Double.parseDouble(input);
            return doubleValue;
        } catch (NumberFormatException e) {
            logger.warn("Could not convert user input '" + input + "' to a double value!");
            return null;
        }
    }

}

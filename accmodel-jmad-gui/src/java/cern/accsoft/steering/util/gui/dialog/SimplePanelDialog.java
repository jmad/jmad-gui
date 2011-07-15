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

package cern.accsoft.steering.util.gui.dialog;

import javax.swing.JDialog;
import javax.swing.JPanel;

public class SimplePanelDialog extends JDialog {
    private static final long serialVersionUID = 1695446208261983899L;

    /**
     * intit method for spring
     */
    public void init() {
        pack();
    }

    /**
     * sets the panel as content
     * 
     * @param panel
     */
    public void setPanel(JPanel panel) {
        this.getContentPane().removeAll();
        this.getContentPane().add(panel);
    }
}

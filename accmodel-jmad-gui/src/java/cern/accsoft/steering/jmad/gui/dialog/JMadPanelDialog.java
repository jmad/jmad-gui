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

package cern.accsoft.steering.jmad.gui.dialog;

import cern.accsoft.steering.jmad.gui.icons.Icon;
import cern.accsoft.steering.util.gui.dialog.SimplePanelDialog;

/**
 * The same as a panel - dialog, but has the MADX icon on top.
 * 
 * @author kfuchsbe
 */
public class JMadPanelDialog extends SimplePanelDialog {
    private static final long serialVersionUID = -4191349467425767648L;

    public JMadPanelDialog() {
        super();
        setIconImage(Icon.MADX.getImageIcon().getImage());
    }
}

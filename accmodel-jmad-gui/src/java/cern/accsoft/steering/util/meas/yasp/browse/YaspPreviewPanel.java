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

package cern.accsoft.steering.util.meas.yasp.browse;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * displays the data contained in a yasp file.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class YaspPreviewPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /** The panel to display the orbits */
    private DataViewerPanel dvPanel = new DataViewerPanel();

    /** The panel to display the header data of a yasp file */
    private YaspHeaderPanel headerPanel = new YaspHeaderPanel();

    /**
     * the constructor, which simply initializes all the components
     */
    public YaspPreviewPanel() {
        initComponenets();
    }

    /**
     * creates all the components and places them in the panel
     */
    private void initComponenets() {
        setLayout(new BorderLayout());

        JTabbedPane tabPane = new JTabbedPane();
        add(tabPane, BorderLayout.CENTER);

        tabPane.addTab("data", dvPanel);
        tabPane.addTab("header", headerPanel);
    }

}

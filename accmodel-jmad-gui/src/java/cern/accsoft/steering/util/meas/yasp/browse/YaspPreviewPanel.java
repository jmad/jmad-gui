// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, Kajetan Fuchsberger. All rights reserved.
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
package cern.accsoft.steering.util.meas.yasp.browse;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * displays the data contained in a yasp file.
 * 
 * @author kaifox
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

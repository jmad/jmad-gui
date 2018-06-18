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

package cern.accsoft.steering.jmad.gui.panels;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.BevelBorder;

import static java.util.Objects.requireNonNull;

public class MainPanel extends JPanel {
    private static final long serialVersionUID = -8292677890152652172L;

    private JPanel modelOperationPanel;
    private JPanel dataViewerPanel;
    private JPanel outputPanel;
        private JPanel modelManagerPanel;

    public void init() {
        requireNonNull(modelOperationPanel, "modelOperationPanel cannot be null. Configuration problem");
        requireNonNull(dataViewerPanel, "dataViewerPanel cannot be null. Configuration problem");
        requireNonNull(outputPanel, "outputPanel cannot be null. Configuration problem");
        requireNonNull(modelManagerPanel, "modelManagerPanel cannot be null. Configuration problem");


        JPanel modelManagerBox = new JPanel(new BorderLayout());
        modelManagerBox.setBorder(new BevelBorder(BevelBorder.LOWERED));
        modelManagerBox.add(new JLabel("Models"), BorderLayout.NORTH);
        modelManagerBox.add(modelManagerPanel, BorderLayout.CENTER);

        modelOperationPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));

        dataViewerPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));

        JPanel outputBox = new JPanel(new BorderLayout());
        outputBox.setBorder(new BevelBorder(BevelBorder.LOWERED));
        outputBox.add(new JLabel("Output"), BorderLayout.NORTH);
        outputBox.add(outputPanel, BorderLayout.CENTER);

        JSplitPane topTwoLeftPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, modelManagerBox, modelOperationPanel);
        JSplitPane topPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, topTwoLeftPane, outputBox);
        JSplitPane fullPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPane, dataViewerPanel);

        setLayout(new BorderLayout());
        add(fullPane);
        validate();

        /* Weird Swing magic numbers */
        topTwoLeftPane.setDividerLocation(0.35);
        topTwoLeftPane.setResizeWeight(0.35);
        topPane.setDividerLocation(0.66);
        topPane.setResizeWeight(0.66);

        fullPane.setDividerLocation(0.5);
        fullPane.setResizeWeight(0.5);
    }

    public final void setModelOperationPanel(JPanel modelOperationPanel) {
        this.modelOperationPanel = modelOperationPanel;
    }

    public final void setDataViewerPanel(JPanel dataViewerPanel) {
        this.dataViewerPanel = dataViewerPanel;
    }

    public void setOutputPanel(JPanel outputPanel) {
        this.outputPanel = outputPanel;
    }

    public void setModelManagerPanel(JPanel modelManagerPanel) {
        this.modelManagerPanel = modelManagerPanel;
    }

}

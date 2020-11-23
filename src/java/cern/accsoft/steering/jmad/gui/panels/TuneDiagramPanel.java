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

import java.awt.*;
import java.util.Optional;

import cern.accsoft.steering.jmad.domain.result.tfs.TfsResult;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsSummary;
import cern.accsoft.steering.jmad.domain.var.enums.MadxGlobalVariable;
import cern.accsoft.steering.util.gui.dv.chart.TuneDiagramChart;

/**
 * listens to the TfsResultManager and displays the tune-values in the tune diagram, if the result is updated.
 *
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class TuneDiagramPanel extends AbstractTfsDataSetManagerResultPanel {
    private static final long serialVersionUID = 1L;

    /**
     * The special chart for the tune diagram
     */
    private TuneDiagramChart tuneDiagramChart;

    /**
     * init method called by spring
     */
    public void init() {
        initComponents();
    }

    /**
     * creates all the components
     */
    private void initComponents() {
        setLayout(new BorderLayout());
        this.tuneDiagramChart = new TuneDiagramChart(10);
        add(this.tuneDiagramChart, BorderLayout.CENTER);
    }

    @Override
    protected void update(TfsResult tfsResult) {
        TfsSummary tfsSummary = tfsResult.getSummary();

        double qX = Optional.ofNullable(tfsSummary.getDoubleValue(MadxGlobalVariable.Q1)).orElse(0.0);
        double qY = Optional.ofNullable(tfsSummary.getDoubleValue(MadxGlobalVariable.Q2)).orElse(0.0);

        this.tuneDiagramChart.setValues(qX, qY);
    }

}

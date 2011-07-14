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
package cern.accsoft.steering.jmad.gui.panels;

import java.awt.BorderLayout;

import cern.accsoft.steering.jmad.domain.result.tfs.TfsResult;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsSummary;
import cern.accsoft.steering.jmad.domain.var.enums.MadxGlobalVariable;
import cern.accsoft.steering.util.gui.dv.chart.TuneDiagramChart;

/**
 * listens to the TfsResultManager and displays the tune-values in the tune diagram, if the result is updated.
 * 
 * @author kfuchsbe
 */
public class TuneDiagramPanel extends AbstractTfsDataSetManagerResultPanel {
    private static final long serialVersionUID = 1L;

    /** The special chart for the tune diagram */
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
        
        double qX = tfsSummary.getDoubleValue(MadxGlobalVariable.Q1);
        double qY = tfsSummary.getDoubleValue(MadxGlobalVariable.Q2);

        this.tuneDiagramChart.setValues(qX, qY);
    }

}

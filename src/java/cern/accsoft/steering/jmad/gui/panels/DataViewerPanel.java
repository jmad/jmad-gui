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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;

import cern.accsoft.steering.jmad.domain.result.tfs.TfsResult;
import cern.accsoft.steering.jmad.gui.actions.JMadGuiActions;
import cern.accsoft.steering.jmad.gui.data.TfsResultDataSet;
import cern.accsoft.steering.jmad.gui.dv.ChartFactory;
import cern.accsoft.steering.jmad.gui.icons.Icon;
import cern.accsoft.steering.jmad.gui.manage.DataSetManagerListener;
import cern.accsoft.steering.jmad.gui.manage.TfsDataSetManager;
import cern.accsoft.steering.util.gui.UserInteractor;
import cern.jdve.Chart;
import cern.jdve.Style;
import cern.jdve.data.DataSet;
import cern.jdve.data.DataSource;
import cern.jdve.data.DefaultDataSource;
import cern.jdve.renderer.StairsChartRenderer;
import cern.jdve.viewer.DVView;
import cern.jdve.viewer.DataView;
import cern.jdve.viewer.DataViewer;

public class DataViewerPanel extends JPanel implements DataSetManagerListener {
    private final static Dimension PREFERRED_SIZE_DV = new Dimension(800, 300);

    private static final long serialVersionUID = 1L;

    /** the integrated dataViewer */
    private DataViewer dataViewer = null;

    /** the dataset - manager */
    private TfsDataSetManager tfsDataSetManager = null;

    /** the class which provides factory methods for charts. */
    private ChartFactory chartFactory;

    /** the panel which is used to create plots */
    private PlotCreationPanel plotCreationPanel;

    /** the user interactor to show the dialog */
    private UserInteractor userInteractor;

    private JMadGuiActions jmadGuiActions;

    /**
     * the action to show the dialog for creating new plots
     */
    private final Action createPlotAction = new AbstractAction("Add view") {
        private static final long serialVersionUID = 1L;

        {
            setToolTipText("Create a new chart-view.");
            putValue(AbstractAction.SMALL_ICON, Icon.CHART.getImageIcon());
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            getUserInteractor().showPanelDialog(getPlotCreationPanel(), DataViewerPanel.this);
        }
    };

    /**
     * creates all contained components
     */
    public void initComponents() {
        setLayout(new BorderLayout());

        /*
         * The DataViewer
         */
        dataViewer = new DataViewer();
        //dataViewer.setPreferredSize(PREFERRED_SIZE_DV);
        dataViewer.setExplorerVisible(true);
        add(dataViewer, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        add(buttonPanel, BorderLayout.SOUTH);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.weightx = 0;
        constraints.weighty = 1;

        JButton btn = new JButton(this.createPlotAction);
        buttonPanel.add(btn, constraints);

        /*
         * the refresh - button
         */
        btn = new JButton(new AbstractAction("Refresh all views") {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (tfsDataSetManager != null) {
                    tfsDataSetManager.refresh();
                }
            }
        });

        constraints.weightx = 1;
        constraints.gridx++;
        buttonPanel.add(btn, constraints);

        btn = new JButton(new AbstractAction(" >>> ref ") {
            private static final long serialVersionUID = 9180066463817578987L;
            {
                putValue(AbstractAction.SHORT_DESCRIPTION,
                        "Stores the actual available data as reference for relative charts.");
            }

            @Override
            public void actionPerformed(ActionEvent arg0) {
                getTfsDataSetManager().setAsReference();
            }
        });

        constraints.weightx = 0;
        constraints.gridx++;
        buttonPanel.add(btn, constraints);

        JButton saveTwissButton = new JButton(jmadGuiActions.getSaveTwissAction());
        constraints.weightx = 0;
        constraints.gridx++;
        buttonPanel.add(saveTwissButton, constraints);

    }

    /**
     * creates a new chart and adds it to the dataviewer.
     * 
     * @param name the name of the new chart
     * @param xLabel
     * @param dataSets
     */
    private void createChart(String name, String xLabel, Map<Integer, List<TfsResultDataSet>> dataSets) {
        if (getChartFactory() == null) {
            return;
        }

        Chart chart = null;
        int axisCount = 0;
        for (Integer yAxis : dataSets.keySet()) {

            List<TfsResultDataSet> aperDataSets = new ArrayList<TfsResultDataSet>();
            DataSource dataSource = new DefaultDataSource();
            for (TfsResultDataSet dataSet : dataSets.get(yAxis)) {
                if (dataSet.isApertureDataSet()) {
                    aperDataSets.add(dataSet);
                } else {
                    dataSource.addDataSet(dataSet);
                }
            }

            String yAxisTitle = "Y" + new Integer(yAxis + 1).toString();

            if (chart == null) {
                chart = getChartFactory().createMarkablePolyLineChart(dataSource);
                chart.setName(name);
                chart.getXScale()
                        .setTitle(xLabel);
                chart.getYScale()
                        .setTitle(yAxisTitle);
            } else {
                chart.addYAxis(true, false);
                chart.getYScale(axisCount)
                        .setTitle(yAxisTitle);
                chart.getYScale(axisCount)
                        .synchronizeWith(chart.getYScale(), true);
                chart.addRenderer(axisCount, getChartFactory().createPolyLineRenderer(dataSource));
            }

            if (aperDataSets.size() > 0) {
                DataSource aperDataSource = new DefaultDataSource(
                        aperDataSets.toArray(new DataSet[aperDataSets.size()]));
                StairsChartRenderer renderer = new StairsChartRenderer();
                renderer.setDataSource(aperDataSource);
                Style[] styles = new Style[aperDataSets.size()];
                for (int i = 0; i < aperDataSets.size(); i++) {
                    styles[i] = new Style(Color.black);
                }
                renderer.setStyles(styles);
                chart.addRenderer(axisCount, renderer);
            }
            axisCount++;
        }
        DVView dvView = new DVView(name);
        dvView.addDataView(new DataView(chart));
        dataViewer.addView(dvView);
        dataViewer.setCurrentView(dvView);
    }

    /**
     * setter used for DI
     * 
     * @param tfsDataSetManager the tfsDataSetManager to set
     */
    public void setTfsDataSetManager(TfsDataSetManager tfsDataSetManager) {
        this.tfsDataSetManager = tfsDataSetManager;
        tfsDataSetManager.addListener(this);
    }

    /**
     * @return the tfsDataSetManager
     */
    public TfsDataSetManager getTfsDataSetManager() {
        return tfsDataSetManager;
    }

    /**
     * @param chartFactory the chartFactory to set
     */
    public void setChartFactory(ChartFactory chartFactory) {
        this.chartFactory = chartFactory;
    }

    /**
     * @return the chartFactory
     */
    private ChartFactory getChartFactory() {
        return chartFactory;
    }

    @Override
    public void createdDataSets(String name, String xLabel, Map<Integer, List<TfsResultDataSet>> dataSets) {
        createChart(name, xLabel, dataSets);
    }

    public void setPlotCreationPanel(PlotCreationPanel plotCreationPanel) {
        this.plotCreationPanel = plotCreationPanel;
    }

    private PlotCreationPanel getPlotCreationPanel() {
        return plotCreationPanel;
    }

    public void setUserInteractor(UserInteractor userInteractor) {
        this.userInteractor = userInteractor;
    }

    private UserInteractor getUserInteractor() {
        return userInteractor;
    }

    @Override
    public void twissCalculated(TfsResult tfsResult) {
        /* nothing to do ss */
    }

    public void setJmadGuiActions(JMadGuiActions jmadGuiActions) {
        this.jmadGuiActions = jmadGuiActions;
    }
}

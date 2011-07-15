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

package cern.accsoft.steering.util.gui.dv.ds;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;

import org.apache.log4j.Logger;

import cern.accsoft.steering.util.fit.DataViewerFit;
import cern.accsoft.steering.util.fit.SineFit;
import cern.accsoft.steering.util.gui.icons.Icon;
import cern.jdve.data.DataSet;
import cern.jdve.interactor.EditInteractor;
import cern.jdve.utils.DataRange;
import cern.jdve.utils.DisplayPoint;

/**
 * this class provides methods to create a fit on a dataset of a dataviewer, using selected points
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public abstract class SineFitInteractor extends EditInteractor {
    private final static Logger logger = Logger.getLogger(SineFitInteractor.class);

    /** the minimum of points that must be selected */
    private final static int MIN_POINTS_SELECTED = 1;

    /** the number of points that shall be calculated by the fit */
    private final static int DEFAULT_CALC_POINTS = 400;

    /** the action to create a sine-fit */
    private Action sineFitAction = new SineFitAction();

    /** the action to remove the fit */
    private Action clearFitAction = new ClearFitAction();

    /** the buttons for the toolbar */
    private JButton[] toolBarButtons;

    /** the button to invoke the sine fit */
    private JButton btnSineFit;

    /**
     * the constructor. Prepares the Toolbar-buttons
     */
    public SineFitInteractor() {
        super();
        createToolBarButtons();
    }

    /**
     * creates a sineFit with the selected DataPoints.
     */
    private void createSineFit() {
        List<DisplayPoint> selectedPoints = getSelectedDataPoints();
        if (selectedPoints.size() < MIN_POINTS_SELECTED) {
            logger.warn("Not enough datapoints selected (min " + MIN_POINTS_SELECTED + "), aborting fit.");
            return;
        }

        SineFit fit = new SineFit(selectedPoints);
        fit.doFit();
        setFitToChart(fit);
    }

    /**
     * searches for the selected points for this
     * 
     * @return
     */
    private List<DisplayPoint> getSelectedDataPoints() {
        DisplayPoint[] displayPoints = getSelectedDisplayPoints();
        List<DisplayPoint> list = new ArrayList<DisplayPoint>();
        for (int i = 0; i < displayPoints.length; i++) {
            list.add(displayPoints[i]);
        }
        return list;
    }

    /**
     * adds the given Fit to the chart.
     * 
     * @param fit the fit to add.
     */
    private void setFitToChart(DataViewerFit fit) {
        DataRange range = getChart().getDataWindow().getXRange();
        setFitDataSet(fit.getResultDataSet(range, DEFAULT_CALC_POINTS));
    }

    /**
     * to be overridden by subclass: must display the given data
     * 
     * @param dataSet the dataSet which contains the fitted data
     */
    protected abstract void setFitDataSet(DataSet dataSet);

    /**
     * to be overriden by subclass: must clear the data of the fit.
     */
    protected abstract void clearFit();

    /**
     * creates the Buttons for display in the toolbar
     */
    private void createToolBarButtons() {
        btnSineFit = new JButton(sineFitAction);
        btnSineFit.setText(null);
        this.toolBarButtons = new JButton[] { btnSineFit };
    }

    @Override
    public Component[] getToolBarComponents() {
        return this.toolBarButtons;
    }

    @Override
    public Action[] getPopupActions() {
        return new Action[] { sineFitAction, clearFitAction };
    }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);

        if (toolBarButtons != null && !active) {
            toolBarButtons[0].setEnabled(active);
        }
    }

    /**
     * The action to create a sine fit
     * 
     * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
     */
    public class SineFitAction extends AbstractAction {
        private static final long serialVersionUID = -645526133743397868L;

        public SineFitAction() {
            super("Sine fit", Icon.SINE_FIT.getImageIcon());
            super.putValue(Action.SHORT_DESCRIPTION, "<html><center><b>Sine fit</b></center>Usage:<br/>"
                    + "To create a sine fit - select points to use as fit - input select <br/>"
                    + "them using left mouse button and click this button.</html>");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            createSineFit();
        }
    }

    /**
     * action to remove the fit from the dataViewer
     * 
     * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
     */
    public class ClearFitAction extends AbstractAction {
        private static final long serialVersionUID = -6375357705542370646L;

        public ClearFitAction() {
            super("clear fit");
            super.putValue(Action.SHORT_DESCRIPTION, "<html><center><b>clear fit</b></center>Usage:<br/>"
                    + "use this button to remove the fit line from the dataviewer. </html>");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            clearFit();
        }

    }

}

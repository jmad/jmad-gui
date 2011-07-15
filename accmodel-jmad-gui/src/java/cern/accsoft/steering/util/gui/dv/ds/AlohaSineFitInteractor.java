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

import cern.accsoft.steering.util.gui.dv.ds.Aloha2DChart.ChartRendererRole;
import cern.jdve.Chart;
import cern.jdve.ChartRenderer;
import cern.jdve.data.DataSet;

/**
 * this is the implementation of a SineFitInteractor for Aloha
 * 
 * @author kfuchsbe
 */
public class AlohaSineFitInteractor extends SineFitInteractor {

    public final static int SINEFIT_INTERACTOR_TYPE = 100;

    @Override
    public int getType() {
        return SINEFIT_INTERACTOR_TYPE;
    }

    @Override
    protected void clearFit() {
        ChartRendererRole role = getDestinationRendererRole();
        Aloha2DChart chart = getAloha2DChart();
        if ((role == null) || (chart == null)) {
            return;
        }
        chart.clearRenderer(role);
    }

    @Override
    protected void setFitDataSet(DataSet dataSet) {
        ChartRendererRole role = getDestinationRendererRole();
        Aloha2DChart chart = getAloha2DChart();
        if ((role == null) || (chart == null)) {
            return;
        }
        chart.setRendererDataSet(role, dataSet);
    }

    /**
     * determines the renderer, on which the fitted function shall be plotted. this depends on the renderer, to which
     * the function was fitted.
     * 
     * @return the renderer, if an appropriate one was found.
     */
    private ChartRendererRole getDestinationRendererRole() {
        Aloha2DChart chart = getAloha2DChart();
        if (chart == null) {
            return null;
        }

        ChartRenderer sourceRenderer = getEditedRenderer();
        ChartRendererRole role = chart.getRendererRole(sourceRenderer);
        if (role == null) {
            return null;
        }

        /*
         * finally we decide, which renderer to use, depending on the renderer from which the datapoints are coming
         * from.
         */
        if (ChartRendererRole.MEAS_DATA.equals(role)) {
            return ChartRendererRole.MEAS_FIT;
        }
        if (ChartRendererRole.MODEL_DATA.equals(role)) {
            return ChartRendererRole.MODEL_FIT;
        } else {
            return null;
        }
    }

    /**
     * @return the actual chart, if it is an {@link Aloha2DChart}, null otherwise.
     */
    private Aloha2DChart getAloha2DChart() {
        Chart chart = getChart();
        if (chart == null) {
            return null;
        }

        if (chart instanceof Aloha2DChart) {
            return (Aloha2DChart) chart;
        }
        return null;
    }
}

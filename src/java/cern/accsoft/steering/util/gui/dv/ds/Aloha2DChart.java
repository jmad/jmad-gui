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

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.gui.dv.MarkableChart;
import cern.accsoft.steering.util.StatUtil;
import cern.jdve.ChartRenderer;
import cern.jdve.EditionManager;
import cern.jdve.Style;
import cern.jdve.data.DataSet;
import cern.jdve.data.DataSource;
import cern.jdve.data.DefaultDataSet;
import cern.jdve.data.DefaultDataSource;
import cern.jdve.event.DataSetEvent;
import cern.jdve.event.DataSetListener;
import cern.jdve.event.DataSourceEvent;
import cern.jdve.event.DataSourceListener;
import cern.jdve.graphic.ChartAnnotation;
import cern.jdve.renderer.HiLoRenderer;

/**
 * This class is derived from a MarkerXProvider-chart, which is defined in jmad, in order to provide some additional
 * functions, we use in aloha. Here we provide an easy way to access the renderers, which each of them will have a
 * dedicated role in Aloha.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class Aloha2DChart extends MarkableChart {
    private static final long serialVersionUID = 7117238469835822463L;

    /** here we display the mean and rms */
    private ChartAnnotation commentAnnotation = null;

    /** determines, if stats shall be shown, or not */
    private boolean visibleStatistics = true;

    /**
     * the default constructor, which creates the necessary renderers with default settings.
     */
    public Aloha2DChart() {
        super();
        initDefaultConfig();
        createRenderers();
        createDefaultInteractors();
    }

    /**
     * just sets the default-values
     */
    private void initDefaultConfig() {
        super.setAntiAliasingText(true);
    }

    public void setVisibleCategory(boolean visibleCategory) {
        super.getXScale().setLabelRotation((visibleCategory ? 90 : 0));
        super.getXScale().setCategory(visibleCategory);
    }

    public boolean isVisibleCategory() {
        return super.getXScale().isCategory();
    }

    /**
     * create the default renderers and set empty dataSets to them.
     */
    private void createRenderers() {
        /*
         * one renderer for each role:
         */
        /* MEASUREMENT_DATA */
        addEmptyRenderer(styleRenderer(DvUtils.createBarChartRenderer(), ChartRendererRole.MEAS_DATA));
        /* MEAS_ERROR */
        addEmptyRenderer(styleRenderer(DvUtils.createHiLoRenderer(), ChartRendererRole.MEAS_ERROR));
        /* MODEL_DATA */
        addEmptyRenderer(styleRenderer(DvUtils.createMarkerPolylineRenderer(), ChartRendererRole.MODEL_DATA));
        /* MEASUREMENT_FIT */
        addEmptyRenderer(styleRenderer(DvUtils.createPolyLineRenderer(), ChartRendererRole.MEAS_FIT));
        /* MODEL_FIT */
        addEmptyRenderer(styleRenderer(DvUtils.createPolyLineRenderer(), ChartRendererRole.MODEL_FIT));
    }

    /**
     * returns the correct style for the given role
     * 
     * @param role
     * @return
     */
    private ChartRenderer styleRenderer(ChartRenderer renderer, ChartRendererRole role) {
        Color color = null;
        Color strokeColor = null;
        if (ChartRendererRole.MEAS_DATA.equals(role)) {
            color = ColorConstants.COLOR_MEAS_DATA_FILL;
            strokeColor = ColorConstants.COLOR_MEAS_DATA_STROKE;
        } else if (ChartRendererRole.MEAS_ERROR.equals(role)) {
            color = ColorConstants.COLOR_MEAS_ERROR_FILL;
            strokeColor = ColorConstants.COLOR_MEAS_ERROR_STROKE;
        } else if (ChartRendererRole.MODEL_DATA.equals(role)) {
            color = ColorConstants.COLOR_MODEL_DATA;
            strokeColor = color.darker();
        }

        if (color != null) {
            renderer.setStyles(new Style[] { new Style(strokeColor, color) });
        }

        return renderer;
    }

    /**
     * adds the default interactors for an aloha-chart
     */
    public void createDefaultInteractors() {
        DvUtils.configureDefaultInteractors(this);
    }

    /**
     * sets an empty dataSet to the given renderer and adds it to the chart.
     * 
     * @param renderer the renderer which to add to the chart.
     */
    private void addEmptyRenderer(ChartRenderer renderer) {
        if (renderer instanceof HiLoRenderer) {
            renderer.setDataSource(new DefaultDataSource(new DataSet[] { createEmptyDataSet(), createEmptyDataSet() }));
        } else {
            renderer.setDataSet(createEmptyDataSet());
        }
        addRenderer(renderer);
    }

    /**
     * sets a new dataSet to the renderer of the given role.
     * 
     * @param role the role of the renderer for which to set the new DataSet
     * @param dataSet the new DataSet which to set to the renderer
     */
    public void setRendererDataSet(ChartRendererRole role, DataSet dataSet) {
        ChartRenderer renderer = getRenderer(role);
        renderer.setDataSet(dataSet);

        if (ChartRendererRole.MEAS_DATA.equals(role)) {
            dataSet.addDataSetListener(new DataSetListener() {

                @Override
                public void dataSetChanged(DataSetEvent evt) {
                    updateStatistics();
                }
            });
        }
        updateStatistics();
    }

    /**
     * sets a new dataSource to the renderer of a given Role
     * 
     * @param role the role of the Renderer to which to set the dataSource
     * @param dataSource the {@link DataSource} to set
     */
    public void setRenderDataSource(ChartRendererRole role, DataSource dataSource) {
        ChartRenderer renderer = getRenderer(role);
        renderer.setDataSource(dataSource);

        if (ChartRendererRole.MEAS_DATA.equals(role)) {
            dataSource.addDataSourceListener(new DataSourceListener() {

                @Override
                public void dataSourceChanged(DataSourceEvent evt) {
                    updateStatistics();
                }
            });
        }
        updateStatistics();
    }

    /**
     * sets the renderer as empty
     */
    public void clearRenderer(ChartRendererRole role) {
        setRendererDataSet(role, createEmptyDataSet());
    }

    /**
     * creates an empty dataSet which is used as default for the renderers.
     * 
     * @return the empty DataSet.
     */
    private DataSet createEmptyDataSet() {
        return new DefaultDataSet("empty", new double[] {}, new double[] {});
    }

    /**
     * @param role the {@link ChartRendererRole} for which to retrieve the renderer.
     * @return the renderer for that role.
     */
    public ChartRenderer getRenderer(ChartRendererRole role) {
        return super.getRenderer(role.ordinal());
    }

    /**
     * exchanges the renderer of the given role with the new one.
     * 
     * @param role
     * @param renderer
     */
    public void setRenderer(ChartRendererRole role, ChartRenderer renderer) {
        super.setRenderer(role.ordinal(), styleRenderer(renderer, role));
        updateEditablePlots();
    }

    /**
     * replace the renderer for the given Role with a renderer of another type
     * 
     * @param role the role of the renderer
     * @param rendererType the type of the renderer
     */
    public void setRendererType(ChartRendererRole role, int rendererType) {
        setRendererType(role.ordinal(), rendererType);
    }

    /**
     * set the renderer-type for a given role, by Enum
     * 
     * @param role the role for which to set the new renderer
     * @param type the type of renderer to set.
     */
    public void setRendererType(ChartRendererRole role, RendererType type) {
        setRendererType(role, type.getDvIntValue());
    }

    /**
     * @param role the role for which to retrieve the renderer-type
     * @return the {@link RendererType} for the given renderer-role.
     */
    public RendererType getRendererType(ChartRendererRole role) {
        int intValue = ChartRenderer.getRendererType(getRenderer(role));
        return RendererType.fromDvIntValue(intValue);
    }

    @Override
    public void setRendererType(int index, int rendererType) {
        /*
         * for some types we want to use our costumized renderers.
         */
        if (rendererType == ChartRenderer.BAR) {
            setRenderer(index, DvUtils.createBarChartRenderer());
        } else if (rendererType == ChartRenderer.POLYLINE_WITH_MARKERS) {
            setRenderer(index, DvUtils.createMarkerPolylineRenderer());
        } else if (rendererType == ChartRenderer.POLYLINE) {
            setRenderer(index, DvUtils.createPolyLineRenderer());
        } else if (rendererType == ChartRenderer.SCATTER) {
            setRenderer(index, DvUtils.createScatterRenderer());
        } else {
            /* if we do not have a custom one, we use the default method. */
            super.setRendererType(index, rendererType);
        }
        updateEditablePlots();
    }

    /**
     * this methods causes e.g. the refill of the combo-box for editable datasets.
     */
    private final void updateEditablePlots() {
        EditionManager editionManager = getEditionManager();
        if (editionManager != null) {
            /*
             * XXX ??? we get an error here!
             */
            // editionManager.updateEditablePlots();
        }
    }

    /**
     * @param renderer the renderer for which to determine the role.
     * @return the role of the renderer, if it is contained in this chart, otherwise null
     */
    public ChartRendererRole getRendererRole(ChartRenderer renderer) {
        for (int i = 0; i < getRenderersCount(); i++) {
            ChartRenderer chartRenderer = getRenderer(i);
            if (chartRenderer == renderer) {
                return ChartRendererRole.values()[i];
            }
        }
        return null;
    }

    /**
     * draws the new stats for the plot
     */
    private void updateStatistics() {
        if (this.visibleStatistics) {
            if (this.commentAnnotation == null) {
                this.commentAnnotation = DvUtils.addComment(this, " ");
            }
            this.commentAnnotation.setText(getStatText());
        } else {
            if (this.commentAnnotation != null) {
                removeDecoration(this.commentAnnotation);
                this.commentAnnotation = null;
                repaintChart();
            }
        }
    }

    /**
     * @return a text with statistical info for the measurement-data
     */
    private String getStatText() {
        ChartRenderer dataRenderer = getRenderer(ChartRendererRole.MEAS_DATA);
        if ((dataRenderer == null) || (dataRenderer.getDataSource() == null)) {
            return "";
        }

        DataSet dataSet = dataRenderer.getDataSource().getDataSet(0);
        if (dataSet == null) {
            return "";
        }

        List<Double> values = new ArrayList<Double>();
        for (int i = 0; i < dataSet.getDataCount(); i++) {
            if ((dataSet instanceof ValidityDataSet) && (((ValidityDataSet) dataSet).hasValidityInformation())) {
                if (!((ValidityDataSet) dataSet).getValidity(i)) {
                    continue;
                }
            }
            values.add(dataSet.getY(i));
        }

        double[] valuesArray = new double[values.size()];
        for (int i = 0; i < valuesArray.length; i++) {
            valuesArray[i] = values.get(i);
        }
        return StatUtil.createMeanRmsString(valuesArray);
    }

    /**
     * @param visibleStatistics the visibleStatistics to set
     */
    public void setVisibleStatistics(boolean visibleStatistics) {
        this.visibleStatistics = visibleStatistics;
        updateStatistics();
    }

    /**
     * @return the visibleStatistics
     */
    public boolean isVisibleStatistics() {
        return visibleStatistics;
    }

    /**
     * this is the enum, which will define the roles for different renderers in ALOHA.
     * 
     * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
     */
    public enum ChartRendererRole {
        MEAS_DATA, MEAS_ERROR, MODEL_DATA, MEAS_FIT, MODEL_FIT;
    }

    /**
     * this enum encapsulates the int-constants used in dataViewer into an enum
     * 
     * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
     */
    public enum RendererType {

        POLYLINE(ChartRenderer.POLYLINE), POLYLINE_WITH_MARKERS(ChartRenderer.POLYLINE_WITH_MARKERS), SCATTER(
                ChartRenderer.SCATTER), BAR(ChartRenderer.BAR), IMPULSES(ChartRenderer.IMPULSES), AREA(
                ChartRenderer.AREA), DIFF_AREA(ChartRenderer.DIFF_AREA), STAIRS(ChartRenderer.STAIRS), CONTOUR(
                ChartRenderer.CONTOUR), BOOLEAN(ChartRenderer.BOOLEAN), HI_LO(ChartRenderer.HI_LO);

        private int dvIntValue = 0;

        /**
         * determines the RendererType - value out of a given integer used by the dataviewer
         * 
         * @param value the value for which to find the enum
         * @return the type of the renderer as enum
         */
        public final static RendererType fromDvIntValue(int value) {
            for (RendererType type : RendererType.values()) {
                if (type.getDvIntValue() == value) {
                    return type;
                }
            }
            return null;
        }

        /**
         * the constructor, which also sets the Dv-integer value
         * 
         * @param dvIntValue
         */
        private RendererType(int dvIntValue) {
            this.dvIntValue = dvIntValue;
        }

        /**
         * @return the dvIntValue
         */
        public int getDvIntValue() {
            return dvIntValue;
        }
    }

}

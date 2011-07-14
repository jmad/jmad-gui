// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
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
package cern.accsoft.steering.util.gui.dv.ds;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import cern.jdve.Chart;
import cern.jdve.ChartInteractor;
import cern.jdve.ChartRenderer;
import cern.jdve.LabelRenderer;
import cern.jdve.data.DataSet;
import cern.jdve.graphic.ChartAnnotation;
import cern.jdve.graphic.Marker;
import cern.jdve.graphic.MarkerFactory;
import cern.jdve.graphic.RenderingHint;
import cern.jdve.interactor.CoordinatesPane;
import cern.jdve.interactor.DataPickerInteractor;
import cern.jdve.interactor.ScrollInteractor;
import cern.jdve.interactor.ZoomInteractor;
import cern.jdve.renderer.BarChartRenderer;
import cern.jdve.renderer.HiLoRenderer;
import cern.jdve.renderer.PolylineChartRenderer;
import cern.jdve.renderer.ScatterChartRenderer;
import cern.jdve.viewer.DataView;

/**
 * @author kfuchsbe
 */
public final class DvUtils {

    public final static RenderingHint VALIDITY_RENDERING_HINT = new ValidityRenderingHint();

    /**
     * private constructor, no external instantiation
     */
    private DvUtils() {
        // only static methods
    }

    /**
     * creates a DataView which additionaly contains an edit - interactor toolbar for fitting.
     * 
     * @param chart
     * @return
     */
    public final static DataView createFitInteractorView(Chart chart) {
        chart.setInteractors(ChartInteractor.createEditIteractors());

        ChartInteractor interactor = chart.getInteractorOfType(ChartInteractor.DATA_PICKER);
        if ((interactor != null) && (interactor instanceof DataPickerInteractor)) {
            configureDataPickerInteractor((DataPickerInteractor) interactor);
        }

        SineFitInteractor sineFitInteractor = new AlohaSineFitInteractor();
        chart.addInteractor(sineFitInteractor);

        DataView dataView = new DataView(chart);
        JToolBar toolBar = chart.getInteractorsToolbar();

        /* add all components from the fit-interactor to the toolbar */
        toolBar.addSeparator();
        Component[] fitComponents = sineFitInteractor.getToolBarComponents();
        for (int i = 0; i < fitComponents.length; i++) {
            toolBar.add(fitComponents[i]);
        }

        dataView.add(toolBar, BorderLayout.NORTH);
        return dataView;
    }

    /**
     * creates the default DataView for Aloha.
     * 
     * @param chart the chart for which to create the DataView
     * @return the dataView
     */
    public final static DataView createDataView(Chart chart) {
        /* default behaviour for the moment */
        return new DataView(chart);
    }

    /**
     * create a polyline renderer for aloha and sets the dataSet
     * 
     * @param dataSet the dataSet to use for the renderer
     * @return the renderer
     */
    public final static ChartRenderer createPolylineRenderer(DataSet dataSet) {
        ChartRenderer renderer = createMarkerPolylineRenderer();
        renderer.setDataSet(dataSet);
        return renderer;
    }

    /**
     * creates the default polyline-renderer with marker for aloha
     * 
     * @return
     */
    public final static ChartRenderer createMarkerPolylineRenderer() {
        PolylineChartRenderer renderer = new PolylineChartRenderer(MarkerFactory.getMarker(Marker.CIRCLE));
        renderer.setMarkerSize(4);
        configureRenderer(renderer);
        return renderer;
    }

    /**
     * creates the default marker rederer for aloha
     * 
     * @return
     */
    public final static ChartRenderer createScatterRenderer() {
        ScatterChartRenderer renderer = new ScatterChartRenderer(MarkerFactory.getMarker(Marker.CIRCLE), 4);
        configureRenderer(renderer);
        return renderer;
    }

    /**
     * creates the default renderer that connects two points (e.g. for alignment) for aloha.
     * 
     * @return the renderer
     */
    public final static ChartRenderer createTwoPointRenderer() {
        TwoPointChartRenderer renderer = new TwoPointChartRenderer(MarkerFactory.getMarker(Marker.CIRCLE), 5);
        configureRenderer(renderer);
        return renderer;
    }

    /**
     * creates the default PolyLineRenderer without markers for aloha.
     * 
     * @return the Renderer
     */
    public final static ChartRenderer createPolyLineRenderer() {
        ChartRenderer renderer = new PolylineChartRenderer();
        configureRenderer(renderer);
        return renderer;
    }

    /**
     * creates the default HiLo-Renderer for aloha
     * 
     * @return the Renderer
     */
    public final static ChartRenderer createHiLoRenderer() {
        ChartRenderer renderer = new HiLoRenderer();
        configureRenderer(renderer);
        return renderer;
    }

    /**
     * creates the default BarChartRenderer for aloha.
     * 
     * @return the BarChartRenderer
     */
    public final static ChartRenderer createBarChartRenderer() {
        BarChartRenderer renderer = new BarChartRenderer();
        configureRenderer(renderer);
        return renderer;
    }

    private final static void configureRenderer(ChartRenderer renderer) {
        renderer.setRenderingHint(VALIDITY_RENDERING_HINT);
    }

    /**
     * create the default data-picker for aloha.
     * 
     * @return the DataPickerInteractor
     */
    public final static ChartInteractor createDataPickerInteractor() {
        DataPickerInteractor dataPicker = new DataPickerInteractor();
        configureDataPickerInteractor(dataPicker);
        return dataPicker;
    }

    public final static void configureDefaultInteractors(Chart chart) {
        chart.addInteractor(DvUtils.createDataPickerInteractor());
        chart.addInteractor(new ZoomInteractor());
        chart.addInteractor(new ScrollInteractor());
    }

    /**
     * configures the {@link DataPickerInteractor} to our default values,
     * 
     * @param dataPicker
     */
    private final static void configureDataPickerInteractor(DataPickerInteractor dataPicker) {
        dataPicker.setPointCoordPane(new AccurateCoordinatesPane());
        dataPicker.setDisplayDataSetName(false);
        dataPicker.setCursorCoordPainted(false);
        dataPicker.getPointCoordPane().setXCoordDisplay(CoordinatesPane.DATA_LABEL);
    }

    /**
     * creates a simple annotation in the chart, with the given Text
     * 
     * @param chart the chart to add the annotation to
     * @param comment the text of the annotation
     * @return the annotatioon
     */
    public final static ChartAnnotation addComment(Chart chart, String comment) {
        ChartAnnotation commentAnnotation = new ChartAnnotation(comment, 5, 1, false);
        LabelRenderer labRender = new LabelRenderer();
        labRender.setFont(new Font("Monospaced", Font.BOLD, 12));
        labRender.setHorizontalAlignment(SwingConstants.LEFT);
        labRender.setVerticalAlignment(SwingConstants.TOP);
        labRender.setBackground(null);
        labRender.setBorder(null);
        commentAnnotation.setLabelRenderer(labRender);

        chart.addDecoration(commentAnnotation);
        return commentAnnotation;
    }

}

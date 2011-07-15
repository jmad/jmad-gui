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

package cern.accsoft.steering.jmad.gui.dv;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import cern.accsoft.steering.jmad.gui.mark.MarkedElementsManager;
import cern.accsoft.steering.jmad.gui.mark.MarkedElementsManagerListener;
import cern.accsoft.steering.jmad.gui.mark.MarkerXProvider;
import cern.jdve.Chart;
import cern.jdve.LabelRenderer;
import cern.jdve.Style;
import cern.jdve.data.DataSet;
import cern.jdve.data.DataSource;
import cern.jdve.graphic.DataIndicator;
import cern.jdve.utils.DataRange;

/**
 * This chart is a chart which listens on the MarkedElementsManager, in order to display all the elements as markers.
 * 
 * @author kfuchsbe
 */
public class MarkableChart extends Chart {
    private static final long serialVersionUID = -6909388067830384883L;

    /** the manager, which keeps track of all */
    private MarkedElementsManager markedElementsManager;

    /**
     * with this map we keep track of the added markers, in order to be able to remove/replace them by name afterwards
     */
    private Map<String, List<DataIndicator>> markerDataIndicators = new HashMap<String, List<DataIndicator>>();

    /**
     * if this is set, then the x-positions of the markers are fetched from here. Otherwise the datasets are searched
     * for a valid marker-position.
     */
    private MarkerXProvider markerXProvider = null;

    /** the default font for an point indicator in the chart. */
    private final static Font DEFAULT_INDICATOR_FONT = new Font("Dialog", Font.BOLD, 10);

    /** the default style of the indicator in the chart */
    private static final Style DEFAULT_INDICATOR_STYLE = new Style(new BasicStroke(1, BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_BEVEL, 1, new float[] { 2, 2 }, 2), Color.GRAY, Color.BLACK);

    /** a light red */
    private static final Color COLOR_H_RANGE = new Color(227, 151, 89, 50);

    /** a light blue */
    private static final Color COLOR_V_RANGE = new Color(89, 188, 227, 50);

    /** the font for the ranges */
    private final static Font DEFAULT_FONT_RANGE = new Font("Dialog", Font.BOLD, 20);

    /** shows / hides the H/V indicators */
    private boolean visibleHVIndicators = false;

    private DataIndicator hRangeIndicator = null;
    private DataIndicator vRangeIndicator = null;

    /**
     * the init method, which shall be called when the {@link MarkedElementsManager} was set. It creates all markers,
     * according to the manager. {@link MarkedElementsManager} was set.
     */
    public void initMarkers() {
        if (getMarkedElementsManager() == null) {
            return;
        }

        for (String elementName : getMarkedElementsManager().getElementNames()) {
            addMarker(elementName);
        }
    }

    /**
     * shows the marker for the given element name. This only works if at least one of the {@link DataSet}s implements
     * the {@link MarkerXProvider} interface in order to determine the x-position of the marker.
     * 
     * @param elementName
     */
    private void addMarker(String elementName) {
        /*
         * we want to ensure, that only one indicator of the same name exists (important in the chart).
         */
        removeMarker(elementName);

        List<Double> xPositions = getMarkerXPositions(elementName);
        List<DataIndicator> dataIndicators = new ArrayList<DataIndicator>();
        for (Double xPos : xPositions) {
            DataIndicator indicator = createXIndicator(xPos, elementName);
            dataIndicators.add(indicator);
            addDecoration(indicator);
        }
        this.markerDataIndicators.put(elementName, dataIndicators);
        repaintChart();
    }

    /**
     * removes the marker for the given elementName both from the chart and from our map.
     * 
     * @param elementName the name of the element, for which to remove the marker.
     */
    private void removeMarker(String elementName) {
        List<DataIndicator> indicators = markerDataIndicators.get(elementName);
        if (indicators != null) {
            for (DataIndicator indicator : indicators) {
                removeDecoration(indicator);
            }
        }
        this.markerDataIndicators.remove(elementName);
        repaintChart();
    }

    /**
     * adds the inidicators for horizontal and vertical range to the chart.
     */
    private void createHVRangeIndicators() {
        removeHVRangeIndicators();
        List<Double> xPositions = getMarkerXPositions(MarkerXProvider.ELEMENT_NAME_HV_BORDER);
        Double hvBorder = null;
        if (xPositions.size() == 1) {
            hvBorder = xPositions.get(0);
        } else {
            return;
        }

        double minX = getXAxis().getVisibleRange().getMin();
        double maxX = getXAxis().getVisibleRange().getMax();
        hRangeIndicator = createRangeIndicator(new DataRange(minX, hvBorder), COLOR_H_RANGE, "H");
        addDecoration(hRangeIndicator);

        vRangeIndicator = createRangeIndicator(new DataRange(hvBorder, maxX), COLOR_V_RANGE, "V");

        addDecoration(vRangeIndicator);

        repaintChart();
    }

    private DataIndicator createRangeIndicator(DataRange dataRange, Color fillColor, String label) {
        DataIndicator rangeIndicator = new DataIndicator(DataIndicator.X_RANGE, dataRange, label);

        rangeIndicator.setTextLocation(0.8);
        rangeIndicator.setStyle(new Style(fillColor, fillColor));

        LabelRenderer labelRenderer = rangeIndicator.getLabelRenderer();
        labelRenderer.setBackground(null);
        labelRenderer.setForeground(fillColor.darker());
        labelRenderer.setFont(DEFAULT_FONT_RANGE);
        return rangeIndicator;
    }

    /**
     * removes the indicators for horizontal and vertical range from the chart.
     */
    private void removeHVRangeIndicators() {
        if (this.hRangeIndicator != null) {
            this.removeDecoration(hRangeIndicator);
        }
        if (this.vRangeIndicator != null) {
            this.removeDecoration(vRangeIndicator);
        }
        repaintChart();
    }

    /**
     * searches for the first markerXProvider DataSet, that returns a non-null xposition for the given element. This is
     * then returned.
     * 
     * @return the DataSet, if one is found. null otherwise.
     */
    private List<Double> getMarkerXPositions(String elementName) {

        /*
         * if a x-provider is explicitely set, we try to get the x-pos from there.
         */
        List<Double> xPositions = new ArrayList<Double>();
        if (getMarkerXProvider() != null) {
            xPositions = getMarkerXProvider().getXPositions(elementName);
        }

        if (xPositions.size() > 0) {
            return xPositions;
        }

        /*
         * if it is not set, or does not return a valid value, then we search in the datasets.
         */
        DataSource[] dataSources = getDataSources();
        for (int i = 0; i < dataSources.length; i++) {
            DataSet[] dataSets = dataSources[i].getDataSets();
            for (int j = 0; j < dataSets.length; j++) {
                DataSet dataSet = dataSets[j];
                if (dataSet instanceof MarkerXProvider) {
                    MarkerXProvider markable = (MarkerXProvider) dataSet;
                    List<Double> xPosNew = markable.getXPositions(elementName);
                    /*
                     * we take the longest list ...
                     */
                    if (xPosNew.size() > xPositions.size()) {
                        return xPositions = xPosNew;
                    }
                }
            }
        }
        /* if no dataset implementing MarkerXProvider, then we return null */
        return xPositions;
    }

    /**
     * This method creates a data-indicater of our default shape.
     * 
     * @return the DataIndicator object.
     */
    private DataIndicator createXIndicator(double xPos, String label) {
        DataIndicator pointIndicator = new DataIndicator(DataIndicator.X_VALUE, xPos, label);

        pointIndicator.setTextLocation(0.06);
        pointIndicator.setStyle(DEFAULT_INDICATOR_STYLE);

        LabelRenderer labelRenderer = pointIndicator.getLabelRenderer();
        labelRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        labelRenderer.setBackground(Color.WHITE);
        labelRenderer.setForeground(Color.DARK_GRAY);
        labelRenderer.setBorder(new LineBorder(Color.DARK_GRAY));
        labelRenderer.setFont(DEFAULT_INDICATOR_FONT);

        return pointIndicator;
    }

    /**
     * @param markedElementsManager the markedElementsManager to set
     */
    public void setMarkedElementsManager(MarkedElementsManager markedElementsManager) {
        this.markedElementsManager = markedElementsManager;
        markedElementsManager.addListener(new MarkedElementsManagerListener() {

            @Override
            public void addedElementName(String elementName) {
                addMarker(elementName);
            }

            @Override
            public void removedElementName(String elementName) {
                removeMarker(elementName);
            }
        });
    }

    /**
     * @return the markedElementsManager
     */
    private MarkedElementsManager getMarkedElementsManager() {
        return markedElementsManager;
    }

    /**
     * @param markerXProvider the markerXProvider to set
     */
    public void setMarkerXProvider(MarkerXProvider markerXProvider) {
        this.markerXProvider = markerXProvider;
    }

    /**
     * @return the markerXProvider
     */
    public MarkerXProvider getMarkerXProvider() {
        return markerXProvider;
    }

    /**
     * @param visibleHVIndicators the visibleHVIndicators to set
     */
    public void setVisibleHVIndicators(boolean visibleHVIndicators) {
        this.visibleHVIndicators = visibleHVIndicators;
        if (visibleHVIndicators) {
            createHVRangeIndicators();
        } else {
            removeHVRangeIndicators();
        }
    }

    /**
     * @return the visibleHVIndicators
     */
    public boolean isVisibleHVIndicators() {
        return visibleHVIndicators;
    }
}

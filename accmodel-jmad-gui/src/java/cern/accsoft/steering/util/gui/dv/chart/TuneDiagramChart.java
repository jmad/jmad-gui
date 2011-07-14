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
package cern.accsoft.steering.util.gui.dv.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.swing.JButton;

import cern.jdve.Chart;
import cern.jdve.ChartRenderer;
import cern.jdve.Scale;
import cern.jdve.Style;
import cern.jdve.data.DefaultDataSet;
import cern.jdve.data.DefaultDataSource;
import cern.jdve.event.ChartInteractionListener;
import cern.jdve.graphic.DefaultRenderingHint;
import cern.jdve.graphic.Marker;
import cern.jdve.graphic.MarkerFactory;
import cern.jdve.interactor.ZoomInteractor;
import cern.jdve.renderer.PolylineChartRenderer;
import cern.jdve.renderer.ScatterChartRenderer;
import cern.jdve.utils.DataWindow;

/**
 * Chart for a tune diagram. It just takes two tune values, the H and V tune
 * 
 * @author rstein
 */
public class TuneDiagramChart extends Chart {
    private static final long serialVersionUID = 1L;
    private final int MAX_NUMBER_OF_MARKER = 10;
    private ZoomInteractor fzoomInteractor;
    private ScatterChartRenderer fScatterChartRenderer;
    private PolylineChartRenderer fpolyrenderer;
    private DefaultDataSource fresonances = new DefaultDataSource();
    private DefaultDataSet fdataset = new DefaultDataSet("TuneTrace");
    private Collection<ChartInteractionListener> flisteners = new ArrayList<ChartInteractionListener>();
    private Style[] fstyles = new Style[MAX_NUMBER_OF_MARKER];
    private Style[] flinestyles = new Style[24];
    private int fresonanceOrder = 10;

    public TuneDiagramChart(int resonanceOrder) {
        super();
        this.fresonanceOrder = resonanceOrder;

        this.setName("TuneDiagram");
        this.setPreferredSize(new Dimension(500, 300));
        this.setAntiAliasing(true);
        this.setAntiAliasingText(true);
        this.setRenderingType(ChartRenderer.POLYLINE_WITH_MARKERS);
        this.getYGrid().setMinorLineVisible(true);
        this.getYGrid().setMinorColor(new Color(230, 230, 230));
        this.getXScale().setTitleAlignment(Scale.TITLE_RIGHT);
        this.getYScale().setTitleAlignment(Scale.TITLE_RIGHT);
        this.setAntiAliasing(false);
        this.setXScaleTitle("Q1 [2 pi]");
        this.setYScaleTitle("Q2 [2 pi]");
        this.setDoubleBuffered(true);

        this.setDataSet(fdataset);

        fScatterChartRenderer = new ScatterChartRenderer();
        fScatterChartRenderer.setDataSet(fdataset);
        this.addRenderer(fScatterChartRenderer);

        updateTuneMarkerStyles(MAX_NUMBER_OF_MARKER);

        fzoomInteractor = new ZoomInteractor();
        fzoomInteractor.setAnimationStep(0);
        fzoomInteractor.setOutOfRangeZoomAllowed(false);
        this.addInteractor(fzoomInteractor);

        drawResonanceLines(true, true, fresonanceOrder);
    }

    private void updateTuneMarkerStyles(int maxMarker) {
        int tmaxMarker = Math.min(maxMarker, MAX_NUMBER_OF_MARKER);
        if (tmaxMarker <= 0) {
            tmaxMarker = 1;
        }

        fstyles = new Style[tmaxMarker];
        final int scale = (int) (255.0 / (double) (tmaxMarker * 1.1));
        for (int i = 0; i < fstyles.length; i++) {
            Color c = new Color(i * scale, i * scale, 255);
            fstyles[i] = new Style(new BasicStroke(1.5f), c, c);
        }

        fScatterChartRenderer.setMarker(MarkerFactory.getMarker(Marker.CIRCLE));
        fScatterChartRenderer.setMarkerSize(10);
        for (int i = 0; i < fstyles.length; i++) {
            fScatterChartRenderer.setRenderingHint(fdataset, i, new DefaultRenderingHint(
                    fstyles[fstyles.length - 1 - i]));
        }
    }

    /**
     * Transforms value to above/below 0.5
     * 
     * @param val - the tune value
     * @param below05 - whether value is supposed to be 0.5
     * @return
     */
    private double Qtrafo(double val, boolean below) {
        if (below)
            return val;
        else
            return (1.0 - val);
    }

    private void drawResonanceLines(boolean Qxbelow, boolean Qybelow, int resonanceOrder) {

        Color c;
        boolean update = false;
        if (fpolyrenderer != null) {
            update = true;
        } else {
            update = false;
            // first and second order resonances
            c = new Color(255, 0, 0);
            flinestyles[0] = new Style(new BasicStroke(1.5f), c, c);

            // third order resonances
            c = new Color(0, 0, 255);
            flinestyles[1] = new Style(new BasicStroke(1.5f), c, c);
            flinestyles[2] = new Style(new BasicStroke(1.5f), c, c);
            flinestyles[3] = new Style(new BasicStroke(1.5f), c, c);

            // fourth order resonances
            c = new Color(100, 100, 100);
            flinestyles[4] = new Style(new BasicStroke(1.0f), c, c);
            flinestyles[5] = new Style(new BasicStroke(1.0f), c, c);

            // fifth order resonances
            c = new Color(100, 100, 100);
            flinestyles[6] = new Style(new BasicStroke(0.5f), c, c);
            flinestyles[7] = new Style(new BasicStroke(0.5f), c, c);
            flinestyles[8] = new Style(new BasicStroke(0.5f), c, c);
            flinestyles[9] = new Style(new BasicStroke(0.5f), c, c);
            flinestyles[10] = new Style(new BasicStroke(0.5f), c, c);
            flinestyles[11] = new Style(new BasicStroke(0.5f), c, c);
            flinestyles[12] = new Style(new BasicStroke(0.5f), c, c);

            // sixth order resonances
            c = new Color(150, 150, 150);
            flinestyles[13] = new Style(new BasicStroke(0.5f), c, c);
            flinestyles[14] = new Style(new BasicStroke(0.5f), c, c);
            flinestyles[15] = new Style(new BasicStroke(0.5f), c, c);
            flinestyles[16] = new Style(new BasicStroke(0.5f), c, c);
            flinestyles[17] = new Style(new BasicStroke(0.5f), c, c);
            flinestyles[18] = new Style(new BasicStroke(0.5f), c, c);
            flinestyles[19] = new Style(new BasicStroke(0.5f), c, c);
            flinestyles[20] = new Style(new BasicStroke(0.5f), c, c);
            flinestyles[21] = new Style(new BasicStroke(0.5f), c, c);
            flinestyles[22] = new Style(new BasicStroke(0.5f), c, c);
            flinestyles[23] = new Style(new BasicStroke(0.5f), c, c);

            fpolyrenderer = new PolylineChartRenderer();
            fpolyrenderer.setStyles(flinestyles);
        }

        /* overwrites eventual older resonance lines */
        fresonances = new DefaultDataSource();

        DefaultDataSet dataframe = new DefaultDataSet("frame");
        dataframe.setEditable(true);
        dataframe.add(Qtrafo(0.0, Qxbelow), Qtrafo(0.0, Qybelow));
        dataframe.add(Qtrafo(0.5, Qxbelow), Qtrafo(0.0, Qybelow));
        dataframe.add(Qtrafo(0.5, Qxbelow), Qtrafo(0.5, Qybelow));
        dataframe.add(Qtrafo(0.0, Qxbelow), Qtrafo(0.5, Qybelow));
        dataframe.add(Qtrafo(0.0, Qxbelow), Qtrafo(0.0, Qybelow));
        dataframe.add(Qtrafo(0.5, Qxbelow), Qtrafo(0.5, Qybelow));
        fresonances.addDataSet(dataframe);

        /* add and plot all third order resonances */
        if (resonanceOrder >= 3) {
            dataframe = new DefaultDataSet("third_1");
            dataframe.setEditable(true);
            dataframe.add(Qtrafo(0.0, Qxbelow), Qtrafo(1.0 / 3.0, Qybelow));
            dataframe.add(Qtrafo(0.5, Qxbelow), Qtrafo(1.0 / 3.0, Qybelow));
            fresonances.addDataSet(dataframe);

            dataframe = new DefaultDataSet("third_2");
            dataframe.setEditable(true);
            dataframe.add(Qtrafo(1.0 / 3.0, Qxbelow), Qtrafo(0.0, Qybelow));
            dataframe.add(Qtrafo(1.0 / 3.0, Qxbelow), Qtrafo(0.5, Qybelow));
            fresonances.addDataSet(dataframe);

            dataframe = new DefaultDataSet("third_3");
            dataframe.setEditable(true);
            dataframe.add(Qtrafo(0.5, Qxbelow), Qtrafo(0.0, Qybelow));
            dataframe.add(Qtrafo(1.0 / 3.0, Qxbelow), Qtrafo(0.5, Qybelow));
            dataframe.add(Qtrafo(0.0, Qxbelow), Qtrafo(0.0, Qybelow));
            dataframe.add(Qtrafo(0.5, Qxbelow), Qtrafo(1.0 / 3.0, Qybelow));
            dataframe.add(Qtrafo(0.0, Qxbelow), Qtrafo(0.5, Qybelow));
            fresonances.addDataSet(dataframe);
        }

        /* add and plot all fourth order resonances */
        if (resonanceOrder >= 4) {
            dataframe = new DefaultDataSet("fourth_1");
            dataframe.setEditable(true);
            dataframe.add(Qtrafo(0.25, Qxbelow), Qtrafo(0.0, Qybelow));
            dataframe.add(Qtrafo(0.25, Qxbelow), Qtrafo(0.5, Qybelow));
            fresonances.addDataSet(dataframe);

            dataframe = new DefaultDataSet("fourth_2");
            dataframe.setEditable(true);
            dataframe.add(Qtrafo(0.0, Qxbelow), Qtrafo(0.25, Qybelow));
            dataframe.add(Qtrafo(0.5, Qxbelow), Qtrafo(0.25, Qybelow));
            fresonances.addDataSet(dataframe);
        }

        /* add and plot all fifth order resonances */
        if (resonanceOrder >= 5) {
            dataframe = new DefaultDataSet("fifth_1");
            dataframe.setEditable(true);
            dataframe.add(Qtrafo(0.2, Qxbelow), Qtrafo(0.0, Qybelow));
            dataframe.add(Qtrafo(0.2, Qxbelow), Qtrafo(0.5, Qybelow));
            fresonances.addDataSet(dataframe);

            dataframe = new DefaultDataSet("fifth_2");
            dataframe.setEditable(true);
            dataframe.add(Qtrafo(0.4, Qxbelow), Qtrafo(0.0, Qybelow));
            dataframe.add(Qtrafo(0.4, Qxbelow), Qtrafo(0.5, Qybelow));
            fresonances.addDataSet(dataframe);

            dataframe = new DefaultDataSet("fifth_3");
            dataframe.setEditable(true);
            dataframe.add(Qtrafo(0.0, Qxbelow), Qtrafo(0.2, Qybelow));
            dataframe.add(Qtrafo(0.5, Qxbelow), Qtrafo(0.2, Qybelow));
            fresonances.addDataSet(dataframe);

            dataframe = new DefaultDataSet("fifth_4");
            dataframe.setEditable(true);
            dataframe.add(Qtrafo(0.0, Qxbelow), Qtrafo(0.4, Qybelow));
            dataframe.add(Qtrafo(0.5, Qxbelow), Qtrafo(0.4, Qybelow));
            fresonances.addDataSet(dataframe);

            dataframe = new DefaultDataSet("fifth_5");
            dataframe.setEditable(true);
            dataframe.add(Qtrafo(0.0, Qxbelow), Qtrafo(0.5, Qybelow));
            dataframe.add(Qtrafo(0.5, Qxbelow), Qtrafo(0.0, Qybelow));
            fresonances.addDataSet(dataframe);

            dataframe = new DefaultDataSet("fifth_6");
            dataframe.setEditable(true);
            dataframe.add(Qtrafo(1.0 / 6.0, Qxbelow), Qtrafo(0.5, Qybelow));
            dataframe.add(Qtrafo(1.0 / 3.0, Qxbelow), Qtrafo(0.0, Qybelow));
            fresonances.addDataSet(dataframe);

            dataframe = new DefaultDataSet("fifth_7");
            dataframe.setEditable(true);
            dataframe.add(Qtrafo(0.5, Qxbelow), Qtrafo(1.0 / 6.0, Qybelow));
            dataframe.add(Qtrafo(0.0, Qxbelow), Qtrafo(1.0 / 3.0, Qybelow));
            fresonances.addDataSet(dataframe);
        }

        /* add and plot all sixth order resonances */
        if (resonanceOrder >= 6) {
            dataframe = new DefaultDataSet("sixth_1");
            dataframe.setEditable(true);
            dataframe.add(Qtrafo(0.0, Qxbelow), Qtrafo(1.0 / 6.0, Qybelow));
            dataframe.add(Qtrafo(0.5, Qxbelow), Qtrafo(1.0 / 6.0, Qybelow));
            fresonances.addDataSet(dataframe);

            dataframe = new DefaultDataSet("sixth_2");
            dataframe.setEditable(true);
            dataframe.add(Qtrafo(1.0 / 6.0, Qxbelow), Qtrafo(0.0, Qybelow));
            dataframe.add(Qtrafo(1.0 / 6.0, Qxbelow), Qtrafo(0.5, Qybelow));
            fresonances.addDataSet(dataframe);

            dataframe = new DefaultDataSet("sixth_3");
            dataframe.setEditable(true);
            dataframe.add(Qtrafo(0.25, Qxbelow), Qtrafo(0.0, Qybelow));
            dataframe.add(Qtrafo(1.0 / 8.0, Qxbelow), Qtrafo(0.5, Qybelow));
            fresonances.addDataSet(dataframe);

            dataframe = new DefaultDataSet("sixth_4");
            dataframe.setEditable(true);
            dataframe.add(Qtrafo(1.0 / 3.0, Qxbelow), Qtrafo(0.0, Qybelow));
            dataframe.add(Qtrafo(0.0, Qxbelow), Qtrafo(0.5, Qybelow));
            fresonances.addDataSet(dataframe);

            dataframe = new DefaultDataSet("sixth_5");
            dataframe.setEditable(true);
            dataframe.add(Qtrafo(0.5, Qxbelow), Qtrafo(0.0, Qybelow));
            dataframe.add(Qtrafo(0.0, Qxbelow), Qtrafo(1.0 / 3.0, Qybelow));
            fresonances.addDataSet(dataframe);

            dataframe = new DefaultDataSet("sixth_6");
            dataframe.setEditable(true);
            dataframe.add(Qtrafo(0.5, Qxbelow), Qtrafo(0.0, Qybelow));
            dataframe.add(Qtrafo(3.0 / 8.0, Qxbelow), Qtrafo(0.5, Qybelow));
            fresonances.addDataSet(dataframe);

            dataframe = new DefaultDataSet("sixth_7");
            dataframe.setEditable(true);
            dataframe.add(Qtrafo(0.0, Qxbelow), Qtrafo(0.25, Qybelow));
            dataframe.add(Qtrafo(0.5, Qxbelow), Qtrafo(0.12569, Qybelow));
            fresonances.addDataSet(dataframe);

            dataframe = new DefaultDataSet("sixth_8");
            dataframe.setEditable(true);
            dataframe.add(Qtrafo(0.0, Qxbelow), Qtrafo(1.0 / 3.0, Qybelow));
            dataframe.add(Qtrafo(0.5, Qxbelow), Qtrafo(1.0 / 6.0, Qybelow));
            fresonances.addDataSet(dataframe);

            dataframe = new DefaultDataSet("sixth_9");
            dataframe.setEditable(true);
            dataframe.add(Qtrafo(0.0, Qxbelow), Qtrafo(0.5, Qybelow));
            dataframe.add(Qtrafo(0.5, Qxbelow), Qtrafo(3.0 / 8.0, Qybelow));
            fresonances.addDataSet(dataframe);

            dataframe = new DefaultDataSet("sixth_10");
            dataframe.setEditable(true);
            dataframe.add(Qtrafo(1.0 / 4.0, Qxbelow), Qtrafo(0.5, Qybelow));
            dataframe.add(Qtrafo(0.5, Qxbelow), Qtrafo(1.0 / 3.0, Qybelow));
            fresonances.addDataSet(dataframe);

            dataframe = new DefaultDataSet("sixth_11");
            dataframe.setEditable(true);
            dataframe.add(Qtrafo(1.0 / 3.0, Qxbelow), Qtrafo(0.5, Qybelow));
            dataframe.add(Qtrafo(0.5, Qxbelow), Qtrafo(1.0 / 4.0, Qybelow));
            fresonances.addDataSet(dataframe);
        }

        /* add and plot all seventh order resonances */
        if (resonanceOrder >= 7) {
            // TODO: to populate
        }

        /* add and plot all eighth order resonances */
        if (resonanceOrder >= 8) {
            // TODO: to populate
        }

        fpolyrenderer.setDataSource(fresonances);
        if (!update) {
            this.addRenderer(fpolyrenderer);
        }
    }

    /**
     * Sets the <code>xZoomAllowed</code> flag.
     */
    public void setXZoomAllowed(boolean zoom_mode) {
        fzoomInteractor.setXZoomAllowed(true);
    }

    /**
     * Sets the <code>yZoomAllowed</code> flag.
     */
    public void setYZoomAllowed(boolean zoom_mode) {
        fzoomInteractor.setYZoomAllowed(true);
    }

    /**
     * Sets the <code>xZoomAllowed=true</code> and <code>yZoomAllowed=true</code> flag.
     */
    public void setBothZoomAllowed() {
        fzoomInteractor.setXZoomAllowed(true);
        fzoomInteractor.setYZoomAllowed(true);
    }

    /**
     * Sets the <code>xZoomAllowed=true</code> and <code>yZoomAllowed=false</code> flag.
     */
    public void setXZoomAllowedOnly() {
        fzoomInteractor.setXZoomAllowed(true);
        fzoomInteractor.setYZoomAllowed(false);
    }

    /**
     * Sets the <code>xZoomAllowed=false</code> and <code>yZoomAllowed=true</code> flag.
     */
    public void setYZoomAllowedOnly() {
        fzoomInteractor.setXZoomAllowed(false);
        fzoomInteractor.setYZoomAllowed(true);
    }

    public void zoomToOrigin() {
        zoom(fdataset.getXRange().getMin(), fdataset.getXRange().getMax(), fdataset.getYRange().getMin(), fdataset
                .getYRange().getMax());
    }

    public void zoomLimit(double minX, double maxX, double minY, double maxY) {
        double min_x = fdataset.getXRange().getMin();
        double max_x = fdataset.getXRange().getMax();
        double min_y = fdataset.getYRange().getMin();
        double max_y = fdataset.getYRange().getMax();

        if (!((Double) minX).isNaN())
            min_x = minX;
        if (!((Double) maxX).isNaN())
            max_x = maxX;
        if (!((Double) minY).isNaN())
            min_y = minY;
        if (!((Double) maxY).isNaN())
            max_y = maxY;

        zoom(min_x, max_x, min_y, max_y);
    }

    /**
     * local zoom implementation with some safe guards
     * 
     * @param minX
     * @param maxX
     * @param minY
     * @param maxY
     */
    private void zoom(double minX, double maxX, double minY, double maxY) {
        boolean isAdjusting = isAdjusting();
        if (!isAdjusting)
            setAdjusting(true);

        fzoomInteractor.clearZoomList();
        // some sanity checks
        if (minX > maxX) {
            double temp = minX;
            minX = maxX;
            maxX = temp;
        }
        if (minY > maxY) {
            double temp = minY;
            minY = maxY;
            maxY = temp;
        }

        if (minX == maxX) {
            maxX += 1;
        }

        if (minY == maxY) {
            maxY += 1;
        }

        zoom(0, new DataWindow(minX, maxX, minY, maxY));
        if (!isAdjusting)
            this.setAdjusting(false);
    }

    private double adjustToHalfInteger(double value, boolean aboveHalfInteger) {
        if ((aboveHalfInteger && value < 0.5) || (!aboveHalfInteger && value > 0.5))
            return (1.0 - value);
        else
            return value;
    }

    /**
     * Sets the tune trace
     * 
     * @param tunes
     */
    private void set(double[] qXValues, double[] qYValues) {
        int length = qXValues.length;

        if (qYValues.length != length) {
            throw new IllegalArgumentException("Arrays for Qx and Qy must be of same length!");
        }

        if (length > MAX_NUMBER_OF_MARKER)
            length = MAX_NUMBER_OF_MARKER;

        boolean xAboveHalfInteger = isAboveHalfInteger(qXValues);
        boolean yAboveHalfInteger = isAboveHalfInteger(qYValues);
        
        this.setAdjusting(true);

        double xmin = 1e99, xmax = -1e99, ymin = 1e99, ymax = -1e99;
        double[] xtrace = new double[length];
        double[] ytrace = new double[length];

        for (int i = 0; i < length; i++) {

            double x = adjustToHalfInteger(qXValues[i], xAboveHalfInteger);
            double y = adjustToHalfInteger(qYValues[i], yAboveHalfInteger);

            if (i < xtrace.length) {
                xtrace[i] = x;
                ytrace[i] = y;
            }

            if (x > xmax)
                xmax = x;
            if (x < xmin)
                xmin = x;
            if (y > ymax)
                ymax = y;
            if (y < ymin)
                ymin = y;
        }

        fdataset.set(xtrace, ytrace);
        updateTuneMarkerStyles(xtrace.length);

        this.getXAxis().setRange(0.9 * xmin, 1.1 * xmax);
        this.getYAxis().setRange(0.9 * ymin, 1.1 * ymax);
        this.setAdjusting(false);
    }

    public void addChartInteractionEventListener(ChartInteractionListener listener) {
        flisteners.add(listener);
    }

    public void removeChartInteractionListener(ChartInteractionListener listener) {
        if (flisteners != null) {
            flisteners.remove(listener);
        }
    }

    private boolean isAboveHalfInteger(double[] values) {
        // TODO: define exact behaviour
        if (values.length < 1) {
            return false;
        }
        return values[0] > 0.5;
    }

    public void setValues(double qX, double qY) {
        setValues(new double[] { qX }, new double[] { qY });
    }

    public void setValues(double[] qXValues, double[] qYValues) {
        if (!isAdjusting() && this.isShowing()) {
            double[] qXCopy = getFractionalPart(qXValues);
            double[] qYCOpy = getFractionalPart(qYValues);

            drawResonanceLines(!isAboveHalfInteger(qXCopy), !isAboveHalfInteger(qYCOpy), fresonanceOrder);
            set(qXCopy, qYCOpy);
        }
    }

    private static final double[] getFractionalPart(double[] values) {
        double[] valuesCopy = Arrays.copyOf(values, values.length);
        for (int i = 0; i < valuesCopy.length; i++) {
            double value = valuesCopy[i];
            valuesCopy[i] = value - Math.floor(value);
        }
        return valuesCopy;
    }

}

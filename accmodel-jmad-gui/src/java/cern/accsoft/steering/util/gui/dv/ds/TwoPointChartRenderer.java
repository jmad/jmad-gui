/*
 * $Id: TwoPointChartRenderer.java,v 1.1 2009-02-25 18:48:41 kfuchsbe Exp $
 * 
 * $Date: 2009-02-25 18:48:41 $ 
 * $Revision: 1.1 $ 
 * $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.util.gui.dv.ds;

import java.awt.Graphics2D;

import cern.jdve.Style;
import cern.jdve.data.DataSet;
import cern.jdve.graphic.Marker;
import cern.jdve.renderer.ScatterChartRenderer;
import cern.jdve.utils.DisplayPoints;

/**
 * This renderer always connects two subsequent points with one line. This e.g.
 * is useful for displaying data, which contains start and end-values. (e.g.
 * alignmentData)
 * 
 * @author kfuchsbe
 * 
 */
public class TwoPointChartRenderer extends ScatterChartRenderer {

	//
	// -- CONSTRUCTORS -----------------------------------------------
	//
	/**
	 * Creates a new <code>TwoPointChartRenderer</code>.
	 */
	public TwoPointChartRenderer() {
		super(true);
	}

	/**
	 * Creates a new <code>TwoPointChartRenderer</code>.
	 * 
	 * @param marker
	 *            marker that will be drawn at each data point.
	 */
	public TwoPointChartRenderer(Marker marker) {
		super(marker);
	}

	/**
	 * Creates a new instance of <code>TwoPointChartRenderer</code>.
	 * 
	 * @param marker
	 *            marker that will be drawn at each data point.
	 * @param markerSize
	 *            size of the marker (in pixels)
	 */
	public TwoPointChartRenderer(Marker marker, int markerSize) {
		super(marker, markerSize);
	}

	//
	// -- Overrides ChartRenderer
	// -----------------------------------------------
	//

	/*
	 * (non-Javadoc)
	 * 
	 * @see cern.jdve.ChartRenderer#drawLegendSymbol(java.awt.Graphics2D, int,
	 * cern.jdve.Style)
	 */
	protected void drawLegendSymbol(Graphics2D g2, int width, int height,
			Style style) {
		int[] xPoints = new int[] { 2, width - 2 };
		int[] yPoints = new int[] { height / 2, height / 2 };
		style.drawPolyline(g2, xPoints, yPoints);
		if (getMarker() != null) {
			super.drawLegendSymbol(g2, width, height, style);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cern.jdve.ChartRenderer#draw(java.awt.Graphics2D,
	 * cern.jdve.utils.DisplayPoints, cern.jdve.Style)
	 */
	protected void draw(Graphics2D g2, DisplayPoints points,
			Style defaultStyle, DataSet dataSet) {

		/*
		 * The points must alternate, so if division by zero is not null, then
		 * the first point is a end-point and we start the drawing at the next
		 * one.
		 */
		int startIndex = 0;
		if ((points.getIndexRange().getMin() % 2) != 0) {
			startIndex = 1;
		}

		/* First we draw the polylines with the current style */
		for (int i = startIndex + 1; i < points.getXValues().length; i += 2) {
			int[] xValues = new int[] { points.getXValues()[i - 1],
					points.getXValues()[i] };
			int[] yValues = new int[] { points.getYValues()[i - 1],
					points.getYValues()[i] };
			defaultStyle.drawPolyline(g2, xValues, yValues);
		}

		/* then we draw the markers */
		super.draw(g2, points, defaultStyle, dataSet);

	}
}

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
package cern.accsoft.steering.jmad.gui.dv;

import cern.jdve.Chart;
import cern.jdve.data.DataSource;
import cern.jdve.graphic.Marker;
import cern.jdve.graphic.MarkerFactory;
import cern.jdve.interactor.CoordinatesPane;
import cern.jdve.interactor.DataPickerInteractor;
import cern.jdve.interactor.ScrollInteractor;
import cern.jdve.interactor.ZoomInteractor;
import cern.jdve.renderer.PolylineChartRenderer;

/**
 * This class provides methods to create charts for JMad
 * 
 * @author kfuchsbe
 * 
 */
public abstract class ChartFactory {

	/**
	 * creates a chart from a datasource with the default interactors for JMad.
	 * 
	 * @param ds
	 *            the DataSource
	 * @return the chart
	 */
	public Chart createMarkablePolyLineChart(DataSource ds) {
		MarkableChart chart = createMarkableChart();
		configurePolylineChart(ds, chart);

		/* initialize the markers, after the dataSource is set. */
		chart.initMarkers();

		return chart;
	}

	/**
	 * configures a chart with the default interactors for JMad
	 * 
	 * @param ds
	 *            the dataset to display
	 * @param chart
	 *            the chart to configure
	 */
	public void configurePolylineChart(DataSource ds, Chart chart) {
		PolylineChartRenderer renderer = createPolyLineRenderer(ds);
		chart.addRenderer(renderer);

		chart.setLegendVisible(true);

		chart.addInteractor(new ZoomInteractor());
		chart.addInteractor(new ScrollInteractor());

		DataPickerInteractor dataPicker = new DataPickerInteractor();
		dataPicker.setPointCoordPane(new AccurateCoordinatesPane());
		dataPicker.setDisplayDataSetName(false);
		dataPicker.setCursorCoordPainted(false);
		dataPicker.getPointCoordPane().setXCoordDisplay(
				CoordinatesPane.DATA_LABEL);
		chart.addInteractor(dataPicker);
	}

	public PolylineChartRenderer createPolyLineRenderer(DataSource ds) {
		PolylineChartRenderer renderer = new PolylineChartRenderer(
				MarkerFactory.getMarker(Marker.CIRCLE));
		renderer.setMarkerSize(3);
		renderer.setDataSource(ds);
		return renderer;
	}

	/**
	 * this method shall create a markable chart, which reacts on elements to be
	 * shown as markers in the chart.
	 * 
	 * this method is injected by spring.
	 * 
	 * @return the MarkableChart
	 */
	public abstract MarkableChart createMarkableChart();
}

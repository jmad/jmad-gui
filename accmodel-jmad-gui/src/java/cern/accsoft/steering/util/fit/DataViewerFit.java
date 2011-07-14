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
package cern.accsoft.steering.util.fit;

import cern.jdve.Chart;
import cern.jdve.data.DataSet;
import cern.jdve.utils.DataRange;

/**
 * This interface defines the methods necessary to display the result of the fit
 * in a DataViewer
 * 
 * @author kfuchsbe
 * 
 */
public interface DataViewerFit {

	/**
	 * must return a {@link DataSet} for the result of the fit.
	 * 
	 * @param xRange
	 *            the Range for which to calculate the values for the fitted
	 *            function
	 * @param nPoints
	 *            the amount of points to be calculated in the range
	 * @return the {@link DataSet} ready to display in the {@link Chart}
	 */
	public DataSet getResultDataSet(DataRange xRange, int nPoints);
}

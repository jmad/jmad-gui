/*
 * $Id: DataViewerFit.java,v 1.1 2008-09-07 01:00:02 kfuchsbe Exp $
 * 
 * $Date: 2008-09-07 01:00:02 $ 
 * $Revision: 1.1 $ 
 * $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
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

/*
 * $Id: ValidityDataSet.java,v 1.3 2009-02-25 18:48:42 kfuchsbe Exp $
 * 
 * $Date: 2009-02-25 18:48:42 $ 
 * $Revision: 1.3 $ 
 * $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.util.gui.dv.ds;

/**
 * This Interface must be implemented by all datasets, which shall allow a
 * different style for different datapoints.
 * 
 * @author kfuchsbe
 * 
 */
public interface ValidityDataSet {

	/**
	 * return the style for a given Datapoint
	 * 
	 * @param index
	 *            the index for which to retrieve the style
	 * @return the Style
	 */
	boolean getValidity(int index);

	/**
	 * should return, if validity-information is available, false otherwise.
	 * 
	 * @return true, if validity information is available, false otherwise
	 */
	boolean hasValidityInformation();
}

/*
 * $Id: MarkerXProvider.java,v 1.1 2009-02-25 18:48:36 kfuchsbe Exp $
 * 
 * $Date: 2009-02-25 18:48:36 $ 
 * $Revision: 1.1 $ 
 * $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.gui.mark;

import java.util.List;

/**
 * This interface should be e.g. implemented by a dataset, which then can
 * determine the x position for a marker
 * 
 * @author kfuchsbe
 * 
 */
public interface MarkerXProvider {

	/**
	 * this is special key can be used to return the x-position of border
	 * between the range of x- and y-values (useful for charts, that contain
	 * both)
	 */
	public final static String ELEMENT_NAME_HV_BORDER = "BORDER_BETWEEN_HORIZONTAL_AND_VERTICAL_PLANE_RANGE";

	/**
	 * @param elementName
	 *            the element for which to calc for which to calc the x-position
	 * @return the x-positions for the element
	 */
	public List<Double> getXPositions(String elementName);
}

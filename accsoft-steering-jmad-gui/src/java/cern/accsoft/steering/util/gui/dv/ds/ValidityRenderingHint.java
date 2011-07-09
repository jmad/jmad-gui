/*
 * $Id: ValidityRenderingHint.java,v 1.5 2009-03-16 16:38:11 kfuchsbe Exp $
 * 
 * $Date: 2009-03-16 16:38:11 $ 
 * $Revision: 1.5 $ 
 * $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.util.gui.dv.ds;

import cern.jdve.Style;
import cern.jdve.data.DataSet;
import cern.jdve.graphic.RenderingHint;
import cern.jdve.utils.DisplayPoint;

/**
 * This class defines, how the value for a BPM will be rendered.
 * 
 * @author kfuchsbe
 * 
 */
public class ValidityRenderingHint implements RenderingHint {

	/* the two different styles we use */
	private final static Style STYLE_INVALID = new Style(
			ColorConstants.COLOR_INVALID_DATA,
			ColorConstants.COLOR_INVALID_DATA);

	@Override
	public Style getStyle(DisplayPoint point, Style defaultStyle) {
		DataSet dataSet = point.getDataSet();

		/*
		 * check which basic style to use
		 */
		Style style = defaultStyle;
		if ((dataSet instanceof ValidityDataSet)
				&& (((ValidityDataSet) dataSet).hasValidityInformation())) {
			if (!((ValidityDataSet) dataSet).getValidity(point.getIndex())) {
				style = STYLE_INVALID;
			}
		}

		return style;
	}
}

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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import cern.jdve.interactor.CoordinatesPane;
import cern.jdve.utils.DisplayPoint;

public class AccurateCoordinatesPane extends CoordinatesPane {
	
	private static final long serialVersionUID = 1L;
	private NumberFormat numberFormat;
	
	
	public AccurateCoordinatesPane() {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
	    numberFormat = new DecimalFormat("#.########", symbols);		
	}
	protected String computeYLabel(DisplayPoint displayPoint) {
		return numberFormat.format(displayPoint.getY());
	}
}

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
package cern.accsoft.steering.jmad.gui.components;

import java.text.NumberFormat;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * This is a renderer for table-cells, that renders doiuble values with more
 * digits after the comma than the standard renderer.
 * 
 * @author kaifox
 * 
 */
public class DoubleTableCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = -5226757407025454017L;

	/** the number of digits we use for the format */
	private final static int NUMBER_DIGITS = 10;

	/** the format as static variable */
	private final static NumberFormat NUMBER_FORMAT;

	/**
	 * initialization block for the number - format
	 */
	static {
		NUMBER_FORMAT = NumberFormat.getInstance();
		NUMBER_FORMAT.setMaximumFractionDigits(NUMBER_DIGITS);
	}

	/**
	 * constructor, which just configures the alignment
	 */
	public DoubleTableCellRenderer() {
		super();
		setHorizontalAlignment(SwingConstants.RIGHT);
	}

	/**
	 * the overridden conversion - method.
	 */
	@Override
	public void setValue(Object value) {
		setText((value == null) ? "" : NUMBER_FORMAT.format(value));
	}
}
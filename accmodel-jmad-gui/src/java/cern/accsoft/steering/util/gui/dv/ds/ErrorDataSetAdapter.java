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
package cern.accsoft.steering.util.gui.dv.ds;

import cern.jdve.data.AbstractDataSet;
import cern.jdve.data.DataSet;
import cern.jdve.data.DataSource;
import cern.jdve.data.DefaultDataSource;

/**
 * @author kfuchsbe
 * 
 */
public class ErrorDataSetAdapter extends AbstractDataSet implements
		ValidityDataSet, DataSet {

	private ErrorDataSet delegate;

	/** the mode, which determines, what has to be done with the errors. */
	private ErrorAddMode errorAddMode = ErrorAddMode.OFF;

	/**
	 * this enum determines, if the errors should be added (PLUS) or subtracted
	 * (MINUS) from the y-values. When set to OFF, the errors are not taken into
	 * account.
	 * 
	 * @author kfuchsbe
	 * 
	 */
	public enum ErrorAddMode {
		PLUS, MINUS, OFF;
	}

	public ErrorDataSetAdapter(ErrorDataSet delegate, ErrorAddMode errorAddMode) {
		super(delegate.getName() + "-error (" + errorAddMode.toString() + ")");
		this.delegate = delegate;
		this.errorAddMode = errorAddMode;
	}

	public double getY(int index) {
		double value = delegate.getY(index);

		Double errorValue = delegate.getYError(index);
		if (errorValue != null) {
			if (ErrorAddMode.PLUS.equals(this.errorAddMode)) {
				value += errorValue;
			} else if (ErrorAddMode.MINUS.equals(this.errorAddMode)) {
				value -= errorValue;
			}
		}
		return value;
	}

	@Override
	public boolean getValidity(int index) {
		return delegate.getValidity(index);
	}

	@Override
	public boolean hasValidityInformation() {
		return delegate.hasValidityInformation();
	}

	@Override
	public void add(int index, double x, double y) {
		delegate.add(index, x, y);
	}

	@Override
	public int getDataCount() {
		return delegate.getDataCount();
	}

	@Override
	public double getX(int index) {
		return delegate.getX(index);
	}

	@Override
	public void remove(int fromIndex, int toIndex) {
		delegate.remove(fromIndex, toIndex);
	}

	/**
	 * creates a DefaultDataSource with one dataset containing the dataset with
	 * plus errors and one with minus errors
	 * 
	 * @param dataSet
	 * @return
	 */
	public final static DataSource createDefaultErrorDataSource(
			ErrorDataSet dataSet) {
		return new DefaultDataSource(new DataSet[] {
				new ErrorDataSetAdapter(dataSet, ErrorAddMode.PLUS),
				new ErrorDataSetAdapter(dataSet, ErrorAddMode.MINUS) });
	}
}

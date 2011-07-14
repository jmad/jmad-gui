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

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.gui.mark.MarkerXProvider;
import cern.jdve.data.AbstractDataSet;
import cern.jdve.event.DataSetEvent;

/**
 * This class is the basic implementation of our DataSets
 * 
 * @author kfuchsbe
 * 
 */
public abstract class AbstractJmadDataSet extends AbstractDataSet implements
		MarkerXProvider {

	/** the labels to be displayed */
	private List<String> labels = new ArrayList<String>();

	/**
	 * the double-values for the x-values. This is null on purpose. If it is
	 * null, then the index is returned as x - value, otherwise the value in
	 * this list.
	 */
	private List<Double> yValues = null;

	/**
	 * the constructor, which needs the name of the dataset
	 * 
	 * @param name
	 *            the name of the dataset
	 */
	protected AbstractJmadDataSet(String name) {
		super(name);
	}

	@Override
	public void add(int arg0, double arg1, double arg2) {
		throw new UnsupportedOperationException("Not implemented!");
	}

	@Override
	public void remove(int arg0, int arg1) {
		throw new UnsupportedOperationException("Not implemented!");
	}

	/**
	 * enforces a refresh.
	 */
	public void refresh() {
		fireFullChange();
	}

	/**
	 * fires a full-change event to all listeners.
	 */
	protected final void fireFullChange() {
		setLimitsValid(false);
		fireDataSetChanged(new DataSetEvent(this, DataSetEvent.FULL_CHANGE));
	}

	@Override
	public String getDataLabel(int index) {
		if ((labels != null) && (labels.size() > index)) {
			return labels.get(index);
		} else {
			return null;
		}
	}

	/**
	 * @param labels
	 *            the labels, which correspond to the indizes
	 */
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	/**
	 * @return the actual data-labels
	 */
	public List<String> getLabels() {
		return labels;
	}

	@Override
	public double getX(int index) {
		if (this.getXValues() != null) {
			Double value = this.getXValues().get(index);
			if (value == null) {
				return 0;
			} else {
				return value;
			}
		} else {
			return index;
		}
	}

	@Override
	public List<Double> getXPositions(String elementName) {
		/* we assume, that the labels are the element-names */
		List<Integer> indizes = getLabelIndizes(elementName);
		List<Double> xValues = new ArrayList<Double>();
		for (Integer index : indizes) {
			xValues.add(getX(index));
		}
		return xValues;
	}

	/**
	 * search in the labels and return the indizes of the given label
	 * 
	 * @param label
	 *            the label for which to search the index
	 * @return the indizes of the label
	 */
	private List<Integer> getLabelIndizes(String label) {
		List<Integer> indizes = new ArrayList<Integer>();
		if (label == null) {
			return indizes;
		}

		for (int i = 0; i < getDataCount(); i++) {
			if (label.equals(getDataLabel(i))) {
				indizes.add(i);
			}
		}
		return indizes;
	}

	protected void setXValues(List<Double> xValues) {
		this.yValues = xValues;
	}

	public final List<Double> getXValues() {
		return yValues;
	}

}

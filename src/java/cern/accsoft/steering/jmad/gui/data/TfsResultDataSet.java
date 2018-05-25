// @formatter:off
/*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on

package cern.accsoft.steering.jmad.gui.data;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.result.tfs.TfsResult;
import cern.accsoft.steering.jmad.domain.var.MadxVariable;
import cern.accsoft.steering.jmad.domain.var.TwissVariable;
import cern.accsoft.steering.jmad.domain.var.enums.MadxTwissVariable;
import cern.accsoft.steering.jmad.gui.mark.MarkerXProvider;
import cern.jdve.data.AbstractDataSet;
import cern.jdve.event.DataSetEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TfsResultDataSet extends AbstractDataSet implements
		MarkerXProvider {
	private final static Logger logger = LoggerFactory.getLogger(TfsResultDataSet.class);

	public final static MadxTwissVariable LABEL_VAR = MadxTwissVariable.NAME;

	/** the limit for zero-definition */
	private final static double LIMIT_ZERO = 1e-20;

	/** maximum value for aperture */
	private double maxAperture = 0.0;

	/** the actual tfsResult to display */
	private TfsResult tfsResult = null;

	/** the reference tfsResult (if available) */
	private TfsResult referenceTfsResult = null;

	/** an additional factor to apply to the values */
	private double factor = 1;

	/**
	 * how to display the data set
	 */
	private TfsResultDataSetType type = TfsResultDataSetType.ABSOLUTE;

	/** the cached x-values */
	private List<Double> xValues = new ArrayList<Double>();

	/** the cached y-values */
	private List<Double> yValues = new ArrayList<Double>();

	private MadxVariable xVar = null;
	private TwissVariable yVar = null;

	public enum TfsResultDataSetType {
		ABSOLUTE, DIFFERENCE, RELATIVE, BEATING;
	}

	public TfsResultDataSet(String name, MadxVariable xVar, TwissVariable yVar,
			TfsResultDataSetType type, TfsResult tfsResult,
			TfsResult referenceTfsResult) {
		super(name);
		this.xVar = xVar;
		this.yVar = yVar;
		this.type = type;
		this.tfsResult = tfsResult;
		this.referenceTfsResult = referenceTfsResult;
		calculate();
	}

	@Override
	public void add(int arg0, double arg1, double arg2) {
		throw new UnsupportedOperationException("not implemented.");
	}

	@Override
	public int getDataCount() {
		if (tfsResult == null) {
			return 0;
		}

		List<String> stringData = tfsResult.getStringData(xVar);
		if (stringData != null) {
			return stringData.size();
		}
		return 0;
	}

	@Override
	public double getX(int index) {
		return this.xValues.get(index);
	}

	@Override
	public double getY(int index) {
		return this.yValues.get(index);
	}

	@Override
	public void remove(int arg0, int arg1) {
		throw new UnsupportedOperationException("not implemented.");
	}

	@Override
	public String getDataLabel(int index) {
		if (tfsResult == null) {
			return null;
		}

		List<String> stringData = tfsResult.getStringData(LABEL_VAR);
		if (stringData != null) {
			return stringData.get(index);
		} else {
			logger.error("No data in TfsResult for variable '" + LABEL_VAR
					+ "'.");
		}
		return null;
	}

	/**
	 * (re) calculates all the cached values
	 */
	private void calculate() {
		if (this.tfsResult == null) {
			logger.error("No tfs result set.");
			return;
		}

		List<Double> xVals = tfsResult.getDoubleData(this.xVar);
		List<Double> yVals = tfsResult.getDoubleData(this.yVar);
		if ((xVals == null) || (yVals == null)) {
			logger.error("No x- or no y- values found in tfs result.");
			return;
		}

		this.xValues = new ArrayList<Double>(xVals);
		this.yValues = new ArrayList<Double>(yVals);

		if (isApertureDataSet()) {
			calcMaxAperture(yVals);
			this.yValues = new ArrayList<Double>(yVals.size());
			for (int i = 0; i < yVals.size(); i++) {
				Double value = yVals.get(i);
				double absValue = Math.abs(value);
				if (absValue > getMaxAperture()) {
					value = Math.signum(value) * getMaxAperture();
				} else if (absValue < LIMIT_ZERO) {
					value = getMaxAperture();
				}
				this.yValues.add(value);
			}
		} else if (isRelative()) {
			boolean refOk = true;
			List<Double> yRefValues = null;

			if (this.referenceTfsResult == null) {
				logger.warn("Should be relative dataset, but no reference set. Data will be absolute.");
				refOk = false;
			}

			if (refOk) {
				yRefValues = this.referenceTfsResult.getDoubleData(this.yVar);
				if (yRefValues == null) {
					logger.warn("No reference values for variable '"
							+ this.yVar + "'. Data will be absolute.");
					refOk = false;
				}
			}

			if (refOk) {
				if (yVals.size() != yRefValues.size()) {
					logger.warn("y-values and ref values are of different size. Data will be absolute.");
					refOk = false;
				}
			}

			if (refOk) {
				this.yValues = new ArrayList<Double>(yVals.size());
				for (int i = 0; i < yVals.size(); i++) {
					Double value = yVals.get(i);
					Double refValue = yRefValues.get(i);
					if (refValue != null) {
						if (TfsResultDataSetType.DIFFERENCE.equals(this.type)) {
							value -= refValue;
						} else if (TfsResultDataSetType.RELATIVE
								.equals(this.type)) {
							value /= refValue;
						} else if (TfsResultDataSetType.BEATING
								.equals(this.type)) {
							value = (value - refValue) / refValue;
						}
					}
					this.yValues.add(value);
				}
			}
		}

		for (int i = 0; i < this.yValues.size(); i++) {
			this.yValues.set(i, this.yValues.get(i) * getFactor());
		}
	}

	/**
	 * @return the calculated maximum aperture
	 */
	private double getMaxAperture() {
		return this.maxAperture;
	}

	/**
	 * sets the actual dataset
	 * 
	 * @param tfsResult
	 *            the Result to set
	 */
	public void setTfsResult(TfsResult tfsResult) {
		this.tfsResult = tfsResult;
		fireFullChange();
	}

	/**
	 * sets a new TfsResult as reference
	 * 
	 * @param referenceTfsResult
	 *            the TfsResult to set as Reference
	 */
	public void setReferenceTfsResult(TfsResult referenceTfsResult) {
		this.referenceTfsResult = referenceTfsResult;
		if (isRelative()) {
			fireFullChange();
		}
	}

	/**
	 * @return true, if this dataset is any kind of relative dataset
	 */
	private boolean isRelative() {
		return (!TfsResultDataSetType.ABSOLUTE.equals(this.type));
	}

	/**
	 * enforces the recalc of the ranges and fires a fullChange - Event.
	 */
	private void fireFullChange() {
		calculate();
		initRanges();
		fireDataSetChanged(new DataSetEvent(this, DataSetEvent.FULL_CHANGE));
	}

	private void calcMaxAperture(List<Double> values) {
		double maxAbsValue = 0;
		for (int i = 0; i < values.size(); i++) {
			double value = Math.abs(values.get(i));
			if (value > maxAbsValue) {
				maxAbsValue = value;
			}
		}
		this.maxAperture = maxAbsValue;
	}

	@Override
	public List<Double> getXPositions(String elementName) {
		List<Double> xPositions = new ArrayList<Double>();
		if (tfsResult == null) {
			return xPositions;
		}

		/* get the actual value */
		/*
		 * TODO: maybe extend to more values
		 */
		Integer index = tfsResult.getElementIndex(elementName);
		if (index != null) {
			xPositions.add(xValues.get(index));
		}

		return xPositions;
	}

	/**
	 * @param factor
	 *            the factor to set
	 */
	public void setFactor(double factor) {
		this.factor = factor;
	}

	/**
	 * @return the factor
	 */
	public double getFactor() {
		return factor;
	}

	/**
	 * @return true, if this represents an aperture-dataset.
	 */
	public boolean isApertureDataSet() {
		return this.yVar.isApertureVariable();
	}
}

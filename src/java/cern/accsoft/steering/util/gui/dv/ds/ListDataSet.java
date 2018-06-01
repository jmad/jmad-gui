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

package cern.accsoft.steering.util.gui.dv.ds;

import java.util.ArrayList;
import java.util.List;

/**
 * this class represents a dataset for a list of double-values. The x-values are then simply the indizes
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class ListDataSet extends AbstractJmadDataSet implements ErrorDataSet {

    /** the double-values for the y-values */
    private List<Double> yValues = new ArrayList<Double>();

    /** this list can be set to add additional errors to the yValues. */
    private List<Double> yErrorValues = null;

    /**
     * the boolean values, which indicate the validity of the datapoints. this is null on purpose! if its null, then all
     * datapoints are valid.
     */
    private List<Boolean> validityValues = null;

    /**
     * the constructor, which needs a name for the dataset
     * 
     * @param name the name of the dataset
     */
    public ListDataSet(String name) {
        super(name);
        setUndefValue(Double.NaN);
    }

    @Override
    public int getDataCount() {
        return yValues.size();
    }

    @Override
    public double getY(int index) {
        return yValues.get(index);
    }

    /**
     * @return the list of y-values
     */
    public List<Double> getYValues() {
        return this.yValues;
    }

    /**
     * sets x- and y- values simultanously. This only can be done together. So the length of them is consistent and the
     * refresh is only triggered once.
     * 
     * @param xValues the values for the x-axis
     * @param yValues the values for the y-axis
     */
    public void setValues(List<Double> xValues, List<Double> yValues) {
        this.setXValues(xValues);
        this.yValues = yValues;
        fireFullChange();
    }

    /**
     * sets x- and y- values simultaniously. This only can be done together. So the length of them is consistent and the
     * refresh is only triggered once.
     * 
     * @param xValues the values for the x-axis
     * @param yValues the values for the y-axis
     */
    public void setValues(List<Double> xValues, List<Double> yValues, List<Boolean> validityValues) {
        this.setXValues(xValues);
        this.yValues = yValues;
        this.validityValues = validityValues;
        fireFullChange();
    }

    public void setValues(List<Double> xValues, List<Double> yValues, List<Double> yErrorValues,
            List<Boolean> validityValues) {
        this.setXValues(xValues);
        this.yValues = yValues;
        this.yErrorValues = yErrorValues;
        this.validityValues = validityValues;
        fireFullChange();
    }

    /**
     * @param yValues the yValues to set
     */
    public void setYValues(List<Double> yValues) {
        this.yValues = yValues;
        fireFullChange();
    }

    @Override
    public boolean getValidity(int index) {
        if (getValidityValues() == null) {
            return true;
        } else {
            return getValidityValues().get(index);
        }
    }

    /**
     * @return the validityValues
     */
    private List<Boolean> getValidityValues() {
        return validityValues;
    }

    @Override
    public boolean hasValidityInformation() {
        return (this.validityValues != null);
    }

    /**
     * @return the yErrorValues
     */
    public List<Double> getYErrorValues() {
        return yErrorValues;
    }

    @Override
    public Double getYError(int index) {
        if ((this.yErrorValues != null) && (index < this.yErrorValues.size())) {
            return this.yErrorValues.get(index);
        } else {
            return null;
        }
    }

}

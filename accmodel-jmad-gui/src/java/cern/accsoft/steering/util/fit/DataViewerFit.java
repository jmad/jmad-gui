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

package cern.accsoft.steering.util.fit;

import cern.jdve.Chart;
import cern.jdve.data.DataSet;
import cern.jdve.utils.DataRange;

/**
 * This interface defines the methods necessary to display the result of the fit in a DataViewer
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public interface DataViewerFit {

    /**
     * must return a {@link DataSet} for the result of the fit.
     * 
     * @param xRange the Range for which to calculate the values for the fitted function
     * @param nPoints the amount of points to be calculated in the range
     * @return the {@link DataSet} ready to display in the {@link Chart}
     */
    public DataSet getResultDataSet(DataRange xRange, int nPoints);
}

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

package cern.accsoft.steering.jmad.gui.manage;

import java.util.List;
import java.util.Map;

import cern.accsoft.steering.jmad.domain.result.tfs.TfsResult;
import cern.accsoft.steering.jmad.gui.data.TfsResultDataSet;

/**
 * the interface for listeners on {@link DataSetManager}
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public interface DataSetManagerListener {

    /**
     * called when a new set of datasets was created.
     * 
     * @param dataSets the new datasets
     */
    public void createdDataSets(String name, String label, Map<Integer, List<TfsResultDataSet>> dataSets);

    /**
     * called when new twiss values were calculated
     * 
     * @param tfsResult the result from the last twiss calculation
     */
    public void twissCalculated(TfsResult tfsResult);
}

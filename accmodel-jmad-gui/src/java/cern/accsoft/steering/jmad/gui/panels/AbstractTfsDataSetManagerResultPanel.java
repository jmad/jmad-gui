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

package cern.accsoft.steering.jmad.gui.panels;

import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import cern.accsoft.steering.jmad.domain.result.tfs.TfsResult;
import cern.accsoft.steering.jmad.gui.data.TfsResultDataSet;
import cern.accsoft.steering.jmad.gui.manage.DataSetManager;
import cern.accsoft.steering.jmad.gui.manage.DataSetManagerListener;

public abstract class AbstractTfsDataSetManagerResultPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /**
     * listens for updates and triggers a refresh
     */
    private DataSetManagerListener dataSetManagerListener = new DataSetManagerListener() {

        @Override
        public void twissCalculated(TfsResult tfsResult) {
            update(tfsResult);
        }

        @Override
        public void createdDataSets(String name, String label, Map<Integer, List<TfsResultDataSet>> dataSets) {
            /* nothing to do here */
        }
    };

    public void setDataSetManager(DataSetManager dataSetManager) {
        if (dataSetManager != null) {
            dataSetManager.addListener(this.dataSetManagerListener);
        }
    }

    protected abstract void update(TfsResult tfsResult);

}
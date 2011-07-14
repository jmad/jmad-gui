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
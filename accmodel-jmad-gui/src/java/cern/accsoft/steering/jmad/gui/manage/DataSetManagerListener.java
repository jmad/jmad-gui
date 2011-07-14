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
package cern.accsoft.steering.jmad.gui.manage;

import java.util.List;
import java.util.Map;

import cern.accsoft.steering.jmad.domain.result.tfs.TfsResult;
import cern.accsoft.steering.jmad.gui.data.TfsResultDataSet;

/**
 * the interface for listeners on {@link DataSetManager}
 * 
 * @author kfuchsbe
 * 
 */
public interface DataSetManagerListener {

	/**
	 * called when a new set of datasets was created.
	 * 
	 * @param dataSets
	 *            the new datasets
	 */
	public void createdDataSets(String name, String label,
			Map<Integer, List<TfsResultDataSet>> dataSets);

	/**
	 * called when new twiss values were calculated
	 * 
	 * @param tfsResult
	 *            the result from the last twiss calculation
	 */
	public void twissCalculated(TfsResult tfsResult);
}

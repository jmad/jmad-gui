/*
 * $Id: DataSetManagerListener.java,v 1.3 2009-03-16 16:36:33 kfuchsbe Exp $
 * 
 * $Date: 2009-03-16 16:36:33 $ 
 * $Revision: 1.3 $ 
 * $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
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

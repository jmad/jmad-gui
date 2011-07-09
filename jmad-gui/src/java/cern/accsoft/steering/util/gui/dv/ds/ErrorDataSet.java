package cern.accsoft.steering.util.gui.dv.ds;

import cern.jdve.data.DataSet;

public interface ErrorDataSet extends ValidityDataSet, DataSet {
	
	
	Double getYError(int index);

}

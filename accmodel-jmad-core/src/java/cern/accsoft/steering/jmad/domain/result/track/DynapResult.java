package cern.accsoft.steering.jmad.domain.result.track;

import java.util.List;

import cern.accsoft.steering.jmad.domain.result.Result;
import cern.accsoft.steering.jmad.domain.var.enums.MadxDynapVariable;

/**
 * 
 * This interface allow to manage informations contained in the output of a dynap task.
 * Information in the summary are discarded.
 * 
 * @author xbuffat
 *
 */

public interface DynapResult extends Result{

	/**
	 * clear the result
	 */
	void clear();

	/**
	 * store a value associated to the variable
	 * 
	 * @param variable
	 * @param value
	 */
	void add(MadxDynapVariable variable, Double value);
	
	
	/**
	 * retrieve data associated to the variable.
	 * 
	 * @param variable
	 * @return all values for the variable
	 */
	 List<Double> get(MadxDynapVariable variable);

}

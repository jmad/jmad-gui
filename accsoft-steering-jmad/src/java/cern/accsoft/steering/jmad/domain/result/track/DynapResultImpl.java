package cern.accsoft.steering.jmad.domain.result.track;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cern.accsoft.steering.jmad.domain.result.ResultType;
import cern.accsoft.steering.jmad.domain.var.enums.MadxDynapVariable;

/**
 * Standard implementation of DynapResult
 * 
 * @author xbuffat
 *
 */
public class DynapResultImpl implements DynapResult {

	Map<MadxDynapVariable,List<Double>> data;
	
	public DynapResultImpl() {
		this.data = new HashMap<MadxDynapVariable,List<Double>>();
		for(MadxDynapVariable var : MadxDynapVariable.values())
		{
			this.data.put(var, new ArrayList<Double>());
		}
	}
	
	@Override
	public ResultType getResultType() {
		return ResultType.DYNAP_RESULT;
	}

	@Override
	public void add(MadxDynapVariable var, Double value) {
		this.data.get(var).add(value);
		
	}

	@Override
	public void clear() {
		for(MadxDynapVariable var : MadxDynapVariable.values())
		{
			this.data.get(var).clear();
		}
	}

	@Override
	public List<Double> get(MadxDynapVariable variable) {
		return this.data.get(variable);
	}

}

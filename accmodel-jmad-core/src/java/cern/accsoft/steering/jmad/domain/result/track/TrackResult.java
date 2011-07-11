package cern.accsoft.steering.jmad.domain.result.track;

import java.util.List;

import cern.accsoft.steering.jmad.domain.result.Result;
import cern.accsoft.steering.jmad.domain.var.enums.MadxTrackVariable;

/**
 * 
 * This interface allow to manage data contained in the output of a track task.
 * 
 * @author xbuffat
 *
 */

public interface TrackResult extends Result {

	/**
	 * 
	 * @return number of particle tracked
	 */
	Integer getParticleCount();
	
	/**
	 * 
	 * Store the value associated to a variable for a particle
	 * 
	 * @param variable
	 * @param particle integer associated to the tracked particle
	 * @param value
	 */
	void add(MadxTrackVariable variable,Integer particle, Double value);
	
	/**
	 * 
	 * @param variable
	 * @param particle integer associated to the tracked particle from 0 to getParticleCount()
	 * @return values associated to a variable for a particle
	 */
	List<Double> get(MadxTrackVariable variable, Integer particle);
	
	/**
	 * clear the result
	 */
	void clear();
}

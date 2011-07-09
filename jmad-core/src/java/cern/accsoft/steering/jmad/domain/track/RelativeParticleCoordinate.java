package cern.accsoft.steering.jmad.domain.track;

/**
 * 
 * describes the position in 6D phase space of a particle relatively to a reference one.
 * 
 * @author xbuffat
 *
 */

public interface RelativeParticleCoordinate {

	/**
	 * 
	 * @return X
	 */
	double getXRelatviePosition();
	/**
	 * 
	 * @return PX = px/p0
	 */
	double getXRelativeMomentum();
	/**
	 * 
	 * @return Y
	 */
	double getYRelativePosition();
	/**
	 * 
	 * @return PY = py/p0
	 */
	double getYRelatvieMomentum();
	
	/**
	 * 
	 * @return T = -c*t
	 */
	double getRelativeTimeDifference();
	
	/**
	 * 
	 * @return PT = delta(E)/ps*c
	 */
	double getRelativeEnergyError();
	

	void setXRelatviePosition(double x);
	void setXRelativeMomentum(double px);
	void setYRelativePosition(double y);
	void setYRelatvieMomentum(double py);
	void setRelativeTimeDiffence(double t);
	void setRelativeEnergyError(double pt);
}

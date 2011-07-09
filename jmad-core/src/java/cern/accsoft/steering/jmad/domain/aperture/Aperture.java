package cern.accsoft.steering.jmad.domain.aperture;

import java.util.List;

import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;

public interface Aperture {

    /**
     * @return the s-Positions of the ApertureSequence
     */
    public List<Double> getSValues();

    /**
     * @param aroundTrajectory if <code>true</code> the aperture around the nominal beam trajectory is returned,
     *            otherwise the aperture as defined.
     * @return the minimum values in the x-plane
     */
    public abstract List<Double> getXminValues(boolean aroundTrajectory);

    /**
     * @param aroundTrajectory if <code>true</code> the aperture around the nominal beam trajectory is returned,
     *            otherwise the aperture as defined.
     * @return the maximum values in the x-plane
     */
    public abstract List<Double> getXmaxValues(boolean aroundTrajectory);

    /**
     * @param aroundTrajectory if <code>true</code> the aperture around the nominal beam trajectory is returned,
     *            otherwise the aperture as defined.
     * @return the minimum values in the y plane
     */
    public abstract List<Double> getYminValues(boolean aroundTrajectory);

    /**
     * @param aroundTrajectory if <code>true</code> the aperture around the nominal beam trajectory is returned,
     *            otherwise the aperture as defined.
     * @return the maximum values in the y plane
     */
    public abstract List<Double> getYmaxValues(boolean aroundTrajectory);

    /**
     * get the minimum values for the given plane
     * 
     * @param plane the plane for which to retrieve the minValues
     * @param aroundTrajectory if <code>true</code> the aperture around the nominal beam trajectory is returned,
     *            otherwise the aperture as defined.
     * @return the minimum Values for the given plane
     */
    public abstract List<Double> getMinValues(JMadPlane plane, boolean aroundTrajectory);

    /**
     * get the maximum values for the given plane
     * 
     * @param plane the plane for which to retrieve the maximum Values
     * @param aroundTrajectory if <code>true</code> the aperture around the nominal beam trajectory is returned,
     *            otherwise the aperture as defined.
     * @return the maximum values for the given plane
     */
    public abstract List<Double> getMaxValues(JMadPlane plane, boolean aroundTrajectory);

}
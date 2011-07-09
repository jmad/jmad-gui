/**
 * 
 */
package cern.accsoft.steering.jmad.domain.result.match;

import java.util.List;

import cern.accsoft.steering.jmad.domain.result.match.input.MadxVaryParameter;
import cern.accsoft.steering.jmad.domain.result.match.input.MatchConstraint;
import cern.accsoft.steering.jmad.domain.result.match.methods.MatchMethod;
import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditions;

/**
 * This class defines what results we want to have from a matching task
 * 
 * @author muellerg
 */
public interface MatchResultRequest {
    // TODO: think about RMATRIX and CHROM Flag of the MATCH Command

    // TODO: think about SaveBeta/InitialTwissConditions in the Frame of Matching
    // /**
    // * @return The Name of the MadX internally stored initial optical function
    // * values --> SAVEBETA
    // */
    // public abstract String getSaveBetaNames();
    //
    // /**
    // * @return The initial values of the optical functions for the matching if
    // * specified
    // */
    // public abstract TwissInitialConditions getInitialTwiss();

    /**
     * @return the Sequence Name that is going to me used for Matching
     */
    public abstract String getSequenceName();

    /**
     * @param sequenceName the Name of the Sequence used for Matching
     */
    public abstract void setSequenceName(String sequenceName);

    /**
     * @return all the parameters to use for matching
     */
    public abstract List<MadxVaryParameter> getMadxVaryParameters();

    /**
     * @return all the constraints for the matching
     */
    public abstract List<MatchConstraint> getMatchConstraints();

    /**
     * @return the method used for the matching
     */
    public abstract MatchMethod getMatchMethod();

    /**
     * @return The initial values of the optical functions for the insertion matching.
     */
    public abstract TwissInitialConditions getInitialOpticsValues();

    /**
     * @return the name of the previously saved optical functions values --> SAVEBETA
     */
    public abstract String getSaveBetaName();
}

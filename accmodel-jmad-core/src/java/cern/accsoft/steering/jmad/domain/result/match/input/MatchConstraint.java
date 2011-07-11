/**
 * 
 */
package cern.accsoft.steering.jmad.domain.result.match.input;

import java.util.List;
import java.util.Map;

/**
 * Thi interface which represents a general matching constraint for madx
 * 
 * @author muellerg
 */
public interface MatchConstraint {

    /**
     * @return true, if it is a global constraint
     */
    public abstract boolean isGlobal();

    /**
     * @return MadxName-Value Pairs for all defined Parameters of this constraint
     */
    public abstract Map<String, Double> getParameterSettings();

    /**
     * @return Madx values-command names to read the resulting value of the constraints after matching...
     *         <p>
     *         for local matching, if a range is specified, only names for first and last element of the range are
     *         returned
     */
    public abstract List<String> getReadoutNames();

}

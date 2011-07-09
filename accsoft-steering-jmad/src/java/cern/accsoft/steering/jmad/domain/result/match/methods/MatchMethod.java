/**
 * 
 */
package cern.accsoft.steering.jmad.domain.result.match.methods;

/**
 * this is the general type of a method for matching in madx.
 * 
 * @author muellerg
 */
public interface MatchMethod {

    /**
     * Available MadX supported matching algorithms which define the Type of the {@link MatchMethod}
     */
    public static enum AlgorithmType {
        LMDIF("lmdif"), MIGRAD("migrad"), SIMPLEX("simplex"), JACOBIAN("jacobian"), NONE("none");

        private String name;

        private AlgorithmType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    /**
     * @return the {@link AlgorithmType} of the MatchMethod
     */
    public abstract AlgorithmType getAlgorithmType();

    /**
     * @return the number of calls during the matching
     */
    public abstract int getCalls();

    /**
     * @return The desired tolerance for the minimum .
     */
    public abstract double getTolerance();

}

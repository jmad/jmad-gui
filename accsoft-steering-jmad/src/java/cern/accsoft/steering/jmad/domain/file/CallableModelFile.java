/**
 * 
 */
package cern.accsoft.steering.jmad.domain.file;

/**
 * a model file that will be called at initialization time
 * 
 * @author kaifox
 */
public interface CallableModelFile extends ModelFile {

    /**
     * This enum defines if the file will be parsed or not
     * 
     * @author kfuchsbe
     */
    public static enum ParseType {
        NONE, /* do not parse */
        STRENGTHS; /* parse for strengths */
    }

    /**
     * @return the parse type
     */
    public abstract ParseType getParseType();

}

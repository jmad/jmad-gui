/**
 * 
 */
package cern.accsoft.steering.jmad.domain.machine;

import java.util.List;

import cern.accsoft.steering.jmad.domain.beam.Beam;

/**
 * This class defines the attributes which define a sequence.
 * 
 * @author kfuchsbe
 */
public interface SequenceDefinition {

    /**
     * @return the name of the sequence
     */
    public String getName();

    /**
     * @return the range-definitions for this sequence
     */
    public List<RangeDefinition> getRangeDefinitions();

    /**
     * @return the rangeDefinition, which shall be selected by default
     */
    public RangeDefinition getDefaultRangeDefinition();

    /**
     * @return the beam to use for this sequence.
     */
    public Beam getBeam();

    /**
     * returns the range definition of the given name or null
     * 
     * @param name the name of the range definition to find
     * @return the range definition of the given name if available
     */
    public RangeDefinition getRangeDefinition(String name);
}

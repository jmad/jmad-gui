package cern.accsoft.steering.jmad.domain.machine;

import java.util.List;

import cern.accsoft.steering.jmad.domain.beam.Beam;

public interface Sequence {

    /**
     * @return the name
     */
    public abstract String getName();

    /**
     * @return the available ranges of the sequence.
     */
    public abstract List<Range> getRanges();

    /**
     * @return the default range for this sequence
     */
    public abstract Range getDefaultRange();

    /**
     * @return the beam
     */
    public abstract Beam getBeam();

    /**
     * @return the defining SequenceDefinition
     */
    public abstract SequenceDefinition getSequenceDefinition();

}
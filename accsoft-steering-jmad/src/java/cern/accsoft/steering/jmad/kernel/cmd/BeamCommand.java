package cern.accsoft.steering.jmad.kernel.cmd;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.beam.Beam;
import cern.accsoft.steering.jmad.domain.beam.Beam.Direction;
import cern.accsoft.steering.jmad.domain.beam.Beam.Particle;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * represents the madX 'beam' command BEAM, PARTICLE=name,MASS=real,CHARGE=real, ENERGY=real,PC=real,GAMMA=real,
 * EX=real,EXN=real,EY=real,EYN=real, ET=real,SIGT=real,SIGE=real, KBUNCH=integer,NPART=real,BCURRENT=real,
 * BUNCHED=logical,RADIATE=logical,BV=integer,SEQUENCE=name;
 * 
 * @author kfuchsbe
 */
public class BeamCommand extends AbstractCommand {
    private static final String CMD_NAME = "beam";

    private final Beam beam;

    public BeamCommand(Beam beam) {
        this.beam = beam;
    }

    @Override
    public String getName() {
        return CMD_NAME;
    }

    @Override
    public List<Parameter> getParameters() {
        ArrayList<Parameter> parameters = new ArrayList<Parameter>();

        /*
         * define the mapping of the member-fields to the parameters.
         */
        /*
         * for particle, there may be set throug enum or through a arbitrary string. DANGER: not checked, if boths are
         * set!
         */
        parameters.add(new GenericParameter<Particle>("particle", beam.getParticle()));
        parameters.add(new GenericParameter<String>("particle", beam.getParticleName()));

        parameters.add(new GenericParameter<Double>("mass", beam.getMass()));
        parameters.add(new GenericParameter<Double>("charge", beam.getCharge()));
        parameters.add(new GenericParameter<Double>("energy", beam.getEnergy()));
        parameters.add(new GenericParameter<Double>("pc", beam.getMomentum()));
        parameters.add(new GenericParameter<Double>("gamma", beam.getGamma()));
        parameters.add(new GenericParameter<Double>("ex", beam.getHorizontalEmittance()));
        parameters.add(new GenericParameter<Double>("ey", beam.getVerticalEmittance()));
        parameters.add(new GenericParameter<Double>("et", beam.getLongitudinalEmittance()));
        parameters.add(new GenericParameter<Double>("exn", beam.getNormalisedHorizontalEmittance()));
        parameters.add(new GenericParameter<Double>("eyn", beam.getNormalisedVerticalEmittance()));
        parameters.add(new GenericParameter<Double>("sigt", beam.getBunchLength()));
        parameters.add(new GenericParameter<Double>("sige", beam.getRelativeEnergySpread()));
        parameters.add(new GenericParameter<Integer>("kbunch", beam.getBunchNumber()));
        parameters.add(new GenericParameter<Double>("npart", beam.getParticleNumber()));
        parameters.add(new GenericParameter<Double>("bcurrent", beam.getBunchCurrent()));
        parameters.add(new GenericParameter<Boolean>("bunched", beam.getBunched()));
        parameters.add(new GenericParameter<Boolean>("radiate", beam.getRadiate()));
        parameters.add(new GenericParameter<Direction>("bv", beam.getDirection()));
        parameters.add(new GenericParameter<String>("sequence", beam.getSequence()));

        return parameters;
    }

}

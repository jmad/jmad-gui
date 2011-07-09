package cern.accsoft.steering.jmad.kernel.cmd.track;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.track.TrackInitialCondition;
import cern.accsoft.steering.jmad.kernel.cmd.AbstractCommand;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * command TRACK, onepass, deltap= double, dump,...;
 * 
 * @author xbuffat
 *
 */

public class TrackCommand extends AbstractCommand {

	private static final String CMD_NAME = "track";
	
	private final TrackInitialCondition trackInitialCondition;
	
	public TrackCommand(TrackInitialCondition init) {
		this.trackInitialCondition = init;
	}
	
	@Override
	public String getName() {
		return TrackCommand.CMD_NAME;
	}
	
    @Override
    public List<Parameter> getParameters() {
        ArrayList<Parameter> parameters = new ArrayList<Parameter>();
        
        parameters.add(new GenericParameter<Double>("deltap", this.trackInitialCondition.getDeltaP()));
        parameters.add(new GenericParameter<Boolean>("onepass",this.trackInitialCondition.isOnePass()));
        parameters.add(new GenericParameter<Boolean>("recloss",this.trackInitialCondition.isCreateLossParticleFile()));
        parameters.add(new GenericParameter<Boolean>("onetable",this.trackInitialCondition.isOneTable()));
        parameters.add(new GenericParameter<Boolean>("quantum",this.trackInitialCondition.isQuantumExcited()));
        parameters.add(new GenericParameter<Boolean>("damp",this.trackInitialCondition.isSynchrotronDamped()));
        parameters.add(new GenericParameter<Boolean>("dump",this.trackInitialCondition.isWriteAtEachTurn()));
        
        return parameters;   
    }
}

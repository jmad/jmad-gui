package cern.accsoft.steering.jmad.kernel.cmd.track;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.track.RelativeParticleCoordinate;
import cern.accsoft.steering.jmad.kernel.cmd.AbstractCommand;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * command START, x= double, px= double, y= double, py= double, t= double, pt= double;
 * 
 * @author xbuffat
 *
 */

public class TrackStartCommand extends AbstractCommand{

	private static final String CMD_NAME = "start";
	
	private final RelativeParticleCoordinate relatvieParticleCoordinate;
	
	public TrackStartCommand(RelativeParticleCoordinate relativeParticleCoordinate)	{
		this.relatvieParticleCoordinate = relativeParticleCoordinate;
	}
	
	@Override
	public String getName() {
		return TrackStartCommand.CMD_NAME;
	}
	
    @Override
    public List<Parameter> getParameters() {
    	List<Parameter> parameters = new ArrayList<Parameter>();
    	parameters.add(new GenericParameter<Double>("x",this.relatvieParticleCoordinate.getXRelatviePosition()));
    	parameters.add(new GenericParameter<Double>("px",this.relatvieParticleCoordinate.getXRelativeMomentum()));
    	parameters.add(new GenericParameter<Double>("y",this.relatvieParticleCoordinate.getYRelativePosition()));
    	parameters.add(new GenericParameter<Double>("py",this.relatvieParticleCoordinate.getYRelatvieMomentum()));
    	parameters.add(new GenericParameter<Double>("t",this.relatvieParticleCoordinate.getRelativeTimeDifference()));
    	parameters.add(new GenericParameter<Double>("pt",this.relatvieParticleCoordinate.getRelativeTimeDifference()));
    	
        return parameters;
    }

}

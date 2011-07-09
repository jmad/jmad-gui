package cern.accsoft.steering.jmad.kernel.cmd.track;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.kernel.cmd.AbstractCommand;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * command OBSERVE, place= string;
 * 
 * @author xbuffat
 *
 */

public class TrackObserveCommand extends AbstractCommand {

	private static final String CMD_NAME = "observe";
	
	private final String place;
	
	public TrackObserveCommand(String place) {
		this.place = place;
	}
	
	@Override
	public String getName() {
		return TrackObserveCommand.CMD_NAME;
	}
	
	@Override
	public List<Parameter> getParameters() {
		List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(new GenericParameter<String>("place",this.place) );
		return parameters;
	}

}

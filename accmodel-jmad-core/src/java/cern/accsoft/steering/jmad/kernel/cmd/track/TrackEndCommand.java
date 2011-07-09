package cern.accsoft.steering.jmad.kernel.cmd.track;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.kernel.cmd.AbstractCommand;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * command trackend;
 * 
 * @author xbuffat
 *
 */

public class TrackEndCommand extends AbstractCommand {

	private static final String CMD_NAME = "endtrack";
	
	@Override
	public String getName() {
		return TrackEndCommand.CMD_NAME;
	}

    @Override
    public List<Parameter> getParameters() {
        return new ArrayList<Parameter>();
    }
	
}

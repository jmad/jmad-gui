// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 * 
 * JMad is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JMad is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JMad.  If not, see <http://www.gnu.org/licenses/>.
 * 
 ******************************************************************************/
// @formatter:on
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

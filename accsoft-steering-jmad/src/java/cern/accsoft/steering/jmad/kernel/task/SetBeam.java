package cern.accsoft.steering.jmad.kernel.task;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.beam.Beam;
import cern.accsoft.steering.jmad.kernel.cmd.BeamCommand;
import cern.accsoft.steering.jmad.kernel.cmd.Command;
import cern.accsoft.steering.jmad.kernel.cmd.ResbeamCommand;

public class SetBeam extends AbstractTask {
    private final Beam beam;

    public SetBeam(Beam beam) {
        this.beam = beam;
    }

    @Override
    protected List<Command> getCommands() {
        List<Command> commands = new ArrayList<Command>();

        ResbeamCommand resbeamCommand = new ResbeamCommand(beam);
        commands.add(resbeamCommand);

        BeamCommand beamCommand = new BeamCommand(beam);
        commands.add(beamCommand);

        return commands;
    }

}

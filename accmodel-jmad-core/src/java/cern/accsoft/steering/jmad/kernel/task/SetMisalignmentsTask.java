/**
 * Copyright (c) 2015 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package cern.accsoft.steering.jmad.kernel.task;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.misalign.MisalignmentConfiguration;
import cern.accsoft.steering.jmad.domain.misalign.PatternOrClassMisalignmentConfiguration;
import cern.accsoft.steering.jmad.kernel.cmd.Command;

/**
 * A wrapper for a multiple number of tasks such that the command that is sent to the kernel is at once. This class can
 * distinguish both: {@link MisalignmentConfiguration} and {@link PatternOrClassMisalignmentConfiguration} types and
 * creates the commands accordingly.
 * 
 * @author agorzaws
 */
public class SetMisalignmentsTask extends AbstractTask {

    private List<MisalignmentConfiguration> misalignments;

    public SetMisalignmentsTask(List<MisalignmentConfiguration> config) {
        this.misalignments = new ArrayList<>(config);
    }

    @Override
    protected List<Command> getCommands() {
        List<Command> toReturn = new ArrayList<Command>();
        for (MisalignmentConfiguration misalignmentConfiguration : misalignments) {
            if (misalignmentConfiguration instanceof PatternOrClassMisalignmentConfiguration) {
                toReturn.addAll(new SetClassMisalignment(
                        (PatternOrClassMisalignmentConfiguration) misalignmentConfiguration).getCommands());
            } else {
                toReturn.addAll(new SetMisalignment(misalignmentConfiguration).getCommands());
            }
        }
        return toReturn;
    }

}

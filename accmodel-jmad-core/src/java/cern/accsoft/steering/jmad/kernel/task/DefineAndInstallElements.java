// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on

package cern.accsoft.steering.jmad.kernel.task;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.elem.Element;
import cern.accsoft.steering.jmad.kernel.cmd.Command;
import cern.accsoft.steering.jmad.kernel.cmd.DefineElement;
import cern.accsoft.steering.jmad.kernel.cmd.seqedit.EndeditCommand;
import cern.accsoft.steering.jmad.kernel.cmd.seqedit.FlattenCommand;
import cern.accsoft.steering.jmad.kernel.cmd.seqedit.InstallCommand;
import cern.accsoft.steering.jmad.kernel.cmd.seqedit.SeqeditCommand;

/**
 * Define a list of element and add them to the sequence This class has not been tested for usage online.
 * 
 * @author xbuffat
 */

public class DefineAndInstallElements extends AbstractTask {

    private String sequence;
    private List<Element> toInstall;

    public DefineAndInstallElements(String sequ, List<Element> toInstall) {
        this.sequence = sequ;
        this.toInstall = toInstall;
    }

    @Override
    protected List<Command> getCommands() {
        List<Command> commands = new ArrayList<Command>();
        for (int i = 0; i < this.toInstall.size(); ++i) {
            commands.add(new DefineElement(this.toInstall.get(i)));
        }
        commands.add(new SeqeditCommand(this.sequence));
        for (int i = 0; i < this.toInstall.size(); ++i) {
            commands.add(new InstallCommand(this.toInstall.get(i)));
        }
        commands.add(new FlattenCommand());
        commands.add(new EndeditCommand());
        return commands;
    }

}

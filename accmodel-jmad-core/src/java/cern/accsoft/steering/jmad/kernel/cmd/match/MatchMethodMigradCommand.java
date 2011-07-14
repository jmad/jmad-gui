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
/**
 * 
 */
package cern.accsoft.steering.jmad.kernel.cmd.match;

import cern.accsoft.steering.jmad.domain.result.match.methods.MatchMethodMigrad;

/**
 * @author muellerg
 */
public class MatchMethodMigradCommand extends AbstractMatchMethodCommand {

    public MatchMethodMigradCommand(MatchMethodMigrad matchMethod) {
        super(matchMethod);
        /*
         * nothing special here. this command has only the default options of MatchMethodCommand.
         */
    }

    /** the name of the command */
    private static final String CMD_NAME = "migrad";

    @Override
    public String getName() {
        return CMD_NAME;
    }

}

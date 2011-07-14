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
package cern.accsoft.steering.jmad.kernel.cmd.ptc;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.kernel.cmd.AbstractCommand;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * This class represents the madx-command to initialize the ptc-universe
 * 
 * @author kfuchsbe
 */
public class PtcCreateUniverseCommand extends AbstractCommand {

    /** the name of the command */
    private static final String CMD_NAME = "ptc_create_universe";

    /*
     * the possible options:
     */
    private Integer sectorNMulMax = null;
    private Integer sectorNMul = null;
    private Boolean ntpsa = null;
    private Boolean symPrint = null;

    @Override
    public String getName() {
        return CMD_NAME;
    }

    @Override
    public List<Parameter> getParameters() {
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(new GenericParameter<Integer>("sector_nmul_max", sectorNMulMax));
        parameters.add(new GenericParameter<Integer>("sector_nmul", sectorNMul));
        parameters.add(new GenericParameter<Boolean>("ntpsa", ntpsa));
        parameters.add(new GenericParameter<Boolean>("symprint", symPrint));
        return parameters;
    }

    public Integer getSectorNMulMax() {
        return sectorNMulMax;
    }

    public void setSectorNMulMax(Integer sectorNMulMax) {
        this.sectorNMulMax = sectorNMulMax;
    }

    public Integer getSectorNMul() {
        return sectorNMul;
    }

    public void setSectorNMul(Integer sectorNMul) {
        this.sectorNMul = sectorNMul;
    }

    public Boolean getNtpsa() {
        return ntpsa;
    }

    public void setNtpsa(Boolean ntpsa) {
        this.ntpsa = ntpsa;
    }

    public Boolean getSymPrint() {
        return symPrint;
    }

    public void setSymPrint(Boolean symPrint) {
        this.symPrint = symPrint;
    }

}

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
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
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

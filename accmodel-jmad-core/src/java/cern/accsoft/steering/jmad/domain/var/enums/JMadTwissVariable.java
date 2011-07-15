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
package cern.accsoft.steering.jmad.domain.var.enums;

import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;

/**
 * This enum groups two {@link MadxTwissVariable}s together, one for each plane. The correct twiss variable for one
 * plane can then be retrieved through one method.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public enum JMadTwissVariable {
    POS(MadxTwissVariable.X, MadxTwissVariable.Y), //
    P(MadxTwissVariable.PX, MadxTwissVariable.PY), //
    D(MadxTwissVariable.DX, MadxTwissVariable.DY), //
    DP(MadxTwissVariable.DPX, MadxTwissVariable.DPY), //
    DD(MadxTwissVariable.DDX, MadxTwissVariable.DDY), //
    DDP(MadxTwissVariable.DDPX, MadxTwissVariable.DDPY), //
    BETA(MadxTwissVariable.BETX, MadxTwissVariable.BETY), //
    ALFA(MadxTwissVariable.ALFX, MadxTwissVariable.ALFY), //
    MU(MadxTwissVariable.MUX, MadxTwissVariable.MUY), //
    DMU(MadxTwissVariable.DMUX, MadxTwissVariable.DMUY), //
    UNKNOWN(MadxTwissVariable.UNKNOWN, MadxTwissVariable.UNKNOWN);

    /** The madx variable for the horizontal plane */
    private MadxTwissVariable xVar;

    /** the madx variable for the vertical plane */
    private MadxTwissVariable yVar;

    private JMadTwissVariable(MadxTwissVariable xVar, MadxTwissVariable yVar) {
        this.xVar = xVar;
        this.yVar = yVar;
    }

    public MadxTwissVariable getXVar() {
        return xVar;
    }

    public MadxTwissVariable getYVar() {
        return yVar;
    }

    /**
     * return the correct twiss variable defined by the given plane and the current jmad twiss variable.
     * 
     * @param plane the plane for which to get the twiss variable
     * @return a variable which can be used for twissing in madx
     */
    public MadxTwissVariable getMadxTwissVariable(JMadPlane plane) {
        if (JMadPlane.H == plane) {
            return getXVar();
        } else if (JMadPlane.V == plane) {
            return getYVar();
        } else {
            return null;
        }
    }

}

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

package cern.accsoft.steering.jmad.domain.twiss;

import java.util.List;

import cern.accsoft.steering.jmad.domain.optics.EditableOpticPoint;
import cern.accsoft.steering.jmad.domain.var.enums.MadxTwissVariable;

public interface TwissInitialConditions extends EditableOpticPoint {

    public abstract Double getDeltap();

    public abstract void setDeltap(Double deltap);

    /**
     * @param calcChromaticFunctions the calcChromaticFunctions to set
     */
    public abstract void setCalcChromaticFunctions(boolean calcChromaticFunctions);

    /**
     * @return the calcChromaticFunctions
     */
    public abstract boolean isCalcChromaticFunctions();

    /**
     * @param closedOrbit the closedOrbit to set
     */
    public abstract void setClosedOrbit(boolean closedOrbit);

    /**
     * @return the closedOrbit
     */
    public abstract boolean isClosedOrbit();

    /**
     * @param calcAtCentre the calcAtCentre to set
     */
    public abstract void setCalcAtCenter(boolean calcAtCentre);

    /**
     * @return the calcAtCenter
     */
    public abstract boolean isCalcAtCenter();

    public abstract Double getT();

    /* short name to use the same as madx */
    public abstract void setT(Double t); // NOPMD by kaifox on 6/25/10 6:06 PM

    public abstract Double getPt();

    /* short name to use the same as madx */
    public abstract void setPt(Double pt); // NOPMD by kaifox on 6/25/10 6:06 PM

    public abstract List<MadxTwissVariable> getMadxVariables();

    public abstract Double getValue(MadxTwissVariable var);

    public abstract void setSaveBetaName(String saveBetaName);

    public abstract String getSaveBetaName();

    public abstract void addListener(TwissListener listener);

    public abstract void removeListener(TwissListener listener);
}

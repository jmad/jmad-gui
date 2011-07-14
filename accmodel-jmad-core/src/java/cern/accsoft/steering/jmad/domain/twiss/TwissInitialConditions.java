// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, Kajetan Fuchsberger. All rights reserved.
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

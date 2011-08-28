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

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.optics.EditableOpticPointImpl;
import cern.accsoft.steering.jmad.domain.optics.OpticPointImpl;
import cern.accsoft.steering.jmad.domain.var.enums.MadxTwissVariable;
import cern.accsoft.steering.jmad.util.bean.NamedBean;
import cern.accsoft.steering.jmad.util.xml.CloneUtil;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("twiss-initial-conditions")
public final class TwissInitialConditionsImpl extends EditableOpticPointImpl implements NamedBean,
        TwissInitialConditions, Cloneable {

    /** the listeners to the twiss-object */
    private final List<TwissListener> listeners = new ArrayList<TwissListener>();

    public static final List<MadxTwissVariable> TWISS_MADX_VARIABLES = new ArrayList<MadxTwissVariable>();

    /* TODO implement all parameters! */

    static {
        TwissInitialConditionsImpl.TWISS_MADX_VARIABLES.addAll(OpticPointImpl.MADX_VARIABLES);
        TwissInitialConditionsImpl.TWISS_MADX_VARIABLES.add(MadxTwissVariable.DELTAP);
        TwissInitialConditionsImpl.TWISS_MADX_VARIABLES.add(MadxTwissVariable.T);
        TwissInitialConditionsImpl.TWISS_MADX_VARIABLES.add(MadxTwissVariable.PT);
    }

    /*
     * TWISS, BETX=real,ALFX=real,MUX=real, BETY=real,ALFY=real,MUY=real, DX=real,DPX=real,DY=real,DPY=real,
     * X=real,PX=real,Y=real,PY=real, T=real,PT=real, WX=real,PHIX=real,DMUX=real, WY=real,PHIY=real,DMUY=real,
     * DDX=real,DDY=real,DDPX=real,DDPY=real, R11=real,R12=real,R21=real,R22=real, !coupling matrix TABLE=table_name,
     * TOLERANCE=real, DELTAP=real:real:real;
     */

    /** when set, the chromatic functions are also calculated during twiss */
    private boolean calcChromaticFunctions = true;

    /**
     * calculates the closed orbit. This means that the initial values must not be used in the command. This enforces
     * madx to search for the closed orbit solution.
     */
    private boolean closedOrbit = false;

    /**
     * calculates the linear lattice functions at the center of the elements instead at the end of it.
     */
    private boolean calcAtCenter = false;

    /**
     * instead of using TwissInitialValueSet it loads MadX internal Optic function values as saved by a previous
     * SaveBeta Command
     */
    private String saveBetaName = null;

    /**
     * the constructor
     * 
     * @param name the name of the twiss (arbitrary)
     */
    public TwissInitialConditionsImpl(String name) {
        super(name);

    }

    public TwissInitialConditionsImpl() {
        this("unnamed twiss");
    }

    public void setSaveBetaName(String saveBetaName) {
        this.saveBetaName = saveBetaName;
    }

    public String getSaveBetaName() {
        return saveBetaName;
    }

    public Double getDeltap() {
        return getValue(MadxTwissVariable.DELTAP);
    }

    public void setDeltap(Double deltap) {
        setValue(MadxTwissVariable.DELTAP, deltap);
    }

    /**
     * @param calcChromaticFunctions the calcChromaticFunctions to set
     */
    public void setCalcChromaticFunctions(boolean calcChromaticFunctions) {
        this.calcChromaticFunctions = calcChromaticFunctions;
        fireChangedTwiss();
    }

    /**
     * @return the calcChromaticFunctions
     */
    public boolean isCalcChromaticFunctions() {
        return calcChromaticFunctions;
    }

    /**
     * @param closedOrbit the closedOrbit to set
     */
    public void setClosedOrbit(boolean closedOrbit) {
        this.closedOrbit = closedOrbit;
        fireChangedTwiss();
    }

    /**
     * @return the closedOrbit
     */
    public boolean isClosedOrbit() {
        return closedOrbit;
    }

    /*
     * methods concerning listeners
     */

    @Override
    protected void firePropertyChanged() {
        fireChangedTwiss();
    }

    /**
     * notify the listeners, that a value has changed
     */
    private void fireChangedTwiss() {
        for (TwissListener listener : listeners) {
            listener.changedTwiss(this);
        }
    }

    /**
     * @param listener the listener to add
     */
    public void addListener(TwissListener listener) {
        this.listeners.add(listener);
    }

    /**
     * @param listener the listener to remove
     */
    public void removeListener(TwissListener listener) {
        this.listeners.remove(listener);
    }

    @Override
    public String toString() {
        return getName();
    }

    /**
     * @param calcAtCentre the calcAtCenter to set
     */
    public void setCalcAtCenter(boolean calcAtCentre) {
        this.calcAtCenter = calcAtCentre;
        fireChangedTwiss();
    }

    /**
     * @return the calcAtCenter
     */
    public boolean isCalcAtCenter() {
        return calcAtCenter;
    }

    public Double getT() {
        return getValue(MadxTwissVariable.T);
    }

    /* short name to use the same as madx */
    public void setT(Double t) { // NOPMD by kaifox on 6/25/10 6:07 PM
        setValue(MadxTwissVariable.T, t);
    }

    public Double getPt() {
        return getValue(MadxTwissVariable.PT);
    }

    /* short name to use the same as madx */
    public void setPt(Double pt) { // NOPMD by kaifox on 6/25/10 6:07 PM
        setValue(MadxTwissVariable.PT, pt);
    }

    @Override
    public List<MadxTwissVariable> getMadxVariables() {
        return TwissInitialConditionsImpl.TWISS_MADX_VARIABLES;
    }

    @Override
    public TwissInitialConditionsImpl clone() {
        return CloneUtil.clone(TwissInitialConditionsImpl.class, this, new TwissInitialConditionsXmlConverter());
    }

    /*
     * Methods only needed for testing
     */
    /* package level for testing */
    List<TwissListener> getListeners() { // NOPMD by kaifox on 6/25/10 6:07 PM
        return this.listeners;
    }
}

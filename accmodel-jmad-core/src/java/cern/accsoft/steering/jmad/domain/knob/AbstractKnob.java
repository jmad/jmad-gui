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
package cern.accsoft.steering.jmad.domain.knob;

import java.util.ArrayList;
import java.util.List;

/**
 * the abstract implementation of a genera knob
 * 
 * @author kfuchsbe
 */
public abstract class AbstractKnob implements Knob {

    /** the offset, shall be the only thing that is stored here */
    private double offset = 0.0;

    /** the listeners to that knob */
    private final List<KnobListener> listeners = new ArrayList<KnobListener>();

    @Override
    public final double getValue() {
        return getTotalValue() - this.offset;
    }

    @Override
    public final void setValue(double value) {
        setTotalValue(value + this.offset);
    }

    @Override
    public final double getOffset() {
        return this.offset;
    }

    @Override
    public final void setOffset(double offset) {
        /* get the old value with respect to the old offset */
        double oldValue = getValue();

        /* change the offset */
        this.offset = offset;

        /* set again, to have the new value transferred to the madxKnob */
        setValue(oldValue);
    }

    /**
     * sets the value to madx and notifies the listeners, that the value has changed.
     * 
     * @param value the value to set
     */
    private final void setTotalValue(double value) {
        doSetTotalValue(value);
        fireChangedValue();
    }

    /**
     * has to be implemented by subclass to set the total value to madx. The only call from this is from setTotalValue,
     * which also notifies the listeners.
     * 
     * @param value the value to set
     */
    protected abstract void doSetTotalValue(double value);

    @Override
    public final void addListener(KnobListener listener) {
        listeners.add(listener);
    }

    @Override
    public final void removeListener(KnobListener listener) {
        listeners.remove(listener);
    }

    /**
     * notifies the listeners that the values changed
     */
    private final void fireChangedValue() {
        for (KnobListener listener : listeners) {
            listener.changedValue(this);
        }
    }

}

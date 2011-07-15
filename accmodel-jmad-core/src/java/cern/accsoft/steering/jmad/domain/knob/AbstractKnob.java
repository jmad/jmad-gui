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

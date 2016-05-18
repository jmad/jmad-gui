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

package cern.accsoft.steering.jmad.domain.knob.strength;

import cern.accsoft.steering.jmad.domain.knob.AbstractKnob;
import cern.accsoft.steering.jmad.domain.knob.KnobType;

public class SimpleStrength extends AbstractKnob implements Strength {

    private final String name;
    private double totalValue = 0.0;
    private String description = null;

    public SimpleStrength(String name, double value, String comment) {
        this.name = name;
        this.totalValue = value;
        this.description = comment;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    protected void doSetTotalValue(double value) {
        this.totalValue = value;
    }

    @Override
    public double getTotalValue() {
        return this.totalValue;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getKey() {
        return getName().trim()
                .toLowerCase();
    }

    @Override
    public KnobType getType() {
        return KnobType.STRENGTH;
    }

    @Override
    public String getMadxName() {
        return getName();
    }

    @Override
    public String toString() {
        return "SimpleStrength [name=" + name + ", totalValue=" + totalValue + ", description=" + description + "]";
    }

}

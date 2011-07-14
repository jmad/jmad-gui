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
        return getName().trim().toLowerCase();
    }

    @Override
    public KnobType getType() {
        return KnobType.STRENGTH;
    }

    @Override
    public String getMadxName() {
        return getName();
    }

}

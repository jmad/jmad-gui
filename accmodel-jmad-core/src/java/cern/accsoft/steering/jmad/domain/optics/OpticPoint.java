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
package cern.accsoft.steering.jmad.domain.optics;

import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;
import cern.accsoft.steering.jmad.domain.var.enums.JMadTwissVariable;
import cern.accsoft.steering.jmad.domain.var.enums.MadxTwissVariable;

public interface OpticPoint {

    public abstract Double getValue(JMadTwissVariable variable, JMadPlane plane);

    public abstract Double getValue(MadxTwissVariable variable);

    public abstract Double getBetx();

    public abstract Double getBety();

    public abstract Double getMux();

    public abstract Double getMuy();

    public abstract String getName();
    
    public abstract Double getPosition();

    public abstract Double getDx();

    public abstract Double getDy();

    public abstract Double getAlfx();

    public abstract Double getAlfy();

    public abstract Double getDdx();

    public abstract Double getDdy();

    public abstract Double getDdpx();

    public abstract Double getDdpy();

    public abstract Double getDpx();

    public abstract Double getDpy();

    public abstract Double getX();

    public abstract Double getPx();

    public abstract Double getY();

    public abstract Double getPy();

}

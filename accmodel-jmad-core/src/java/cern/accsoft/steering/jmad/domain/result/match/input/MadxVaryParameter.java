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
package cern.accsoft.steering.jmad.domain.result.match.input;

import cern.accsoft.steering.jmad.domain.knob.MadxParameter;

/**
 * the interface for a matchin variation parameter
 * 
 * @author muellerg
 */
public interface MadxVaryParameter {

    /**
     * returns the name of the parameter. This can be e.g. a name of a strength or the attribute of an element (e.g.
     * MCIAH->kick)
     * 
     * @return the (madx) name of the parameter to vary
     */
    public abstract String getName();

    /**
     * The approximate initial step size for varying the parameter. If the step is not entered, MAD tries to find a
     * reasonable step, but this may not always work.
     * 
     * @return the step to use.
     */
    public abstract Double getStep();

    /**
     * Lower limit for the parameter (optional)
     * 
     * @return the lower limit or null if not set
     */
    public abstract Double getLower();

    /**
     * Upper limit for the parameter (optional).
     * 
     * @return the upper limit or null if not set
     */
    public abstract Double getUpper();

    /**
     * @return the basis Parameter used for varying
     */
    public abstract MadxParameter getMadxParameter();
}

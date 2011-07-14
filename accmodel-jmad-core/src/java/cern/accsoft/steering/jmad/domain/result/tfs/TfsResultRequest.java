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
package cern.accsoft.steering.jmad.domain.result.tfs;

import java.util.List;

import cern.accsoft.steering.jmad.domain.var.TwissVariable;

/**
 * Defines which madx-variables shall be contained in the {@link TfsResult} after the twiss.
 * 
 * @author kaifox
 */
public interface TfsResultRequest {

    /**
     * @return the requeset resultVariables.
     */
    public abstract List<TwissVariable> getResultVariables();

    /**
     * @return the requested element-filters.
     */
    public abstract List<String> getElementPattern();

    /**
     * @return the requested element-classes
     */
    public abstract List<String> getElementClasses();

    /**
     * @return <code>true</code>, if it is required to run a ptc-twiss to get the variables
     */
    public abstract boolean needsPtcTwiss();
}

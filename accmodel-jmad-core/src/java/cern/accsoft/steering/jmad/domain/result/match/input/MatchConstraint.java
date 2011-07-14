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
/**
 * 
 */
package cern.accsoft.steering.jmad.domain.result.match.input;

import java.util.List;
import java.util.Map;

/**
 * Thi interface which represents a general matching constraint for madx
 * 
 * @author muellerg
 */
public interface MatchConstraint {

    /**
     * @return true, if it is a global constraint
     */
    public abstract boolean isGlobal();

    /**
     * @return MadxName-Value Pairs for all defined Parameters of this constraint
     */
    public abstract Map<String, Double> getParameterSettings();

    /**
     * @return Madx values-command names to read the resulting value of the constraints after matching...
     *         <p>
     *         for local matching, if a range is specified, only names for first and last element of the range are
     *         returned
     */
    public abstract List<String> getReadoutNames();

}

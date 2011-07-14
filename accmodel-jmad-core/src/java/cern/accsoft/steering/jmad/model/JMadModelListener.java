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
package cern.accsoft.steering.jmad.model;

import cern.accsoft.steering.jmad.domain.machine.Range;

public interface JMadModelListener {

    /**
     * fired, when some values were changed, so that the calculated values are no longer valid.
     */
    public void becameDirty();

    /**
     * fired, when another range was selected in the model
     * 
     * @param newRange the new Range
     */
    public void rangeChanged(Range newRange);

    /**
     * fired when the elements Changed
     */
    public void elementsChanged();

    /**
     * fired, when the optics-values changed (are reloaded)
     */
    public void opticsChanged();

    /**
     * fired, when the optics definition was changed.
     */
    public void opticsDefinitionChanged();

}

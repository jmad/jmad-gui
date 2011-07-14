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
package cern.accsoft.steering.jmad.model;

import cern.accsoft.steering.jmad.domain.machine.Range;

/**
 * A model-listener, which implements all the methods, which may be overridden by subclasses, in order to react only on
 * special events.
 * 
 * @author kfuchsbe
 */
public class AbstractJMadModelListener implements JMadModelListener {

    @Override
    public void becameDirty() {
        /* to be overridden if required */
    }

    @Override
    public void elementsChanged() {
        /* to be overridden if required */
    }

    @Override
    public void opticsChanged() {
        /* to be overridden if required */
    }

    @Override
    public void opticsDefinitionChanged() {
        /* to be overridden if required */
    }

    @Override
    public void rangeChanged(Range newRange) {
        /* to be overridden if required */
    }

}

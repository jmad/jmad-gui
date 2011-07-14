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
package cern.accsoft.steering.jmad.model.manage;

import cern.accsoft.steering.jmad.model.JMadModel;

/**
 * A simple adapter which implements all methods of the listener and requires only those to be implemented when needed.
 * 
 * @author kfuchsbe
 */
public class JMadModelManagerAdapter implements JMadModelManagerListener {

    @Override
    public void addedModel(JMadModel newModel) {
        /* override if needed */
    }

    @Override
    public void changedActiveModel(JMadModel newActiveModel) {
        /* override if needed */
    }

    @Override
    public void removedModel(JMadModel removedModel) {
        /* override if needed */
    }

}

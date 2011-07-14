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
package cern.accsoft.steering.util.gui;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * This is our basic action for the Orbit-feedback dataManager. It is basically the same as {@link AbstractAction} but
 * provides a constructor that allows to set the name and the short description. That simplifies things a bit.
 * 
 * @author kfuchsbe
 */
public abstract class NamedAction extends AbstractAction {
    private static final long serialVersionUID = -165287112033003245L;

    /**
     * the constructor, which enforces the setting of some attributes.
     * 
     * @param name the name of the action
     * @param shortDescription the short description (Tool tip)
     */
    public NamedAction(String name, String shortDescription) {
        putValue(Action.NAME, name);
        putValue(Action.SHORT_DESCRIPTION, shortDescription);
    }

}

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

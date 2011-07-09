/*
 * $Id: OfbdmAction.java,v 1.1 2008-10-10 14:12:43 kfuchsbe Exp $
 * 
 * $Date: 2008-10-10 14:12:43 $ $Revision: 1.1 $ $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
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

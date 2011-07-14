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
/*
 * $Id: ModelManagerListener.java,v 1.1 2008-08-11 11:58:45 kfuchsbe Exp $
 * 
 * $Date: 2008-08-11 11:58:45 $ $Revision: 1.1 $ $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.model.manage;

import cern.accsoft.steering.jmad.model.JMadModel;

/**
 * this interface defines a listener to a ModelManager
 * 
 * @author kfuchsbe
 */
public interface JMadModelManagerListener {

    /**
     * this method id called, when the model manager switches to a different model somehow.
     * <p>
     * NOTE: The new model might be null! This might be the case, e.g. a model is removed from the model manager
     * 
     * @param newActiveModel the actual (new) model
     */
    public void changedActiveModel(JMadModel newActiveModel);

    /**
     * fired when a new model is added.
     * 
     * @param newModel the new model
     */
    public void addedModel(JMadModel newModel);

    /**
     * fired, when a model was removed from the manager.
     * 
     * @param removedModel the model which was removed from the manager
     */
    public void removedModel(JMadModel removedModel);
}

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
package cern.accsoft.steering.jmad.gui.manage;

import javax.swing.Action;

/**
 * interface, which provides actions to choose model, range and optics.
 * 
 * @author kfuchsbe
 */
public interface ChooseActionFactory {

    /**
     * @return the action to choose a new model.
     */
    public abstract Action getNewModelAction();

    /**
     * @return the action to start choosing a new range for the model.
     */
    public abstract Action getChooseRangeAction();

    /**
     * @return the action to start choosing a new optics for the model.
     */
    public abstract Action getChooseOpticsAction();

    /**
     * @return the action to close the active model
     */
    public abstract Action getCloseActiveModelAction();

    /**
     * @return the action to exit the application
     */
    public abstract Action getExitAction();

    /**
     * @return the action to import a model definition
     */
    public abstract Action getImportAction();

    /**
     * @return the action to export a model definition
     */
    public abstract Action getExportAction();
}

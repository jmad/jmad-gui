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

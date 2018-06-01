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

package cern.accsoft.steering.jmad.gui.manage.impl;

import cern.accsoft.steering.jmad.gui.manage.JMadGuiPreferences;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class JMadGuiPreferencesImpl implements JMadGuiPreferences {

    /**
     * The name of the system-property for the working dir.
     */
    private final static String PROP_WORKDIR_PATH = "cern.jmad.gui.workdir.path";
    /** the default value for the workingdir when property is not set. */
    private final static String DEFAULT_WORKDIR_PATH = ".";

    /**
     * The name of the property for the edit-command
     */
    private final static String PROP_EDIT_COMMAND = "cern.jmad.gui.edit.cmd";
    /**
     * the default value for the edit-command when property is not set. TODO some solution needed that works for both
     * windows and linux!?
     */
    private final static String DEFAULT_EDIT_COMMAND = "nedit";

    /** true, if the change of the model is allowed */
    private BooleanProperty enabledChangeModel = new SimpleBooleanProperty(true);

    /** true, if the change of the range is allowed */
    private BooleanProperty enabledChangeRange = new SimpleBooleanProperty(true);

    /** true, if the change of the optic is allowed */
    private BooleanProperty enabledChangeOptic = new SimpleBooleanProperty(true);

    /** true, if the model should be cleaned up on close. */
    private BooleanProperty cleanupOnClose = new SimpleBooleanProperty(true);

    /** true, if the program should exit, when window is closed. */
    private BooleanProperty exitOnClose = new SimpleBooleanProperty(true);

    /** Open JMad frame as main-frame */
    private BooleanProperty mainFrame = new SimpleBooleanProperty(true);

    public boolean isEnabledChangeModel() {
        return enabledChangeModel.getValue();
    }

    public void setEnabledChangeModel(boolean enabledChangeModel) {
        this.enabledChangeModel.setValue(enabledChangeModel);
    }

    @Override
    public BooleanProperty enabledChangeModelProperty() {
        return enabledChangeModel;
    }

    public boolean isEnabledChangeRange() {
        return enabledChangeRange.getValue();
    }

    public void setEnabledChangeRange(boolean enabledChangeRange) {
        this.enabledChangeRange.setValue(enabledChangeRange);
    }

    @Override
    public BooleanProperty enabledChangeRangeProperty() {
        return enabledChangeRange;
    }

    public boolean isEnabledChangeOptic() {
        return enabledChangeOptic.getValue();
    }

    public void setEnabledChangeOptic(boolean enabledChangeOptic) {
        this.enabledChangeOptic.setValue(enabledChangeOptic);
    }

    @Override
    public BooleanProperty enabledChangeOpticProperty() {
        return enabledChangeOptic;
    }

    public void setCleanupOnClose(boolean cleanupOnClose) {
        this.cleanupOnClose.setValue(cleanupOnClose);
    }

    public boolean isCleanupOnClose() {
        return cleanupOnClose.getValue();
    }

    @Override
    public BooleanProperty cleanupOnCloseProperty() {
        return cleanupOnClose;
    }

    public void setExitOnClose(boolean exitOnClose) {
        this.exitOnClose.setValue(exitOnClose);
    }

    public boolean isExitOnClose() {
        return exitOnClose.getValue();
    }

    @Override
    public BooleanProperty exitOnCloseProperty() {
        return exitOnClose;
    }

    @Override
    public String getWorkingDir() {
        String workingDir = System.getProperty(PROP_WORKDIR_PATH);
        if (workingDir != null) {
            return workingDir;
        }
        return DEFAULT_WORKDIR_PATH;
    }

    @Override
    public String getEditorCommand() {
        String editCommand = System.getProperty(PROP_EDIT_COMMAND);
        if (editCommand != null) {
            return editCommand;
        }
        return DEFAULT_EDIT_COMMAND;
    }

    @Override
    public boolean isMainFrame() {
        return this.mainFrame.getValue();
    }

    @Override
    public void setMainFrame(boolean mainFrame) {
        this.mainFrame.setValue(mainFrame);
    }

    @Override
    public BooleanProperty useMainFrameProperty() {
        return mainFrame;
    }

}

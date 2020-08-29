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

import static org.ossgang.commons.properties.Properties.property;

import cern.accsoft.steering.jmad.gui.manage.JMadGuiPreferences;
import org.ossgang.commons.properties.Property;

/**
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class JMadGuiPreferencesImpl implements JMadGuiPreferences {
    /**
     * The name of the system-property for the working dir.
     */
    private final static String PROP_WORKDIR_PATH = "cern.jmad.gui.workdir.path";
    /**
     * the default value for the workingdir when property is not set.
     */
    private final static String DEFAULT_WORKDIR_PATH = ".";

    /**
     * The name of the property for the edit-command
     */
    private final static String PROP_EDIT_COMMAND = "cern.jmad.gui.edit.cmd";

    /**
     * the default value for the edit-command when property is not set.
     */
    private final static String DEFAULT_EDIT_COMMAND = "nedit";

    /**
     * true, if the change of the model is allowed
     */
    private final Property<Boolean> enabledChangeModel = property(true);

    /**
     * true, if the change of the range is allowed
     */
    private final Property<Boolean> enabledChangeRange = property(true);

    /**
     * true, if the change of the optic is allowed
     */
    private final Property<Boolean> enabledChangeOptic = property(true);

    /**
     * true, if the model should be cleaned up on close.
     */
    private final Property<Boolean> cleanupOnClose = property(true);

    /**
     * true, if the program should exit, when window is closed.
     */
    private final Property<Boolean> exitOnClose = property(true);

    /**
     * Open JMad frame as main-frame
     */
    private final Property<Boolean> mainFrame = property(true);

    public boolean isEnabledChangeModel() {
        return enabledChangeModel.get();
    }

    public void setEnabledChangeModel(boolean enabledChangeModel) {
        this.enabledChangeModel.set(enabledChangeModel);
    }

    @Override
    public Property<Boolean> enabledChangeModelProperty() {
        return enabledChangeModel;
    }

    public boolean isEnabledChangeRange() {
        return enabledChangeRange.get();
    }

    public void setEnabledChangeRange(boolean enabledChangeRange) {
        this.enabledChangeRange.set(enabledChangeRange);
    }

    @Override
    public Property<Boolean> enabledChangeRangeProperty() {
        return enabledChangeRange;
    }

    public boolean isEnabledChangeOptic() {
        return enabledChangeOptic.get();
    }

    public void setEnabledChangeOptic(boolean enabledChangeOptic) {
        this.enabledChangeOptic.set(enabledChangeOptic);
    }

    @Override
    public Property<Boolean> enabledChangeOpticProperty() {
        return enabledChangeOptic;
    }

    public void setCleanupOnClose(boolean cleanupOnClose) {
        this.cleanupOnClose.set(cleanupOnClose);
    }

    public boolean isCleanupOnClose() {
        return cleanupOnClose.get();
    }

    @Override
    public Property<Boolean> cleanupOnCloseProperty() {
        return cleanupOnClose;
    }

    public void setExitOnClose(boolean exitOnClose) {
        this.exitOnClose.set(exitOnClose);
    }

    public boolean isExitOnClose() {
        return exitOnClose.get();
    }

    @Override
    public Property<Boolean> exitOnCloseProperty() {
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
        return this.mainFrame.get();
    }

    @Override
    public void setMainFrame(boolean mainFrame) {
        this.mainFrame.set(mainFrame);
    }

    @Override
    public Property<Boolean> useMainFrameProperty() {
        return mainFrame;
    }
}

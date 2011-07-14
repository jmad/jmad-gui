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
package cern.accsoft.steering.jmad.gui.manage.impl;

import cern.accsoft.steering.jmad.gui.manage.JMadGuiPreferences;

/**
 * @author kfuchsbe
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
    private final static String DEFAULT_EDIT_COMMAND = null;

    /** true, if the change of the model is allowed */
    private boolean enabledChangeModel = true;

    /** true, if the change of the range is allowed */
    private boolean enabledChangeRange = true;

    /** true, if the change of the optic is allowed */
    private boolean enabledChangeOptic = true;

    /** true, if the model should be cleaned up on close. */
    private boolean cleanupOnClose = true;

    /** true, if the program should exit, when window is closed. */
    private boolean exitOnClose = true;

    /** Open JMad frame as main-frame */
    private boolean mainFrame = true;

    public boolean isEnabledChangeModel() {
        return enabledChangeModel;
    }

    public void setEnabledChangeModel(boolean enabledChangeModel) {
        this.enabledChangeModel = enabledChangeModel;
    }

    public boolean isEnabledChangeRange() {
        return enabledChangeRange;
    }

    public void setEnabledChangeRange(boolean enabledChangeRange) {
        this.enabledChangeRange = enabledChangeRange;
    }

    public boolean isEnabledChangeOptic() {
        return enabledChangeOptic;
    }

    public void setEnabledChangeOptic(boolean enabledChangeOptic) {
        this.enabledChangeOptic = enabledChangeOptic;
    }

    public void setCleanupOnClose(boolean cleanupOnClose) {
        this.cleanupOnClose = cleanupOnClose;
    }

    public boolean isCleanupOnClose() {
        return cleanupOnClose;
    }

    public void setExitOnClose(boolean exitOnClose) {
        this.exitOnClose = exitOnClose;
    }

    public boolean isExitOnClose() {
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
        return this.mainFrame;
    }

    @Override
    public void setMainFrame(boolean mainFrame) {
        this.mainFrame = mainFrame;
    }

}

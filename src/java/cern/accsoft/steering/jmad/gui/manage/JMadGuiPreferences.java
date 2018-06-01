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

import javafx.beans.property.BooleanProperty;

public interface JMadGuiPreferences {

    boolean isEnabledChangeModel();

    void setEnabledChangeModel(boolean enabledChangeModel);

    BooleanProperty enabledChangeModelProperty();

    boolean isEnabledChangeRange();

    void setEnabledChangeRange(boolean enabledChangeRange);

    BooleanProperty enabledChangeRangeProperty();

    boolean isEnabledChangeOptic();

    void setEnabledChangeOptic(boolean enabledChangeOptic);

    BooleanProperty enabledChangeOpticProperty();

    void setCleanupOnClose(boolean cleanupOnClose);

    boolean isCleanupOnClose();

    BooleanProperty cleanupOnCloseProperty();

    void setExitOnClose(boolean exitOnClose);

    boolean isExitOnClose();

    BooleanProperty exitOnCloseProperty();

    boolean isMainFrame();

    void setMainFrame(boolean mainFrame);

    BooleanProperty useMainFrameProperty();

    String getWorkingDir();

    /**
     * returns a string that can start an external text editor.
     * 
     * @return the command String.
     */
    String getEditorCommand();

}
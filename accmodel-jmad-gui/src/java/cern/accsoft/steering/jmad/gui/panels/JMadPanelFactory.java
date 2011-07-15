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

package cern.accsoft.steering.jmad.gui.panels;

import java.awt.Component;
import java.util.Map;

/**
 * Creates panels and configures them with edit-handlers if wanted.
 * 
 * @author kaifox
 */
public abstract class JMadPanelFactory {

    /*
     * XXX very dirty! We search the edithandlers via the class names :-( Danger for refactoring, since the classnames
     * are simple strings in spring-context xml
     */
    public TabbedPanePanel createModelOperationPanel(Map<String, ? extends TablePanelEditHandler> handlerMap) {
        TabbedPanePanel tabbedPanePanel = createModelOperationsPanel();

        for (Component component : tabbedPanePanel.getTabbedPaneComponents().values()) {
            if (component instanceof EditHandlerUser<?>) {
                EditHandlerUser<? extends TablePanelEditHandler> user = (EditHandlerUser<?>) component;
                String componentClassName = component.getClass().getCanonicalName();
                TablePanelEditHandler editHandler = handlerMap.get(componentClassName);
                if (editHandler != null) {
                    user.setEditHandler(editHandler);
                }
            }
        }

        return tabbedPanePanel;
    }

    /**
     * injected method by spring to get a new, fully configured ModelOperationPanel
     * 
     * @return a new panel (without edit-handlers)
     */
    protected abstract TabbedPanePanel createModelOperationsPanel();
}

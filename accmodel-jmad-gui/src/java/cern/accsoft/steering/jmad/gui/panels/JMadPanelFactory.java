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
package cern.accsoft.steering.jmad.gui.panels;

import java.awt.Component;
import java.util.Map;

/**
 * Creates panels and configures them with edit-handlers if wanted.
 * 
 * @author kaifox
 * 
 */
public abstract class JMadPanelFactory {

	/*
	 * XXX very dirty! We search the edithandlers via the class names :-( Danger
	 * for refactoring, since the classnames are simple strings in
	 * spring-context xml
	 */
	public TabbedPanePanel createModelOperationPanel(
			Map<String, ? extends TablePanelEditHandler> handlerMap) {
		TabbedPanePanel tabbedPanePanel = createModelOperationsPanel();

		for (Component component : tabbedPanePanel.getTabbedPaneComponents()
				.values()) {
			if (component instanceof EditHandlerUser<?>) {
				EditHandlerUser<? extends TablePanelEditHandler> user = (EditHandlerUser<?>) component;
				String componentClassName = component.getClass()
						.getCanonicalName();
				TablePanelEditHandler editHandler = handlerMap
						.get(componentClassName);
				if (editHandler != null) {
					user.setEditHandler(editHandler);
				}
			}
		}

		return tabbedPanePanel;
	}

	/**
	 * injected method by spring to get a new, fully configured
	 * ModelOperationPanel
	 * 
	 * @return a new panel (without edit-handlers)
	 */
	protected abstract TabbedPanePanel createModelOperationsPanel();
}

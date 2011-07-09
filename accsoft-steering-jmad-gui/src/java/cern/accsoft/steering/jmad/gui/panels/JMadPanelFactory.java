/**
 * 
 */
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

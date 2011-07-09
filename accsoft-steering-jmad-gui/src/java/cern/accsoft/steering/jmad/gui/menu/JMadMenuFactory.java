/**
 * 
 */
package cern.accsoft.steering.jmad.gui.menu;

import java.util.List;

import javax.swing.Action;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;

/**
 * The factory for toolbar and menu
 * 
 * @author kfuchsbe
 * 
 */
public interface JMadMenuFactory {

	/**
	 * @return a new toolbar for jmad.
	 */
	public JToolBar createToolBar();

	/**
	 * @return a new menubar for the JMad GUI
	 */
	public JMenuBar createMenuBar();

	/**
	 * @return the list of actions in the toolbar.
	 */
	public List<Action> getToolBarActions();

}

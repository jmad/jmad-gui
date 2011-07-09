package cern.accsoft.steering.util.gui.dialog;

import javax.swing.JDialog;
import javax.swing.JPanel;

public class SimplePanelDialog extends JDialog {
	private static final long serialVersionUID = 1695446208261983899L;

	/**
	 * intit method for spring
	 */
	public void init() {
		pack();
	}
	
	/**
	 * sets the panel as content
	 * 
	 * @param panel
	 */
	public void setPanel(JPanel panel) {
		this.getContentPane().removeAll();
		this.getContentPane().add(panel);
	}
}

/**
 * 
 */
package cern.accsoft.steering.jmad.gui.dialog;

import java.awt.Frame;

import cern.accsoft.steering.jmad.gui.panels.ModelSelectionPanel;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.service.JMadService;
import cern.accsoft.steering.util.gui.dialog.PanelDialog;

/**
 * This is a collection of utility methods to show dialogs related to
 * jmad-models
 * 
 * @author kfuchsbe
 * 
 */
public class JMadOptionPane {

	private JMadOptionPane() {
		/* no instantiation */
	}

	public final static JMadModel showCreateModelDialog(Frame parent, JMadService jmadService) {
		ModelSelectionPanel modelSelectionPanel = new ModelSelectionPanel(true);
		modelSelectionPanel.setJmadService(jmadService);
		modelSelectionPanel.init();
		if (PanelDialog.show(modelSelectionPanel, parent)) {
			return modelSelectionPanel.getModel();
		} else {
			return null;
		}
	}
}

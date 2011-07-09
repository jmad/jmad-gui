/**
 * 
 */
package cern.accsoft.steering.jmad.gui.panels;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.knob.Knob;
import cern.accsoft.steering.jmad.model.JMadModelListener;

/**
 * this panel lists all knobs of the model, displays their values and allows to
 * set new ones
 * 
 * @author kfuchsbe
 * 
 */
public class ModelCustomKnobsPanel extends AbstractKnobsPanel {
	private static final long serialVersionUID = -4669651127690020870L;

	@Override
	public List<? extends Knob> getKnobs() {
		if (getModel() != null) {
			return getModel().getKnobManager().getCustomKnobs();
		} else {
			return new ArrayList<Knob>();
		}
	}

	@Override
	protected JMadModelListener getModelListener() {
		return null;
	}
}

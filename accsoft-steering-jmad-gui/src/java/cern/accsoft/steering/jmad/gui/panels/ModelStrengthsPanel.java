/**
 * 
 */
package cern.accsoft.steering.jmad.gui.panels;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.knob.Knob;
import cern.accsoft.steering.jmad.domain.knob.strength.Strength;
import cern.accsoft.steering.jmad.model.AbstractJMadModelListener;
import cern.accsoft.steering.jmad.model.JMadModelListener;

/**
 * this panel displays the stregthes of the model
 * 
 * @author kfuchsbe
 * 
 */
public class ModelStrengthsPanel extends AbstractKnobsPanel {
	private static final long serialVersionUID = -2718560766381298342L;

	private JMadModelListener modelListener = new AbstractJMadModelListener() {
		@Override
		public void opticsDefinitionChanged() {
			refreshDisplay();
		}
	};

	@Override
	public List<? extends Knob> getKnobs() {
		if (getModel() != null) {
			return new ArrayList<Strength>(getModel().getStrengthsAndVars()
					.getStrengths());
		} else {
			return new ArrayList<Knob>();
		}
	}

	@Override
	protected JMadModelListener getModelListener() {
		return this.modelListener;
	}

}

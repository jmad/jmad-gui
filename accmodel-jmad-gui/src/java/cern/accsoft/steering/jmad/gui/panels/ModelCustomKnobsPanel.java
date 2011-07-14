// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
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

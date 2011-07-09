/*
 * $Id: ModelStrengthesPanelEditHandler.java,v 1.1 2008-08-11 11:58:45 kfuchsbe Exp $
 * 
 * $Date: 2008-08-11 11:58:45 $ 
 * $Revision: 1.1 $ 
 * $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.gui.panels;

import cern.accsoft.steering.jmad.domain.knob.Knob;

/**
 * provides methods to handle the selection of certain strengthes in a
 * {@link AbstractKnobsPanel}
 * 
 * @author kfuchsbe
 * 
 */
public interface KnobsPanelEditHandler extends TablePanelEditHandler {

	/**
	 * @param value
	 * @return true, if the strength is selected, false, if not
	 */
	public boolean getSelectionValue(Knob value);

	/**
	 * sets the strength as selected or unselected, accordiog to the given value
	 * 
	 * @param modelValue
	 *            the strength for which to modify the selection - value
	 * @param value
	 *            true: select the strength, false: deselect
	 */
	public void setSelectionValue(Knob modelValue, boolean value);
}

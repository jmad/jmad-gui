/*
 * $Id: ModelElementsPanelEditHandler.java,v 1.1 2008-08-11 11:58:45 kfuchsbe Exp $
 * 
 * $Date: 2008-08-11 11:58:45 $ 
 * $Revision: 1.1 $ 
 * $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.gui.panels;

import cern.accsoft.steering.jmad.domain.elem.Element;

/**
 * defines methods which are called by ModelElementsPanel for using an
 * additional - selection column.
 * 
 * @author kfuchsbe
 * 
 */
public interface ModelElementsPanelEditHandler extends TablePanelEditHandler {

	/**
	 * @param element
	 * @param attributeName
	 * @return true if the given attribute of the given element is selected.
	 */
	public Boolean getSelectionValue(Element element, String attributeName);

	/**
	 * sets the attribute of the element as selected / deselected
	 * 
	 * @param element
	 *            the element, for which to set the attribute as selected
	 * @param attributeName
	 *            the attributName to select
	 * @param value
	 *            true for select, false for deselect
	 */
	public void setSelectionValue(Element element, String attributeName,
			boolean value);
}

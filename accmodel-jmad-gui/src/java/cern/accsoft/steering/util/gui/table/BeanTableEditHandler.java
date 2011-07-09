/*
 * $Id: BeanTableEditHandler.java,v 1.1 2009-01-19 17:13:40 kfuchsbe Exp $
 * 
 * $Date: 2009-01-19 17:13:40 $ 
 * $Revision: 1.1 $ 
 * $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.util.gui.table;

import cern.accsoft.steering.jmad.gui.panels.TablePanelEditHandler;

/**
 * This interface defines methods, which allow to add a checkable column to a
 * bean table and react on the events
 * 
 * @author kfuchsbe
 * 
 */
public interface BeanTableEditHandler extends TablePanelEditHandler {

	/**
	 * @param bean
	 * @param propertyName
	 * @return true if the given property of the given bean is checked.
	 */
	public Boolean getCheckValue(Object bean, String propertyName);

	/**
	 * sets the property of the bean as checked / unchecked
	 * 
	 * @param bean
	 *            the bean, for which to set the attribute as checked
	 * @param propertyName
	 *            the propertyName to select
	 * @param value
	 *            true for select, false for deselect
	 */
	public void setCheckValue(Object bean, String propertyName, boolean value);

	/**
	 * @return true if the table cells shall be editable, false otherwise
	 */
	public boolean isEditable();
}

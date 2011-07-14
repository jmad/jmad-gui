// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, Kajetan Fuchsberger. All rights reserved.
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

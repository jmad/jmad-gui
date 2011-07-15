// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on

package cern.accsoft.steering.util.gui.table;

import cern.accsoft.steering.jmad.gui.panels.TablePanelEditHandler;

/**
 * This interface defines methods, which allow to add a checkable column to a bean table and react on the events
 * 
 * @author kfuchsbe
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
     * @param bean the bean, for which to set the attribute as checked
     * @param propertyName the propertyName to select
     * @param value true for select, false for deselect
     */
    public void setCheckValue(Object bean, String propertyName, boolean value);

    /**
     * @return true if the table cells shall be editable, false otherwise
     */
    public boolean isEditable();
}

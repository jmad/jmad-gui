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

package cern.accsoft.steering.util.gui.menu;

/**
 * this interface can be implemented by a tableModel to enable the setting of double-values via popup menu.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public interface ValueSetable {

    /**
     * is queried by the popup menu to determine, if it shall enable the setValue-functionality or not.
     * 
     * @return true if enabled, false if not
     */
    public boolean isValueSetEnabled();

    /**
     * sets the Value to all selected rows.
     * 
     * @param value the value to set.
     */
    public void setValueAllSelected(Double value);

    /**
     * has to return the name of the value in order to display it for the user.
     * 
     * @return the name of the value
     */
    public String getValueName();
}

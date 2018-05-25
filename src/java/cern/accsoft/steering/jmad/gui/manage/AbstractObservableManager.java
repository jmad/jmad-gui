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

package cern.accsoft.steering.jmad.gui.manage;

import java.util.ArrayList;
import java.util.List;

/**
 * the base implementation of a class to which listeners can be attached
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 * @param <T> the type of listeners that can be attached
 */
public class AbstractObservableManager<T> implements GenericObservableManager<T> {

    /** all the listeners to this class. */
    private List<T> listeners = new ArrayList<T>();

    @Override
    public void addListener(T listener) {
        this.listeners.add(listener);
    }

    @Override
    public void removeListener(T listener) {
        this.listeners.remove(listener);
    }

    protected List<T> getListeners() {
        return this.listeners;
    }

}

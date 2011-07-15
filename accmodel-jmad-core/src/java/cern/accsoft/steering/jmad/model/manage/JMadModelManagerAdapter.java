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

/**
 * 
 */
package cern.accsoft.steering.jmad.model.manage;

import cern.accsoft.steering.jmad.model.JMadModel;

/**
 * A simple adapter which implements all methods of the listener and requires only those to be implemented when needed.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class JMadModelManagerAdapter implements JMadModelManagerListener {

    @Override
    public void addedModel(JMadModel newModel) {
        /* override if needed */
    }

    @Override
    public void changedActiveModel(JMadModel newActiveModel) {
        /* override if needed */
    }

    @Override
    public void removedModel(JMadModel removedModel) {
        /* override if needed */
    }

}

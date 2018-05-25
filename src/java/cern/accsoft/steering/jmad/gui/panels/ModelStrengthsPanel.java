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

package cern.accsoft.steering.jmad.gui.panels;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.knob.Knob;
import cern.accsoft.steering.jmad.model.AbstractJMadModelListener;
import cern.accsoft.steering.jmad.model.JMadModelListener;
import cern.accsoft.steering.jmad.model.manage.StrengthVarManagerListener;

/**
 * this panel displays the stregthes of the model
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class ModelStrengthsPanel extends AbstractKnobsPanel {
    private static final long serialVersionUID = -2718560766381298342L;

    private final JMadModelListener modelListener = new AbstractJMadModelListener() {
        @Override
        public void opticsDefinitionChanged() {
            refreshDisplay();
        }
    };

    private final StrengthVarManagerListener strengthVarManagerListener = new StrengthVarManagerListener() {

        @Override
        public void changedData() {
            refreshDisplay();
        }
    };

    @Override
    public List<? extends Knob> getKnobs() {
        if (getModel() != null) {
            return new ArrayList<>(getModel().getStrengthsAndVars().getStrengths());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    protected JMadModelListener getModelListener() {
        return this.modelListener;
    }

    @Override
    protected StrengthVarManagerListener getStrengthVarManagerListener() {
        return strengthVarManagerListener;
    }

}

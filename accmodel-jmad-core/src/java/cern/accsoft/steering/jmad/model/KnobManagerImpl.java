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

package cern.accsoft.steering.jmad.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cern.accsoft.steering.jmad.domain.elem.Element;
import cern.accsoft.steering.jmad.domain.knob.Knob;
import cern.accsoft.steering.jmad.domain.knob.KnobType;
import cern.accsoft.steering.jmad.domain.knob.attribute.ElementAttribute;
import cern.accsoft.steering.jmad.domain.knob.bean.BeanPropertyKnob;
import cern.accsoft.steering.jmad.model.knob.ModelKnob;
import cern.accsoft.steering.jmad.model.knob.MultiModelKnob;
import cern.accsoft.steering.jmad.model.knob.StatefulKnob;

/**
 * The simplest implementation of a knob-manager
 * 
 * @author kfuchsbe
 */
public class KnobManagerImpl implements KnobManager {

    /** all the available knobs */
    private final Map<String, Knob> customKnobs = new LinkedHashMap<String, Knob>();

    /**
     * the already created element-attribute knobs are stored here to ensure, that we return the same ones, when they
     * are needed.
     */
    private final Map<String, Knob> elementAttributes = new HashMap<String, Knob>();

    /**
     * the already created initial-conditions knobs are stored here to ensure to return always the same.
     */
    private final Map<String, Knob> twissInitialConditions = new HashMap<String, Knob>();

    /** The model to which this {@link KnobManager} belongs to */
    private final JMadModel model;

    public KnobManagerImpl(JMadModel model) {
        this.model = model;
    }

    @Override
    public void addCustomKnob(Knob knob) {

        if (this.customKnobs.size() == 0) {
            this.model.addListener(new AbstractJMadModelListener() {
                @Override
                public void opticsDefinitionChanged() {
                    triggerOpticChanged();
                }
            });
        }

        if (knob instanceof ModelKnob) {
            ((ModelKnob) knob).setModel(this.model);
        } else if (knob instanceof MultiModelKnob) {
            ((MultiModelKnob) knob).addModel(this.model);
        }

        this.customKnobs.put(knob.getKey(), knob);
    }

    @Override
    public List<Knob> getCustomKnobs() {
        return new ArrayList<Knob>(this.customKnobs.values());
    }

    @Override
    public Knob getKnob(KnobType type, String key) {
        Knob knob = null;
        if (KnobType.STRENGTH.equals(type)) {
            knob = model.getStrengthsAndVars().getStrength(key);
        } else if (KnobType.CUSTOM.equals(type)) {
            return this.customKnobs.get(key);
        } else if (KnobType.ELEMENT_ATTRIBUTE.equals(type)) {
            knob = this.elementAttributes.get(key);
            if (knob == null) {
                knob = createElementAttributeKnob(key);
                if (knob != null) {
                    this.elementAttributes.put(knob.getKey(), knob);
                }
            }
        } else if (KnobType.TWISS_INITIAL_CONDITION.equals(type)) {
            knob = this.twissInitialConditions.get(key);
            if (knob == null) {
                knob = createTwissInitialConditionKnob(key);
                if (knob != null) {
                    this.twissInitialConditions.put(knob.getKey(), knob);
                }
            }
        }
        return knob;
    }

    private Knob createElementAttributeKnob(String key) {
        String elementName = ElementAttribute.getElementNameFromKey(key);
        String attributeName = ElementAttribute.getAttributeNameFromKey(key);
        Element element = model.getActiveRange().getElement(elementName);
        return new ElementAttribute(element, attributeName);
    }

    private Knob createTwissInitialConditionKnob(String key) {
        String propertyName = BeanPropertyKnob.getPropertyNameFromKey(key);
        if (propertyName == null) {
            /*
             * this may mean, that the key contains no ".". We then assume, that the given key is only the attribute
             * name
             * 
             * XXX this is of course inconsistent. For reference in the hashmap the real key of the knob shall be used.
             */
            propertyName = key;
        }
        return new TwissInitialConditionKnob(model, propertyName);
    }

    @Override
    public void cleanup() {
        this.elementAttributes.clear();
        this.twissInitialConditions.clear();
    }

    /**
     * this call notifies the registered knobs that the optic in the assigned model has changed. This call is required,
     * as after an optic change the components of custom knobs might be overridden in the model. The knob does not know
     * about that and will always just add a delta in its knob strengths to the model, neglecting the total value of the
     * knob. Under the assumption that loading of an optic overwrites all strengths the current solution is to trigger
     * all custom knobs to write their current total values to the model.
     */
    final void triggerOpticChanged() {
        for (Knob knob : this.getCustomKnobs()) {
            if (knob instanceof StatefulKnob) {
                StatefulKnob statefulKnob = (StatefulKnob) knob;
                statefulKnob.writeCurrentStateToModel(model);
            }
        }
    }
}

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

/*
 * $Id $
 * 
 * $Date$ $Revision$ $Author$
 * 
 * Copyright CERN ${year}, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.model.knob.custom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.domain.knob.KnobType;
import cern.accsoft.steering.jmad.domain.knob.strength.Strength;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.knob.AbstractMultiModelKnob;
import cern.accsoft.steering.jmad.model.knob.StatefulKnob;

/**
 * @author ${user}
 * @version $Revision$, $Date$, $Author$
 */
public class LinearKnob extends AbstractMultiModelKnob implements StatefulKnob {

    /** the logger of the class */
    private static final Logger LOGGER = Logger.getLogger(LinearKnob.class);

    /** defines one factor for each strength */
    private Map<String, Double> strengthFactors = new HashMap<String, Double>();

    /** the actual value of the knob */
    private double actualValue = 0.0;

    /** the name of this knob */
    private String name;

    public LinearKnob(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return "Knob with linear factors, setting only increments.";
    }

    @Override
    protected synchronized void doSetTotalValue(double value) {
        double oldKnobValue = getTotalValue();
        double knobDeltaValue = value - oldKnobValue;

        /* avoid unnecessary read/write operations to the model */
        if (knobDeltaValue == 0.0) {
            return;
        }

        /* we loop over all connected Models */
        for (JMadModel model : super.getConnectedModels()) {
            this.writeDeltaValueToModel(model, knobDeltaValue);
        }

        this.actualValue = value;
    }

    @Override
    public void writeCurrentStateToModel(JMadModel model) {
        /* this approach assumes that a optics load overwrites all defined strengths!! */
        this.writeDeltaValueToModel(model, getTotalValue());
    }

    /**
     * Write a delta knob value to a {@link JMadModel}. For each strength (str) used by the knob, the model will have an
     * updated value:
     * <p>
     * <code>str = old_str + delta_knob * str_factor</code>
     * 
     * @param model the {@link JMadModel} to write the delta value to
     * @param knobDeltaValue the amount of delta to write
     */
    private synchronized void writeDeltaValueToModel(JMadModel model, double knobDeltaValue) {
        for (Entry<String, Double> strengthEntry : this.strengthFactors.entrySet()) {

            /*
             * we only do incremental change of the parameters. This is necessary to allow several knobs acting on the
             * same strengths.
             */
            String strengthName = strengthEntry.getKey();
            double factor = strengthEntry.getValue();
            try {
                double oldStrengthValue = getStrengthValue(model, strengthName);
                double newStrengthValue = (oldStrengthValue + knobDeltaValue * factor);

                setStrengthValue(model, strengthName, newStrengthValue);
            } catch (JMadModelException e) {
                LOGGER.error("Could not set strength [" + strengthName + "] in model [" + model + "]", e);
            }
        }
    }

    /**
     * tries to find the strength of the given name in the model and retrieve the value from it. If the strength is not
     * available (maybe not parsed, since not in a strength file) it tries to get it directly from the model.
     * 
     * @param model the {@link JMadModel} to get the strength from
     * @param strengthName the name of the strength.
     * @return the value
     * @throws JMadModelException if the retrieval of the value fails
     */
    private double getStrengthValue(JMadModel model, String strengthName) throws JMadModelException {
        Strength strength = getStrength(model, strengthName);
        if (strength != null) {
            return strength.getValue();
        } else {
            return model.getValue(strengthName);
        }
    }

    /**
     * Sets the value to the strength of the given name, if the strength exists. This is preferable, because this way
     * the strengths stay consistent. If it does not exist, then it calls directly the setValue method of the model.
     * 
     * @param model the {@link JMadModel} to set the strength in
     * @param value the value to set for the strength
     * @throws JMadModelException if the setting of the value fails
     */
    private void setStrengthValue(JMadModel model, String strengthName, double value) throws JMadModelException {
        double rdValue = roundToDecimalPlaces(value, MAX_DEC_PLACES);
        Strength strength = getStrength(model, strengthName);
        if (strength != null) {
            strength.setValue(rdValue);
        } else {
            model.setValue(strengthName, rdValue);
        }
    }

    /**
     * searches the strength in the model
     * 
     * @param model the {@link JMadModel} to search the strength in
     * @param strengthName the name of the strength
     * @return the strength or <code>null</code> if the named strength does not exist in the model
     */
    private Strength getStrength(JMadModel model, String strengthName) {
        return model.getStrengthsAndVars().getStrength(strengthName);
    }

    @Override
    public String getKey() {
        return this.name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getTotalValue() {
        return this.actualValue;
    }

    /**
     * adds a factor for a strength
     * 
     * @param strengthName the name of the strength
     * @param factor the scaling factor for this strength
     */
    public void addStrengthFactor(String strengthName, double factor) {
        this.strengthFactors.put(strengthName, Double.valueOf(factor));
    }

    /**
     * update the knob definition to the given strength name value mapping. Strengths that are not contained in the
     * mapping are 'trimmed' back to zero. And new strengths are added to the knob. After this call, the knob is defined
     * with exactly the given strength-factor combinations and the strengths involved are trimmed to the value resulting
     * from the current knob value.
     * 
     * @param strengthFactorMapping the mapping between strength name and factor which defines the knob.
     */
    protected synchronized void updateAllStrengthFactor(Map<String, Double> strengthFactorMapping) {
        List<String> processStrengths = new ArrayList<String>();
        for (Entry<String, Double> entry : strengthFactorMapping.entrySet()) {
            if (!this.strengthFactors.containsKey(entry.getKey())) {
                this.strengthFactors.put(entry.getKey(), 0.0);
                LOGGER.warn("Added factor [" + entry.getKey() + " = " + entry.getValue() + "] during update of knob ["
                        + this.getName() + "]");
            }

            /* update the strength factor to the model(s) */
            this.updateStrengthFactor(entry.getKey(), entry.getValue().doubleValue());
            processStrengths.add(entry.getKey());
        }

        if (processStrengths.size() != this.strengthFactors.size()) {
            for (String strengthName : new ArrayList<String>(this.strengthFactors.keySet())) {
                if (!processStrengths.contains(strengthName)) {
                    double value = this.strengthFactors.get(strengthName);
                    this.removeStrengthFactor(strengthName);
                    LOGGER.warn("Removed factor [" + strengthName + " = " + value + "] during update of knob ["
                            + this.getName() + "]");
                }
            }
        }
    }

    /**
     * Remove the factor for the strength with the given name from the linear knob. This call will ensure, that the
     * impact of the strength factor is removed from the underlying models. After this call the strength is not longer
     * available in the linear knob.
     * 
     * @param strengthName the name of the strength to remove from the linear knob
     */
    protected void removeStrengthFactor(String strengthName) {
        if (!this.strengthFactors.containsKey(strengthName)) {
            throw new IllegalArgumentException("Knob does not contain a factor for strength [" + strengthName + "]");
        }

        try {
            double oldFactor = this.strengthFactors.get(strengthName).doubleValue();
            double totalValue = getTotalValue();
            for (JMadModel model : super.getConnectedModels()) {
                double actStrength = this.getStrengthValue(model, strengthName);

                /* remove the impact of the old strength factor from the strength */
                double newStrength = actStrength - (oldFactor * totalValue);
                this.setStrengthValue(model, strengthName, newStrength);
            }

            this.strengthFactors.remove(strengthName);

        } catch (JMadModelException e) {
            LOGGER.error("Could not remove strength factor for [" + strengthName + "] from knob [" + getName() + "]", e);
        }
    }

    /**
     * update one of the knobs strength factors. During update the old strength factors impact on the model is reset and
     * the new factors resulting value written to the model. If the old factor and the new factor are equal, nothing is
     * done.
     * 
     * @param strengthName the name of the the strength whose factor to update
     * @param factor the new value of the scaling factor for this strength
     */
    protected synchronized void updateStrengthFactor(String strengthName, double factor) {
        if (!this.strengthFactors.containsKey(strengthName)) {
            throw new IllegalArgumentException("Knob does not contain a factor for strength [" + strengthName + "]");
        }

        try {
            double oldFactor = this.strengthFactors.get(strengthName).doubleValue();

            if (oldFactor == factor) {
                /* nothing to do here */
                return;
            }

            double totalValue = getTotalValue();
            for (JMadModel model : super.getConnectedModels()) {

                double actStrength = this.getStrengthValue(model, strengthName);

                /* remove the impact of the old strength factor from the strength */
                /* and add the contribution of the new factor to the strength */
                double newStrength = actStrength + ((factor - oldFactor) * totalValue);

                this.setStrengthValue(model, strengthName, newStrength);
            }

            /* overwrite the current factor in the factor map */
            this.addStrengthFactor(strengthName, factor);

        } catch (JMadModelException e) {
            LOGGER.error("Could not update strength factor for [" + strengthName + "] in Knob [" + getName() + "]", e);
        }
    }

    @Override
    public KnobType getType() {
        return KnobType.CUSTOM;
    }

    public static final int MAX_DEC_PLACES = 14;

    public static final double roundToDecimalPlaces(double value, int decPlaces) {
        return Math.round(value * Math.pow(10, decPlaces)) / Math.pow(10, decPlaces);
    }
}

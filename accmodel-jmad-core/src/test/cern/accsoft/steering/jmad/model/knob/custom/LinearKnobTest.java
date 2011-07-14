// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
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
/*
 * $Id $
 * 
 * $Date$ $Revision$ $Author$
 * 
 * Copyright CERN ${year}, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.model.knob.custom;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.domain.knob.strength.SimpleStrength;
import cern.accsoft.steering.jmad.domain.knob.strength.Strength;
import cern.accsoft.steering.jmad.domain.var.custom.StrengthVarSet;
import cern.accsoft.steering.jmad.domain.var.custom.StrengthVarSetImpl;
import cern.accsoft.steering.jmad.model.JMadModel;

public class LinearKnobTest {

    private static JMadModel MODEL_MOCK;
    private static StrengthVarSet MODEL_STRENGTHS;
    private static StrengthVarSet KNOB_STRENGTHS;
    private static LinearKnob KNOB;

    @BeforeClass
    public static void setUp() {
        BasicConfigurator.configure();

        MODEL_MOCK = createMock(JMadModel.class);
        MODEL_STRENGTHS = new StrengthVarSetImpl();
        KNOB_STRENGTHS = new StrengthVarSetImpl();

        KNOB = new LinearKnob("test-knob");
        KNOB.addModel(MODEL_MOCK);

        /* create the strengths */
        List<Strength> model_strs = new ArrayList<Strength>();
        List<Strength> knob_strs = new ArrayList<Strength>();
        for (int i = 0; i < 5; i++) {
            String name = "str" + i;

            KNOB.addStrengthFactor(name, 0.01 + (0.1 * i));
            Strength str = new SimpleStrength(name, 0.01 + (0.1 * i), "blallal" + i);
            knob_strs.add(str);

            str = new SimpleStrength(name, 0.1 - (0.01 * i), "blallal" + i);
            model_strs.add(str);
        }
        MODEL_STRENGTHS.addAllStrengths(model_strs);
        KNOB_STRENGTHS.addAllStrengths(knob_strs);
    }

    @Test
    public void trimKnobPosTest() throws JMadModelException {
        this.trimKnob(2.0);
    }

    @Test
    public void trimKnobNegTest() throws JMadModelException {
        this.trimKnob(-2.0);
    }

    @Test
    public void trimKnobNullTest() throws JMadModelException {
        this.trimKnob(2.0);
    }

    private void trimKnob(double knobValue) throws JMadModelException {
        for (Strength str : MODEL_STRENGTHS.getStrengths()) {
            expect(MODEL_MOCK.getStrengthsAndVars()).andReturn(new StrengthVarSetImpl());
            expect(MODEL_MOCK.getValue(str.getName())).andReturn(str.getTotalValue());
            expect(MODEL_MOCK.getStrengthsAndVars()).andReturn(new StrengthVarSetImpl());

            double knobDelta = knobValue - KNOB.getTotalValue();

            double str_value = str.getTotalValue()
                    + (KNOB_STRENGTHS.getStrength(str.getName()).getTotalValue() * knobDelta);
            str_value = LinearKnob.roundToDecimalPlaces(str_value, LinearKnob.MAX_DEC_PLACES);
            MODEL_MOCK.setValue(eq(str.getName()), eq(str_value));

            /* update the strength in the 'model' */
            MODEL_STRENGTHS.getStrength(str.getName()).setValue(str_value);
        }

        replay(MODEL_MOCK);
        KNOB.setValue(knobValue);
        verify(MODEL_MOCK);
        reset(MODEL_MOCK);
    }

    @Test
    public void addKnobStrength() throws JMadModelException {
        KNOB.addStrengthFactor("str99", 0.99);
        Strength str = new SimpleStrength("str99", 0.99, "comment");
        MODEL_STRENGTHS.addAllStrengths(Collections.singletonList(str));
        str = new SimpleStrength("str99", 0.99, "comment");
        KNOB_STRENGTHS.addAllStrengths(Collections.singletonList(str));

        this.trimKnob(-4.0);
    }

    @Test
    public void removeKnobStrength() throws JMadModelException {
        String name = "str1";
        expect(MODEL_MOCK.getStrengthsAndVars()).andReturn(new StrengthVarSetImpl());
        expect(MODEL_MOCK.getValue(name)).andReturn(MODEL_STRENGTHS.getStrength(name).getTotalValue());
        expect(MODEL_MOCK.getStrengthsAndVars()).andReturn(new StrengthVarSetImpl());

        double value = MODEL_STRENGTHS.getStrength(name).getValue()
                - (KNOB.getTotalValue() * KNOB_STRENGTHS.getStrength(name).getTotalValue());
        value = LinearKnob.roundToDecimalPlaces(value, LinearKnob.MAX_DEC_PLACES);
        MODEL_MOCK.setValue(eq(name), eq(value));

        replay(MODEL_MOCK);
        KNOB.removeStrengthFactor(name);
        verify(MODEL_MOCK);
        reset(MODEL_MOCK);

        MODEL_STRENGTHS.getStrengths().remove(MODEL_STRENGTHS.getStrength(name));
        KNOB_STRENGTHS.getStrengths().remove(KNOB_STRENGTHS.getStrength(name));

        this.trimKnob(4.0);
    }

    @Test
    public void updateComponentsTest() throws JMadModelException {
        Map<String, Double> newComponents = new HashMap<String, Double>();
        for (Strength str : KNOB_STRENGTHS.getStrengths()) {
            newComponents.put(str.getName(), str.getTotalValue());
        }
        newComponents.put("str99", 0.199);
        newComponents.put("str3", 0.13);
        newComponents.put("str5", 0.15);
        newComponents.remove("str2");

        List<String> processedStrengths = new ArrayList<String>();
        for (Entry<String, Double> entry : newComponents.entrySet()) {
            String name = entry.getKey();
            if (MODEL_STRENGTHS.getStrength(entry.getKey()) != null) {
                /* knob strength already exists */
                if (KNOB_STRENGTHS.getStrength(name).getTotalValue() != entry.getValue()) {
                    /* new knob strength factor */
                    expect(MODEL_MOCK.getStrengthsAndVars()).andReturn(new StrengthVarSetImpl());
                    expect(MODEL_MOCK.getValue(name)).andReturn(MODEL_STRENGTHS.getStrength(name).getTotalValue());
                    expect(MODEL_MOCK.getStrengthsAndVars()).andReturn(new StrengthVarSetImpl());

                    double value = MODEL_STRENGTHS.getStrength(name).getValue()
                            - (KNOB.getTotalValue() * KNOB_STRENGTHS.getStrength(name).getTotalValue())
                            + (KNOB.getTotalValue() * entry.getValue());
                    value = LinearKnob.roundToDecimalPlaces(value, LinearKnob.MAX_DEC_PLACES);
                    MODEL_MOCK.setValue(eq(name), eq(value));
                    MODEL_STRENGTHS.getStrength(name).setValue(value);

                    KNOB_STRENGTHS.getStrength(name).setValue(entry.getValue());
                }
            } else {
                /* new knob strength */
                double initialStrengthValue = 0.098;
                expect(MODEL_MOCK.getStrengthsAndVars()).andReturn(new StrengthVarSetImpl());
                expect(MODEL_MOCK.getValue(name)).andReturn(initialStrengthValue);
                expect(MODEL_MOCK.getStrengthsAndVars()).andReturn(new StrengthVarSetImpl());

                double value = initialStrengthValue + (KNOB.getTotalValue() * entry.getValue());
                value = LinearKnob.roundToDecimalPlaces(value, LinearKnob.MAX_DEC_PLACES);
                MODEL_MOCK.setValue(eq(name), eq(value));

                MODEL_STRENGTHS.addAllStrengths(Collections.singletonList((Strength) (new SimpleStrength(name, value,
                        "added"))));
                KNOB_STRENGTHS.addAllStrengths(Collections.singletonList((Strength) (new SimpleStrength(name, entry
                        .getValue(), "added"))));
            }

            processedStrengths.add(name);
        }

        Iterator<Strength> iterator = MODEL_STRENGTHS.getStrengths().iterator();
        while (iterator.hasNext()) {
            Strength str = iterator.next();
            if (!processedStrengths.contains(str.getName())) {
                /* a left over knob strength to be removed */
                String name = str.getName();
                expect(MODEL_MOCK.getStrengthsAndVars()).andReturn(new StrengthVarSetImpl());
                expect(MODEL_MOCK.getValue(name)).andReturn(MODEL_STRENGTHS.getStrength(name).getTotalValue());
                expect(MODEL_MOCK.getStrengthsAndVars()).andReturn(new StrengthVarSetImpl());

                double value = MODEL_STRENGTHS.getStrength(name).getValue()
                        - (KNOB.getTotalValue() * KNOB_STRENGTHS.getStrength(name).getTotalValue());
                value = LinearKnob.roundToDecimalPlaces(value, LinearKnob.MAX_DEC_PLACES);
                MODEL_MOCK.setValue(eq(name), eq(value));

                iterator.remove();
                KNOB_STRENGTHS.getStrengths().remove(KNOB_STRENGTHS.getStrength(name));
            }
        }

        replay(MODEL_MOCK);
        KNOB.updateAllStrengthFactor(newComponents);
        verify(MODEL_MOCK);
        reset(MODEL_MOCK);

        this.trimKnob(0.0);
    }
}

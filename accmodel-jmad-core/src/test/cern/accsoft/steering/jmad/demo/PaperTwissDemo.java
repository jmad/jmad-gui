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
 * $Id: SimpleTwissDemo.java,v 1.3 2009-03-16 16:35:33 kfuchsbe Exp $
 * 
 * $Date: 2009-03-16 16:35:33 $ $Revision: 1.3 $ $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.demo;

import java.util.List;

import cern.accsoft.steering.jmad.domain.elem.Element;
import cern.accsoft.steering.jmad.domain.elem.JMadElementType;
import cern.accsoft.steering.jmad.domain.elem.impl.Monitor;
import cern.accsoft.steering.jmad.domain.elem.impl.Quadrupole;
import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.domain.machine.Range;
import cern.accsoft.steering.jmad.domain.optics.Optic;
import cern.accsoft.steering.jmad.domain.optics.OpticPoint;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsResult;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsResultRequestImpl;
import cern.accsoft.steering.jmad.domain.var.enums.MadxTwissVariable;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.service.JMadService;
import cern.accsoft.steering.jmad.service.JMadServiceFactory;

/**
 * this demo is the example for the ipac-paper 2010
 * 
 * @author kfuchsbe
 */
public class PaperTwissDemo {

    /**
     * @param args
     */
    public static void main(String[] args) {

        /* create a new JMad service */
        JMadService jmadService = JMadServiceFactory.createJMadService();

        /* then find a model definition */
        JMadModelDefinition modelDefinition = jmadService.getModelDefinitionManager().getModelDefinition("example");

        /* create the model and initialize it */
        JMadModel model = jmadService.createModel(modelDefinition);
        try {
            model.init();

            /* e.g. get all the elements */
            @SuppressWarnings("unused")
            List<Element> elements = model.getActiveRange().getElements();

            /* or perform some custom twiss */
            TfsResultRequestImpl request = new TfsResultRequestImpl();
            /* a regexp for the elements we want to retrieve */
            request.addElementFilter("BPM.*");
            /* and the variables, which we want to see */
            request.addVariable(MadxTwissVariable.NAME);
            request.addVariable(MadxTwissVariable.X);
            request.addVariable(MadxTwissVariable.Y);

            /* run the twiss and get the results */
            @SuppressWarnings("unused")
            TfsResult result = model.twiss(request);

            /* finally we cleanup and close the madx-kernel */
            model.cleanup();
        } catch (JMadModelException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    private void getElementsExample(JMadModel model) {
        /* get all the elements */
        List<Element> elements = model.getActiveRange().getElements();

        /* print name and type of each element */
        for (Element element : elements) {
            System.out.println("name: " + element.getName() + "; type: " + JMadElementType.fromElement(element));
        }
    }

    @SuppressWarnings("unused")
    private void opticsExample(JMadModel model) throws JMadModelException {
        Range activeRange = model.getActiveRange();

        /* retrieve an element by name (MONITOR) */
        Monitor monitor = (Monitor) activeRange.getElement("BPMIH.22604");

        /* retrieve the actual optics */
        Optic optic = model.getOptics();

        /* retrieve some optics values */
        List<Double> betaxValues = optic.getValues(MadxTwissVariable.BETX, activeRange.getElements());

        /* retrieve optics values for one element */
        OpticPoint opticPoint = optic.getPoint(monitor);
        double monX = opticPoint.getX();

        /* change a quad strength by 10 percent */
        Quadrupole aQuad = (Quadrupole) activeRange.getElement("MQIF.20400");
        aQuad.setK1(aQuad.getK1() * 1.1);

        /* IMPORTANT: refetch the optic since it has changed! */
        optic = model.getOptics();
    }

    @SuppressWarnings("unused")
    private void customTwiss(JMadModel model) throws JMadModelException {
        TfsResultRequestImpl request = new TfsResultRequestImpl();
        /* a regexp for the elements we want to retrieve */
        request.addElementFilter("BPM.*");
        /* and the variables, which we want to get */
        request.addVariable(MadxTwissVariable.NAME);
        request.addVariable(MadxTwissVariable.X);
        request.addVariable(MadxTwissVariable.Y);

        /* run the twiss and get the results */
        TfsResult result = model.twiss(request);

        List<String> elementNames = result.getStringData(MadxTwissVariable.NAME);
        List<Double> xValues = result.getDoubleData(MadxTwissVariable.X);
        List<Double> yValues = result.getDoubleData(MadxTwissVariable.Y);

        /* print the values */
        for (String name : elementNames) {
            int index = result.getElementIndex(name);
            System.out.println(name + ": X=" + xValues.get(index) + "; Y=" + yValues.get(index) + ";");
        }
    }
}

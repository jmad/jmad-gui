/**
 * Copyright (c) 2015 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package cern.accsoft.steering.jmad.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.domain.elem.Element;
import cern.accsoft.steering.jmad.domain.machine.Range;
import cern.accsoft.steering.jmad.model.JMadModel;

public class JMadModelSeqElementOnNameComparator implements JMadModelComparator {
    /** The logger for the class */
    protected static final Logger LOGGER = Logger.getLogger(JMadModelSeqElementOnNameComparator.class);

    @Override
    public List<Element> getCommonSequenceElements(JMadModel model1, JMadModel model2, SequenceElementFilter filter) {

        List<Element> toReturn = new ArrayList<Element>();
        Range activeRange1 = model1.getActiveRange();
        Range activeRange2 = model2.getActiveRange();
        if (!activeRange1.getName().equals(activeRange2)) {
            LOGGER.warn("Comparing two models [" + model1.getName() + " vs. " + model2.getName()
                    + " with different RANGES selected [" + activeRange1.getName() + " vs " + activeRange2.getName()
                    + "]. Result may differ in multiple entries!");
        }
        for (Element oneElementModel1 : getFilteredElements(activeRange1.getElements(), filter)) {
            for (Element oneElementModel2 : getFilteredElements(activeRange2.getElements(), filter)) {
                if (oneElementModel1.getName().equals(oneElementModel2.getName())) {
                    toReturn.add(oneElementModel1);
                    break;
                }
            }
        }
        return toReturn;
    }

    /**
     * returns the sublist of given elements list that match the given filter.
     * 
     * @param elements
     * @param filter
     * @return
     */
    public static List<Element> getFilteredElements(List<Element> elements, SequenceElementFilter filter) {
        List<Element> toReturn = new ArrayList<Element>();
        for (Element one : elements) {
            if (filter.accepts(one)) {
                toReturn.add(one);
            }
        }
        return toReturn;
    }

}

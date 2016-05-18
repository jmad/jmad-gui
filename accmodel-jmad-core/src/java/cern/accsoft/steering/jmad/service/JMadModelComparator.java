/**
 * Copyright (c) 2015 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package cern.accsoft.steering.jmad.service;

import java.util.List;

import cern.accsoft.steering.jmad.domain.elem.Element;
import cern.accsoft.steering.jmad.model.JMadModel;

/**
 * Interface for a Model elements comparator.
 * 
 * @author agorzaws
 */
public interface JMadModelComparator {

    /**
     * @param model1 a model to compare
     * @param model2 a model to compare
     * @param filter a filter that is applied for each sequence element on both models before the actual comparison is
     *            done.
     * @return a list of elements that are common in both models.
     */
    List<Element> getCommonSequenceElements(JMadModel model1, JMadModel model2, SequenceElementFilter filter);

}

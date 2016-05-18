/**
 * Copyright (c) 2015 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package cern.accsoft.steering.jmad.service;

import cern.accsoft.steering.jmad.domain.elem.Element;

/**
 * An interface for the filter used on the elements.
 * 
 * @author agorzaws
 */
public interface SequenceElementFilter {

    boolean accepts(Element element);

}

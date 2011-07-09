package cern.accsoft.steering.jmad.domain.elem.impl;

import cern.accsoft.steering.jmad.domain.elem.MadxElementType;

/**
 * represents any element for which there exists currently no type in JMad.
 * 
 * @author kaifox
 */
public class UnknownElement extends AbstractElement {

    public UnknownElement(MadxElementType madxElementType, String name) {
        super(madxElementType, name);
    }

}

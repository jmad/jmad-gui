package cern.accsoft.steering.jmad.domain.elem.impl;

import cern.accsoft.steering.jmad.domain.elem.MadxElementType;

/**
 * This element represents in JMad one of the MadX element types <a
 * href="http://mad.web.cern.ch/mad/Introduction/monitors.html">HMONITOR, VMONITOR or MONITOR</a>
 * 
 * @author kfuchsbe
 */
public class Monitor extends AbstractElement {

    public Monitor(MadxElementType madxElementType, String name) {
        super(madxElementType, name);
    }
}

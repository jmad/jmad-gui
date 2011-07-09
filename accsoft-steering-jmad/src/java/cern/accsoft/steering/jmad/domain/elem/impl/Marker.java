package cern.accsoft.steering.jmad.domain.elem.impl;

import cern.accsoft.steering.jmad.domain.elem.MadxElementType;

/**
 * The madx-element <a href="http://mad.web.cern.ch/mad/Introduction/marker.html">MARKER</a>.
 * 
 * @author kfuchsbe
 */
public class Marker extends AbstractElement {

    public Marker(MadxElementType madxElementType, String name) {
        super(madxElementType, name);
    }
}

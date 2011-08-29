/*
 * $Id $
 * 
 * $Date$ $Revision$ $Author$
 * 
 * Copyright CERN ${year}, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.util.xml;

import com.thoughtworks.xstream.XStream;

public abstract class XmlXStreamService<T> extends AbstractXStreamService<T> {

    @Override
    protected XStream newXStreamInstance() {
        return new XStream();
    }
}

/*
 * $Id $
 * 
 * $Date$ $Revision$ $Author$
 * 
 * Copyright CERN ${year}, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.util.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;

public abstract class JsonXStreamService<T> extends AbstractXStreamService<T> {

    @Override
    protected XStream newXStreamInstance() {
        return new XStream(new JsonHierarchicalStreamDriver());
    }
}

/*
 * $Id $
 * 
 * $Date$ $Revision$ $Author$
 * 
 * Copyright CERN ${year}, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.util.xml;

import com.thoughtworks.xstream.XStream;

public abstract class AbstractXStreamService<T> extends GenericXStreamService<T> {

    private XStream xStream;

    @Override
    protected XStream createXStream() {
        this.xStream = this.newXStreamInstance();
        this.initializeXStream(this.xStream);
        return this.xStream;
    }

    /**
     * @return a new XStream instance
     */
    protected abstract XStream newXStreamInstance();

    /**
     * Force implementing classes to initialize the {@link XStream} instance used in this service. E.g. register
     * converters, set aliases, process annotations, ...
     * 
     * @param xstream the {@link XStream} instance used in this service
     */
    protected abstract void initializeXStream(XStream xstream);

    /**
     * @return the {@link XStream} used in the service
     */
    protected XStream getXstream() {
        return this.xStream;
    }
}

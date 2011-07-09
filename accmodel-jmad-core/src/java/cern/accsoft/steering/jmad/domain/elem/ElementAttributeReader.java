/**
 * 
 */
package cern.accsoft.steering.jmad.domain.elem;

import java.util.Collection;

import cern.accsoft.steering.jmad.domain.ex.JMadModelException;

/**
 * This interface provides a method to read attribute properties from MadX.
 * 
 * @author kaifox
 */
public interface ElementAttributeReader {

    /**
     * reads the properties for the given element
     * 
     * @param element the element for which to read the properties
     * @throws JMadModelException if the reading of the attributes fails
     */
    public void readAttributes(Element element) throws JMadModelException;

    /**
     * reads the properties for all the given elements
     * 
     * @param elements the elements for which to read the attributes
     * @throws JMadModelException if the reading of the attributes failed
     */
    public void readAttributes(Collection<Element> elements) throws JMadModelException;

}

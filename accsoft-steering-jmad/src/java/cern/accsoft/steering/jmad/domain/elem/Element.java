package cern.accsoft.steering.jmad.domain.elem;

import java.util.List;

public interface Element {
    public static final String ATTR_SEPARATOR = "->";

    public String getName();

    public Position getPosition();

    public void setPosition(double position);
    
    public void setPosition(Position position);

    public void setAttribute(String name, Double value);

    /**
     * Retrieves the value of the attribute identified by the given name.
     * 
     * @param attributeName the name of the attribute to retrieve
     * @return the value of the attribute of the given name. If the attribute is not available for the element, then
     *         <code>null</code>.
     */
    public Double getAttribute(String attributeName);

    public List<String> getAttributeNames();

    /**
     * @return the exact element type as defined in the madx sequence of this element as enum
     */
    public MadxElementType getMadxElementType();

    public void setAttributesInitialized(boolean attributesInitialized);

    public void setListenersEnabled(boolean listenersEnabled);

    public void addListener(ElementListener listener);

    public void removeListener(ElementListener listener);

}

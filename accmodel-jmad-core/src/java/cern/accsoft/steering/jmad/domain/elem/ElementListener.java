package cern.accsoft.steering.jmad.domain.elem;

public interface ElementListener {
    public void changedAttribute(Element element, String propertyName);
}

package cern.accsoft.steering.jmad.domain.optics;

import java.util.List;

import cern.accsoft.steering.jmad.domain.elem.Element;
import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;
import cern.accsoft.steering.jmad.domain.var.enums.JMadTwissVariable;
import cern.accsoft.steering.jmad.domain.var.enums.MadxTwissVariable;

public interface Optic {

    /**
     * @return the element names
     */
    public List<String> getNames();

    /*
     * all values
     */
    public List<OpticPoint> getAllPoints();

    public List<Double> getAllValues(MadxTwissVariable variable);

    public List<Double> getAllValues(JMadTwissVariable variable, JMadPlane plane);

    /*
     * by elements
     */
    public OpticPoint getPoint(Element element);

    public List<OpticPoint> getPoints(List<Element> elements);

    public List<Double> getValues(MadxTwissVariable variable, List<Element> elements);

    public List<Double> getValues(JMadTwissVariable variable, JMadPlane plane, List<Element> elements);

    /*
     * by names
     */
    public OpticPoint getPointByName(String name);

    public List<OpticPoint> getPointsByNames(List<String> names);

    public List<Double> getValuesByNames(MadxTwissVariable variable, List<String> names);

    public List<Double> getValuesByNames(JMadTwissVariable variable, JMadPlane plane, List<String> names);

}
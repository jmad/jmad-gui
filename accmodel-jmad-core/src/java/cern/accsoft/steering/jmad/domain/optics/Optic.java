// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, Kajetan Fuchsberger. All rights reserved.
 * 
 * JMad is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JMad is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JMad.  If not, see <http://www.gnu.org/licenses/>.
 * 
 ******************************************************************************/
// @formatter:on
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

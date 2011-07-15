// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

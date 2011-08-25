// @formatter:off
/*******************************************************************************
 * This file is part of JMad. Copyright (c) 2008-2011, CERN. All rights reserved. Licensed under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy
 * of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
// @formatter:on

package cern.accsoft.steering.jmad.tools.interpolate;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cern.accsoft.steering.jmad.domain.elem.Element;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsResult;
import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;
import cern.accsoft.steering.jmad.domain.var.enums.MadxTwissVariable;

public class OrbitInterpolationResultImpl implements OrbitInterpolationResult {

    /** the collected values for the interpolation per element */
    private Map<Element, Map<JMadPlane, Map<MadxTwissVariable, Double>>> perElementValues = new HashMap<Element, Map<JMadPlane, Map<MadxTwissVariable, Double>>>();

    /** the collected values of the interpolation per plane */
    private Map<JMadPlane, Map<Element, Map<MadxTwissVariable, Double>>> perPlaneValues = null;

    /** the result of the interpolation is just an empty tfs result at the beginning */
    private TfsResult result = new InterpolationTfsResult(
            new HashMap<Element, Map<JMadPlane, Map<MadxTwissVariable, Double>>>());

    @Override
    public TfsResult getTfsResult() {
        return this.result;
    }

    public void addValuesPerElement(Map<Element, Map<JMadPlane, Map<MadxTwissVariable, Double>>> valueMapping) {
        perElementValues.putAll(valueMapping);
    }

    public void addValuesPerPlane(JMadPlane plane, Map<Element, Map<MadxTwissVariable, Double>> valueMapping) {
        if (this.perPlaneValues == null) {
            this.perPlaneValues = new HashMap<JMadPlane, Map<Element, Map<MadxTwissVariable, Double>>>();
            for (JMadPlane jmadPlane : JMadPlane.values()) {
                this.perPlaneValues.put(jmadPlane, new HashMap<Element, Map<MadxTwissVariable, Double>>());
            }
        }

        this.perPlaneValues.get(plane).putAll(valueMapping);
    }

    public void create() {
        if (this.perElementValues.size() > 0 && this.perPlaneValues != null) {
            throw new IllegalStateException("Orbit interpolation result MUST either be filled per element "
                    + "or per plane, not both at the same time!");
        }

        if (this.perPlaneValues != null) {
            for (JMadPlane plane : JMadPlane.values()) {
                for (Entry<Element, Map<MadxTwissVariable, Double>> entry : this.perPlaneValues.get(plane).entrySet()) {
                    Map<JMadPlane, Map<MadxTwissVariable, Double>> elementValues = this.perElementValues.get(entry
                            .getKey());
                    if (elementValues == null) {
                        elementValues = new HashMap<JMadPlane, Map<MadxTwissVariable, Double>>();
                        this.perElementValues.put(entry.getKey(), elementValues);
                    }

                    elementValues.put(plane, entry.getValue());
                }
            }
        }

        this.result = new InterpolationTfsResult(this.perElementValues);
    }
}

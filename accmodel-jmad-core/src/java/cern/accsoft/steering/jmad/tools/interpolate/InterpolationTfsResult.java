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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.domain.elem.Element;
import cern.accsoft.steering.jmad.domain.result.ResultType;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsResult;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsSummary;
import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;
import cern.accsoft.steering.jmad.domain.var.MadxVariable;
import cern.accsoft.steering.jmad.domain.var.enums.MadxTwissVariable;
import cern.accsoft.steering.jmad.util.MadxVarType;

public class InterpolationTfsResult implements TfsResult {
    private static final Logger LOGGER = Logger.getLogger(InterpolationTfsResult.class);
    /** the twiss variables available from this interpolation result */
    private Set<MadxTwissVariable> twissVariables = new HashSet<MadxTwissVariable>();

    /** the double value list mapped to the madx twiss variables */
    private Map<MadxTwissVariable, List<Double>> doubleValueMapping = new HashMap<MadxTwissVariable, List<Double>>();
    /** the string value list mapped to the madx twiss variables */
    private Map<MadxTwissVariable, List<String>> stringValueMapping = new HashMap<MadxTwissVariable, List<String>>();

    /** the indices of the elements in the mappings */
    private Map<String, Integer> elementIndices = new HashMap<String, Integer>();

    public InterpolationTfsResult(Map<Element, Map<JMadPlane, Map<MadxTwissVariable, Double>>> valueMapping) {
        /* fill the data mappings */
        this.twissVariables.add(MadxTwissVariable.S);
        this.twissVariables.add(MadxTwissVariable.NAME);
        for (Entry<Element, Map<JMadPlane, Map<MadxTwissVariable, Double>>> elementEntry : valueMapping.entrySet()) {
            for (Entry<JMadPlane, Map<MadxTwissVariable, Double>> planeEntry : elementEntry.getValue().entrySet()) {
                this.twissVariables.addAll(planeEntry.getValue().keySet());
            }
            break;
        }

        for (MadxTwissVariable variable : twissVariables) {
            if (!MadxTwissVariable.NAME.equals(variable)) {
                this.doubleValueMapping.put(variable, new ArrayList<Double>());
            }

            this.stringValueMapping.put(variable, new ArrayList<String>());
        }

        int i = 0;
        for (Entry<Element, Map<JMadPlane, Map<MadxTwissVariable, Double>>> elementEntry : valueMapping.entrySet()) {
            Element element = elementEntry.getKey();
            /* fill the index mapping */
            this.elementIndices.put(element.getName(), i++);

            /* fill the element values */
            this.stringValueMapping.get(MadxTwissVariable.NAME).add(element.getName());
            this.doubleValueMapping.get(MadxTwissVariable.S).add(element.getPosition().getValue());

            /* fill the values for the both planes for the availabe twiss variables */
            for (Entry<JMadPlane, Map<MadxTwissVariable, Double>> planeEntry : elementEntry.getValue().entrySet()) {
                for (Entry<MadxTwissVariable, Double> varEntry : planeEntry.getValue().entrySet()) {
                    this.doubleValueMapping.get(varEntry.getKey()).add(varEntry.getValue());
                }
            }
        }

        /* fill the string mappings */
        for (Entry<MadxTwissVariable, List<Double>> entry : this.doubleValueMapping.entrySet()) {
            List<String> stringValues = this.stringValueMapping.get(entry.getKey());
            for (Double value : entry.getValue()) {
                stringValues.add(value.toString());
            }
        }
    }

    @Override
    public ResultType getResultType() {
        return ResultType.TFS_RESULT;
    }

    @Override
    public int getColumnCount() {
        return twissVariables.size();
    }

    @Override
    public TfsSummary getSummary() {
        throw new UnsupportedOperationException(
                "Retrieval of twiss-summary from interpolated optics is not supported at the moment!");
    }

    @Override
    public List<Double> getDoubleData(MadxVariable resultVariable) {
        if (!this.doubleValueMapping.containsKey(resultVariable)) {
            LOGGER.error("No data available for [" + resultVariable.getName() + "]");
            return new ArrayList<Double>();
        }

        return this.doubleValueMapping.get(resultVariable);
    }

    @Override
    public List<Double> getDoubleData(String key) {
        MadxTwissVariable twissVar = MadxTwissVariable.fromMadxName(key);
        if (MadxTwissVariable.UNKNOWN.equals(twissVar)) {
            LOGGER.error("Unknown twiss variable [" + key + "]");
            return new ArrayList<Double>();
        }

        return this.getDoubleData(twissVar);
    }

    @Override
    public List<String> getStringData(MadxVariable resultVariable) {
        if (!this.stringValueMapping.containsKey(resultVariable)) {
            LOGGER.error("No data available for [" + resultVariable.getName() + "]");
            return new ArrayList<String>();
        }

        return this.stringValueMapping.get(resultVariable);
    }

    @Override
    public List<String> getStringData(String key) {
        MadxTwissVariable twissVar = MadxTwissVariable.fromMadxName(key);
        if (MadxTwissVariable.UNKNOWN.equals(twissVar)) {
            LOGGER.error("Unknown twiss variable [" + key + "]");
            return new ArrayList<String>();
        }

        return this.getStringData(twissVar);
    }

    @Override
    public List<String> getKeys() {
        List<String> keys = new ArrayList<String>();
        for (MadxTwissVariable variable : twissVariables) {
            keys.add(variable.getName().toUpperCase());
        }

        return keys;
    }

    @Override
    public Integer getElementIndex(String elementName) {
        return this.elementIndices.get(elementName);
    }

    @Override
    public MadxVarType getVarType(String key) {
        MadxTwissVariable variable = MadxTwissVariable.fromMadxName(key);
        if (MadxTwissVariable.UNKNOWN.equals(variable)) {
            LOGGER.error("Unknown twiss variable [" + key + "]");
            return MadxVarType.UNKNOWN;
        }

        return variable.getVarType();
    }

    @Override
    public MadxVarType getVarType(MadxVariable var) {
        return getVarType(var.getMadxName());
    }

}

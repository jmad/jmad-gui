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

package cern.accsoft.steering.jmad.domain.result.tfs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cern.accsoft.steering.jmad.domain.elem.MadxElementType;
import cern.accsoft.steering.jmad.domain.var.MadxVariable;
import cern.accsoft.steering.jmad.domain.var.TwissVariable;
import cern.accsoft.steering.jmad.domain.var.enums.MadxTwissVariable;
import cern.accsoft.steering.jmad.domain.var.enums.PtcTwissVariable;
import cern.accsoft.steering.jmad.util.ListUtil;
import cern.accsoft.steering.jmad.util.ListUtil.Mapper;

/**
 * this class provides a way to define, what results one wants to get back from a MadX twiss - Task.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 * @author muellerg
 */
public class TfsResultRequestImpl implements TfsResultRequest {

    /** the variables, which one wants to get back */
    private final List<TwissVariable> resultVariables = new ArrayList<TwissVariable>();

    /** the element (names) one wants to get back */
    private final List<String> elementFilters = new ArrayList<String>();

    /** the element (classes) one wants to get back */
    private final List<MadxElementType> elementTypes = new ArrayList<MadxElementType>();

    /**
     * adds an filter (element name or wildcard) to requested elements.
     * 
     * @param filter the element name / wildcard
     */
    public void addElementFilter(String filter) {
        elementFilters.add(filter);
    }

    /**
     * adds more filters (element names or wildcards) to the requested elements.
     * 
     * @param filters the names wildcards to add
     */
    public void addElementFilters(Collection<String> filters) {
        for (String filter : filters) {
            this.addElementFilter(filter);
        }
    }

    /**
     * adds an MadxElementType to the filters. This represents an element-class in madx
     * 
     * @param madxElementType the element type to add to the filters
     */
    public void addElementType(MadxElementType madxElementType) {
        this.elementTypes.add(madxElementType);
    }

    /**
     * adds more than one class filters to the request.
     * 
     * @param madxElementTypes the MadX element types for which to request results
     */
    public void addElementTypes(Collection<MadxElementType> madxElementTypes) {
        for (MadxElementType madxElementType : madxElementTypes) {
            this.addElementType(madxElementType);
        }
    }

    /**
     * adds a variable to the list of requested variables.
     * 
     * @param resultVariable the variable to add
     */
    public void addVariable(TwissVariable resultVariable) {
        resultVariables.add(resultVariable);
    }

    /**
     * adds more variables to the Request
     * 
     * @param variables the variables to add
     */
    public void addVariables(List<? extends TwissVariable> variables) {
        for (TwissVariable variable : variables) {
            this.addVariable(variable);
        }
    }

    @Override
    public List<TwissVariable> getResultVariables() {
        return resultVariables;
    }

    @Override
    public List<String> getElementPattern() {
        return elementFilters;
    }

    @Override
    public List<String> getElementClasses() {
        return ListUtil.map(this.elementTypes, new Mapper<MadxElementType, String>() {
            @Override
            public String map(MadxElementType inobject) {
                return inobject.getMadxName();
            }
        });
    }

    /**
     * This is a static factory method for a special result request for an empty table. When this request is used for a
     * twiss, then only the summary is returned.
     * <p>
     * It works like this: Since madx does not know the class UNKNOWN and also not the column UNKNOWN it creates a twiss
     * file, with only the summary table. This is useful in some cases.
     * <p>
     * This is not very nice. Maybe there is a better way to do this?
     * 
     * @return a {@link TfsResultRequestImpl} which is ready to be used for twissing, resulting in the summary table
     *         only.
     */
    public static final TfsResultRequest createSummaryOnlyRequest() {
        TfsResultRequestImpl request = new TfsResultRequestImpl();
        request.addElementType(MadxElementType.UNKNOWN);
        request.addVariable(MadxTwissVariable.UNKNOWN);
        return request;
    }

    /**
     * This is a static factory method for a default result request. Only Name is already appended to the list of
     * requested variables. This way it is ensured, that the TfsResult provides access via element names. The user only
     * needs to append the elementFilters and additional variables required.
     * 
     * @return a {@link TfsResultRequestImpl} which is prepared to enable access of values via element name.
     */
    public static final TfsResultRequestImpl createDefaultRequest() {
        TfsResultRequestImpl request = new TfsResultRequestImpl();
        request.addVariable(MadxTwissVariable.NAME);
        return request;
    }

    @Override
    public boolean needsPtcTwiss() {
        for (MadxVariable variable : getResultVariables()) {
            /*
             * if one of the variables is a ptc-variable then we will need a ptc-twiss.
             */
            if (variable instanceof PtcTwissVariable) {
                return true;
            }
        }
        return false;
    }

}

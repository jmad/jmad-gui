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

/*
 * $Id: RegexNameFilter.java,v 1.2 2008-08-25 13:52:12 kfuchsbe Exp $
 * 
 * $Date: 2008-08-25 13:52:12 $ $Revision: 1.2 $ $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.domain.machine.filter;

import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * basic implementation of {@link NameFilter} using regexpressions.
 * 
 * @author kfuchsbe
 */
@XStreamAlias("regex-filter")
public class RegexNameFilter implements NameFilter {

    /* the regex to filter on */
    @XStreamAlias("regex")
    @XStreamAsAttribute
    private String regex;

    @XStreamAlias("plane")
    @XStreamAsAttribute
    private JMadPlane plane;

    /**
     * simplest constructor
     */
    public RegexNameFilter() {
        super();
    }

    /**
     * constructor with setting the regex immediately
     * 
     * @param regex the regexpression representing the concerned element-names.
     * @param plane the plane in which the filter has to act
     */
    public RegexNameFilter(String regex, JMadPlane plane) {
        this();
        setRegex(regex);
        setModelPlane(plane);
    }

    /**
     * set the regular expression to a new value.
     * 
     * @param regex the string to set as new filter - expression
     */
    public final void setRegex(String regex) {
        this.regex = regex;
    }

    /**
     * @param plane the {@link JMadPlane} to set.
     */
    public final void setModelPlane(JMadPlane plane) {
        this.plane = plane;
    }

    @Override
    public boolean isConcerned(String name, JMadPlane planeToCheck) {
        if ((regex == null) || (this.plane == null)) {
            return false;
        } else {
            return ((name.matches(regex)) && (this.plane.equals(planeToCheck)));
        }
    }
}

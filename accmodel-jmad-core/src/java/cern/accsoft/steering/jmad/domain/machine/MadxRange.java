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

/**
 * 
 */
package cern.accsoft.steering.jmad.domain.machine;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * the definition of a range
 * 
 * @author muellerg
 */
@XStreamAlias("madx-range")
public class MadxRange {

    public static final String ELEMENT_SEPARATOR = "/";

    /*
     * the names of the first and the last element in the range. by default we use the whole sequence, which corresponds
     * to #s/#e.
     */
    @XStreamAlias("first")
    @XStreamAsAttribute
    private String firstElementName = "#s";

    @XStreamAlias("last")
    @XStreamAsAttribute
    private String lastElementName = "#e";

    /**
     * Create a new FullRange of Sequence
     */
    public MadxRange() {
        /* nothing to do --> see init */
    }

    public MadxRange(String elementName) {
        this(elementName, null);
    }

    public MadxRange(String firstElementName, String lastElementName) {
        this.firstElementName = firstElementName;
        this.lastElementName = lastElementName;
    }

    public String getMadxString() {
        if (this.lastElementName == null) {
            return this.firstElementName;
        }

        return this.firstElementName + ELEMENT_SEPARATOR + this.lastElementName;
    }

    public boolean isElement() {
        return this.lastElementName == null;
    }
}

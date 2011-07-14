// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
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

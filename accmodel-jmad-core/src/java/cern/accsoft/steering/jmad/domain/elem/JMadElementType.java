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
package cern.accsoft.steering.jmad.domain.elem;

import cern.accsoft.steering.jmad.domain.elem.impl.BeamBeam;
import cern.accsoft.steering.jmad.domain.elem.impl.Bend;
import cern.accsoft.steering.jmad.domain.elem.impl.Corrector;
import cern.accsoft.steering.jmad.domain.elem.impl.Marker;
import cern.accsoft.steering.jmad.domain.elem.impl.Monitor;
import cern.accsoft.steering.jmad.domain.elem.impl.Octupole;
import cern.accsoft.steering.jmad.domain.elem.impl.Quadrupole;
import cern.accsoft.steering.jmad.domain.elem.impl.Sextupole;
import cern.accsoft.steering.jmad.domain.elem.impl.UnknownElement;

/**
 * Represents the type of an element in JMad. This type groups together some of the basic element-types of madx, which
 * themselves are represented one to one by {@link MadxElementType} Most importantly this enum also contains the
 * information, which classes will be instancated for every type.
 * 
 * @author kfuchsbe
 */
public enum JMadElementType {
    /**
     * An element for which there exists currently no dedicated element class in JMad
     */
    UNKNOWN(UnknownElement.class), //
    /**
     * A marker in MadX
     */
    MARKER(Marker.class), //
    /**
     * Any type of orbit corrector in MadX
     */
    CORRECTOR(Corrector.class, "*KICKER"), //
    /**
     * Any type of beam position monitor in MadX
     */
    MONITOR(Monitor.class, "*MONITOR"), //
    /**
     * Any type of bending magnet in MadX
     */
    BEND(Bend.class), //
    /**
     * A quadrupole in MadX
     */
    QUADRUPOLE(Quadrupole.class), //
    /**
     * a sextupole in MadX
     */
    SEXTUPOLE(Sextupole.class), //
    /**
     * an octupole in MadX
     */
    OCTUPOLE(Octupole.class), //
    /**
     * a beam-beam interaction point in MadX
     */
    BEAMBEAM(BeamBeam.class);

    /** a wildcard expression, which can be used e.g. for searches in a database */
    private String wildcardExpression = this.name();

    /** the java class which has to be used to create the element */
    private Class<? extends Element> elementClass;

    /**
     * default constructor
     * 
     * @param elementClass The class that shall be instantiated for this type
     * @see #elementClass
     */
    private JMadElementType(Class<? extends Element> elementClass) {
        this.elementClass = elementClass;
    }

    /**
     * constructor with possibility to provide the element class and a wildcard extension.
     * 
     * @param elementClass the class that shall be instantiated for this element-type.
     * @param wildcardExpression an wildcard expression which represents the madx names of this type. (This is no
     *            regular expression!)
     */
    private JMadElementType(Class<? extends Element> elementClass, String wildcardExpression) {
        this(elementClass);
        this.wildcardExpression = wildcardExpression;
    }

    public String getWildcardExpression() {
        return wildcardExpression;
    }

    /**
     * returns true, if the given element is of the type.
     * 
     * @param element the element to check
     * @return true, if the given element is of the type, false if not.
     */
    public boolean isTypeOf(Element element) {
        return getElementClass().isInstance(element);
    }

    public Class<? extends Element> getElementClass() {
        return elementClass;
    }

    /**
     * determines the {@link JMadElementType} of the given element.
     * 
     * @param element the element for which to get the element type
     * @return the element type
     */
    public static final JMadElementType fromElement(Element element) {
        for (JMadElementType type : JMadElementType.values()) {
            if (type.isTypeOf(element)) {
                return type;
            }
        }
        return null;
    }
}

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
package cern.accsoft.steering.jmad.domain.elem;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.util.ListUtil;

/**
 * The values of this enum represent exactly the (currently implemented) madx element classes. Every
 * {@link MadxElementType} is also associated with one (more general) jmad-internal type. This {@link JMadElementType}
 * then determines which class-instance represents the type in jmad. (e.g. there is only one class for bends in jmad)
 * 
 * @author kfuchsbe
 */
public enum MadxElementType {
    /*
     * some types that are also mapped to a special element type in jmad
     */
    MARKER(JMadElementType.MARKER), //
    KICKER(JMadElementType.CORRECTOR), //
    VKICKER(JMadElementType.CORRECTOR), //
    HKICKER(JMadElementType.CORRECTOR), // 
    MONITOR(JMadElementType.MONITOR), //
    VMONITOR(JMadElementType.MONITOR), //
    HMONITOR(JMadElementType.MONITOR), //
    RBEND(JMadElementType.BEND), //
    SBEND(JMadElementType.BEND), //
    QUADRUPOLE(JMadElementType.QUADRUPOLE), //
    SEXTUPOLE(JMadElementType.SEXTUPOLE), //
    OCTUPOLE(JMadElementType.OCTUPOLE), //
    BEAMBEAM(JMadElementType.BEAMBEAM), //
    
    /*
     * The following types are not (yet) mapped to a corresponding type in jmad
     */
    INSTRUMENT(JMadElementType.UNKNOWN), //
    MULTIPOLE(JMadElementType.UNKNOWN), //
    RFCAVITY(JMadElementType.UNKNOWN), //
    RCOLLIMATOR(JMadElementType.UNKNOWN), //
    TKICKER(JMadElementType.UNKNOWN), //
    SOLENOID(JMadElementType.UNKNOWN), //

    /*
     * the following is for elements which have not (yet) a corresponding type here
     */
    UNKNOWN(JMadElementType.UNKNOWN) {
        @Override
        public String getMadxName() {
            /*
             * this has no associated madx-type! -> returns a special value
             */
            return "jmad_unknown";
        }
    };

    /**
     * The (more generic) jmad - element type which is associated with this madx element-type.
     */
    private JMadElementType elementType;

    private MadxElementType(JMadElementType elementType) {
        this.elementType = elementType;
    }

    /**
     * compares case insensitive.
     * 
     * @param madxName a name as e.g. given in a madx sequence.
     * @return true, if the given string equals the name, false if not
     */
    public boolean equalsMadxName(String madxName) {
        return this.name().equalsIgnoreCase(madxName);
    }

    /**
     * returns true, of the JMad element-type for the {@link MadxElementType} corresponds to the given one.
     * 
     * @param other the element type to compare with
     * @return true, if they are the same
     */
    public boolean equalsJMadElementType(JMadElementType other) {
        return this.elementType.equals(other);
    }

    /**
     * this method must return the correct name of this type (in madx this is called element-class). If the type has not
     * associated class in madx (which should be only the case for UNKNOWN) then it must return <code>null</code>.
     * 
     * @return the class name for use in madx
     */
    public String getMadxName() {
        return this.name().toLowerCase();
    }

    /**
     * returns an madx-element-type from the given madx-name
     * 
     * @param madxName the name used in madx
     * @return the {@link MadxElementType} which corresponds to the name
     */
    public static final MadxElementType fromMadXName(String madxName) {
        for (MadxElementType type : MadxElementType.values()) {
            if (type.equalsMadxName(madxName)) {
                return type;
            }
        }
        return MadxElementType.UNKNOWN;
    }

    /**
     * returns all the madx-element-types, which correspond to the given (jmad-internal) {@link JMadElementType}.
     * 
     * @param elementType the {@link JMadElementType} for which to find the madx-types
     * @return all the madx element types.
     */
    public static final List<MadxElementType> fromElementType(JMadElementType elementType) {
        List<MadxElementType> madxElementTypes = new ArrayList<MadxElementType>();
        for (MadxElementType type : MadxElementType.values()) {
            if (type.equalsJMadElementType(elementType)) {
                madxElementTypes.add(type);
            }
        }
        return madxElementTypes;
    }

    /**
     * finds all the names of the madx-element-types for one (jmad-internal) {@link JMadElementType}.
     * 
     * @param elementType the {@link JMadElementType} for which to find the madx-names
     * @return the names of all madx-element-types for the given {@link JMadElementType}
     */
    public static final List<String> fromElementTypeAsStrings(JMadElementType elementType) {
        List<MadxElementType> madxElementTypes = fromElementType(elementType);
        return ListUtil.toString(madxElementTypes);
    }

    /**
     * @return the (more general) Element-Type associated with this element
     */
    public JMadElementType getElementType() {
        return elementType;
    }
}

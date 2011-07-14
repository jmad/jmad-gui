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
/*
 * $Id: MisalignmentConfiguration.java,v 1.2 2009-01-20 19:43:11 kfuchsbe Exp $
 * 
 * $Date: 2009-01-20 19:43:11 $ $Revision: 1.2 $ $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.domain.misalign;

import java.util.ArrayList;
import java.util.List;

/**
 * A misalignment-configuration for one element.
 * 
 * @author kfuchsbe
 */
public class MisalignmentConfiguration {

    /**
     * the name-prefix, which we use for the misalignment-name.
     */
    private static final String NAME_PREFIX = "misalign";

    /** the listeners */
    private final List<MisalignmentConfigurationListener> listeners = new ArrayList<MisalignmentConfigurationListener>();

    /** the element to which to apply the misalignment */
    private final String elementName;

    /** the misalignment to apply */
    private final Misalignment misalignment;

    /**
     * the constructor which sets the immutable fields
     * 
     * @param elementName the name of the element to which this configuration applies.
     */
    public MisalignmentConfiguration(String elementName) {
        this.elementName = elementName;
        this.misalignment = new Misalignment(NAME_PREFIX + "-" + elementName);
        this.misalignment.addListener(new MisalignmentListener() {

            @Override
            public void changedValues(Misalignment changedMisalignment) {
                fireChangedMisalignmentValues();
            }
        });
    }

    /**
     * @return the misalignment
     */
    public final Misalignment getMisalignment() {
        return misalignment;
    }

    /**
     * @return the elementName
     */
    public String getElementName() {
        return elementName;
    }

    /*
     * methods concerning listeners
     */

    /**
     * notify the listeners, that values in the misalignment changed
     */
    private void fireChangedMisalignmentValues() {
        for (MisalignmentConfigurationListener listener : this.listeners) {
            listener.changedMisalignmentValues(this);
        }
    }

    /**
     * @param listener the listener to add
     */
    public void addListener(MisalignmentConfigurationListener listener) {
        this.listeners.add(listener);
    }

    /**
     * @param listener the listener to remove
     */
    public void removeListener(MisalignmentConfigurationListener listener) {
        this.listeners.remove(listener);
    }
}

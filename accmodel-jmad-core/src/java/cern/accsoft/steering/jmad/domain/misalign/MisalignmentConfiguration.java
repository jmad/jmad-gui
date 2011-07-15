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

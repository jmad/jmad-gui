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
 * $Id $
 * 
 * $Date$ $Revision$ $Author$
 * 
 * Copyright CERN ${year}, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.tools.interpolate;

import java.util.List;
import java.util.Set;

import cern.accsoft.steering.jmad.domain.elem.Element;
import cern.accsoft.steering.jmad.domain.optics.Optic;
import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;

public interface UpdateRequest {

    /**
     * Determine if this request is a structural update.
     * 
     * @param plane the {@link JMadPlane} to check if the structure needs to be updated
     * @return <code>true</code> if a strucure update is required
     */
    public abstract boolean updateStructure(JMadPlane plane);

    /**
     * @return the list of {@link Element}s defining the machine to update the interpolation tool with.
     */
    public abstract List<Element> getMachineElements();

    /**
     * Get the monitors to use for the interpolation. Only the elements returned by this method are used as measurement
     * data input elements during interpolation. It is split up in planes, as it might be possible, that a monitor has
     * only a good reading for one plane.
     * 
     * @param plane the plane for which to retrieve the active monitors
     * @return the monitors to use for the interpolation
     */
    public abstract Set<Element> getMonitors(JMadPlane plane);

    /**
     * @return <code>true</code> if the machine elements provided for a structure update are describing a circular
     *         accelerator.
     */
    public abstract boolean isCircularMachine();

    /**
     * @return <code>true</code> if the optics used for the interpolation should be updated.
     */
    public abstract boolean updateMachineOptics();

    /**
     * @return the optic to update the interpolation tool with.
     */
    public abstract Optic getOptic();
}

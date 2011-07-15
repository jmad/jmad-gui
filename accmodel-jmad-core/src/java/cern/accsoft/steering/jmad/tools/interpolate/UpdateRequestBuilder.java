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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cern.accsoft.steering.jmad.domain.elem.Element;
import cern.accsoft.steering.jmad.domain.optics.Optic;
import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;

/**
 * Builder for {@link UpdateRequest} objects.
 * <p>
 * Usage example (for a circular machine):
 * 
 * <pre>
 * UpdateRequestBuilder builder = new UpdateRequestBuilder(true);</b>
 * UpdateRequest request = builder.setOptic(YOUR_OPTIC)</b>
 *                              .setElements(YOUR_ELEMENTS)</b>
 *                              .setActiveMonitors(H, YOUR_H_MONITORS)</b>
 *                              .setActiveMonitors(V, YOUR_V_MONITORS)</b>
 *                              .build();
 * </pre>
 * 
 * and later on, when only your active monitors have changed:
 * 
 * <pre>
 * UpdateRequest request = builder.setActiveMonitors(H, YOUR_H_MONITORS)</b>
 *                              .setActiveMonitors(V, YOUR_V_MONITORS)</b>
 *                              .build();
 * </pre>
 * 
 * and so forth and so on...
 * <p>
 * This Builder can be reused for multiple update calls and is thread save, as both set-methods and the build method are
 * synchronized. Therefore it is possible to set optic and elements in one thread and use the builder in another one,
 * where the actual update is performed.
 * 
 * @author muellerg
 */
public class UpdateRequestBuilder {
    /** flag determining if it is a transfer line/linear accelerator or a synchrotron */
    private boolean isCircularMachine;

    /** the list of elements defining the machine */
    private List<Element> machineElements = Collections.emptyList();
    /** flag determining if the elements have been updated */
    private boolean elementsUpdated = false;

    /** the machine optic defining the optic functions at all elements in the machine */
    private Optic machineOptic = null;
    /** flag determining if the optics have been updated */
    private boolean opticUpdated = false;

    /** the set of monitors to use for the interpolation */
    private Map<JMadPlane, Set<Element>> interpolationMonitors = new HashMap<JMadPlane, Set<Element>>();
    /** flag determining if the active monitors have been updated */
    private Map<JMadPlane, Boolean> monitorsUpdated;

    /**
     * The constructor of a update request builder.
     * 
     * @param isCirularMachine pass <code>true</code> if this update request builder is for a circular
     *            machine/synchrotron
     */
    public UpdateRequestBuilder(boolean isCirularMachine) {
        this.isCircularMachine = isCirularMachine;
        this.monitorsUpdated = new HashMap<JMadPlane, Boolean>();
        for (JMadPlane plane : JMadPlane.values()) {
            this.monitorsUpdated.put(plane, false);
        }
    }

    /**
     * Set the optic to use in the update request.
     * 
     * @param optic the optic to use
     * @return the builder
     * @throws IllegalArgumentException if null is passed
     */
    public synchronized UpdateRequestBuilder setOptic(Optic optic) {
        if (optic == null) {
            throw new IllegalArgumentException("optic to create a update request from MUST no be 'null'");
        }

        this.machineOptic = optic;
        this.opticUpdated = true;
        return this;
    }

    /**
     * Set the elements defining the machine.
     * 
     * @param elements the list of elements
     * @return the builder
     * @throws IllegalArgumentException in case the provided elements are null or an empty is passed
     */
    public synchronized UpdateRequestBuilder setElements(List<Element> elements) {
        if (elements == null) {
            throw new IllegalArgumentException("elements to create a update request with, MUST not be 'null'");
        }
        if (elements.size() <= 0) {
            throw new IllegalArgumentException("at least one element is required to create a update request");
        }

        this.elementsUpdated = true;
        this.machineElements = elements;

        return this;
    }

    /**
     * Set the active monitors for a given plane in the builder.
     * 
     * @param plane the {@link JMadPlane} to set the active monitors for
     * @param monitors the set of monitors to set active
     * @return the builder
     * @throws IllegalArgumentException if the provided set of monitor elements is null, or does not contain at least
     *             two monitors
     */
    public synchronized UpdateRequestBuilder setActiveMonitors(JMadPlane plane, Set<Element> monitors) {
        if (monitors == null) {
            throw new IllegalArgumentException("monitors to set active in update request, MUST not be 'null'");
        }

        if (monitors.size() < 2) {
            throw new IllegalArgumentException("there MUST be at least 2 monitors to set active in update request");
        }

        this.interpolationMonitors.put(plane, monitors);
        this.monitorsUpdated.put(plane, true);
        return this;
    }

    /**
     * Create a full update request in case the machine elements changed.
     * 
     * @param elements the list of elements to use
     * @param monitors the list of monitors to use
     * @param optic the optic to use
     * @return the builder
     * @throws IllegalArgumentException if one of the arguments is illegal
     */
    public synchronized UpdateRequestBuilder fullUpdate(List<Element> elements, Map<JMadPlane, Set<Element>> monitors,
            Optic optic) {
        this.setElements(elements);
        this.setOptic(optic);
        for (Entry<JMadPlane, Set<Element>> entry : monitors.entrySet()) {
            this.setActiveMonitors(entry.getKey(), entry.getValue());
        }
        return this;
    }

    /**
     * @return the actual request based on the current state of the builder.
     * @throws IllegalStateException in case one of the constraints is not fulfilled
     */
    public synchronized UpdateRequest buildRequest() {
        if (this.elementsUpdated) {
            /* optic needs to be correct */
            if (!this.opticUpdated) {
                try {
                    this.setOptic(this.machineOptic);
                } catch (Exception e) {
                    throw new IllegalStateException("Can not build update request with new elements illegal optic", e);
                }
            }

            /* monitors need to be defined for both planes */
            for (JMadPlane plane : JMadPlane.values()) {
                Set<Element> monitors = this.interpolationMonitors.get(plane);
                try {
                    /* set the active monitors internally */
                    this.setActiveMonitors(plane, monitors);
                    /* flag the structure change for the actual plane */
                    this.monitorsUpdated.put(plane, true);
                } catch (Exception e) {
                    throw new IllegalStateException(
                            "Can not build update request with new elements, illegal monitors defined for plane ["
                                    + plane + "]", e);
                }
            }
        }

        /* check if monitors changed --> optic needs to be available */
        Map<JMadPlane, Boolean> updateMonitorPlanes = new HashMap<JMadPlane, Boolean>();
        for (JMadPlane plane : JMadPlane.values()) {
            if (this.monitorsUpdated.get(plane)) {
                updateMonitorPlanes.put(plane, true);
            }
        }

        if (updateMonitorPlanes.size() > 0) {
            /* we need to have elements and optic defined */
            try {
                this.setElements(machineElements);
                this.setOptic(machineOptic);
            } catch (Exception e) {
                throw new IllegalStateException("Can not build request with changed active monitors.", e);
            }
        }

        /* build the request */
        UpdateRequest request = new UpdateRequestImpl(machineElements, //
                interpolationMonitors, updateMonitorPlanes,//
                machineOptic, opticUpdated, //
                isCircularMachine);

        /* reset the updated flags */
        this.opticUpdated = false;
        this.elementsUpdated = false;
        for (JMadPlane plane : JMadPlane.values()) {
            this.monitorsUpdated.put(plane, false);
        }

        return request;
    }

    private class UpdateRequestImpl implements UpdateRequest {

        private boolean circularMachine;

        private Optic optic;
        private List<Element> elements;

        private Map<JMadPlane, Set<Element>> monitors = new HashMap<JMadPlane, Set<Element>>();

        private Map<JMadPlane, Boolean> structureUpdate;
        private boolean opticUpdate;

        /**
         * The constructor for an update request. All collections passed will be buffered in an internal collection.
         * 
         * @param elements the list of elements defining the machine
         * @param monitors the active monitors to use for interpolation
         * @param optic the machine optic to use for the interpolation
         * @param opticUpdate set to <code>true</code> if optic should be updated
         * @param isCircularMachine set to <code>true</code> if machine is a synchrotron
         * @param structureUpdate a mapping defining which plane must be structural updated, if map is empty, no
         *            structural update required
         */
        public UpdateRequestImpl(List<Element> elements, Map<JMadPlane, Set<Element>> monitors,
                Map<JMadPlane, Boolean> structureUpdate, Optic optic, boolean opticUpdate, boolean isCircularMachine) {
            this.circularMachine = isCircularMachine;

            /* check optic update */
            if (opticUpdate && (optic == null)) {
                throw new IllegalArgumentException("Can not create request for"
                        + " optic update with no optic data provided!!");
            }
            this.opticUpdate = opticUpdate;

            /* ensure that optic and elements are available if structure update requested */
            if (structureUpdate.size() > 0) {
                if (optic == null) {
                    throw new IllegalArgumentException("Can not create request for"
                            + " structural update with no optic data provided!!");
                }

                if (elements == null) {
                    throw new IllegalArgumentException("Can not create request for"
                            + " structural update with no element data provided!!");
                }

                /* we need to update the optic also after creation of the structure */
                this.opticUpdate = true;
            }

            this.elements = new ArrayList<Element>(elements);
            this.optic = optic;

            /* set the flags for the structural update */
            this.structureUpdate = new HashMap<JMadPlane, Boolean>();
            for (JMadPlane plane : JMadPlane.values()) {
                if (structureUpdate.containsKey(plane)) {
                    if (structureUpdate.get(plane) && !monitors.containsKey(plane)) {
                        /* update requested, but no active monitor data provided */
                        throw new IllegalArgumentException("Can not create request for"
                                + " structural update in plane [" + plane + "] with no monitor data provided!!");
                    } else {
                        this.monitors.put(plane, new HashSet<Element>(monitors.get(plane)));
                        this.structureUpdate.put(plane, structureUpdate.get(plane));
                    }
                } else {
                    this.structureUpdate.put(plane, false);
                }
            }
        }

        @Override
        public List<Element> getMachineElements() {
            return this.elements;
        }

        @Override
        public Optic getOptic() {
            return this.optic;
        }

        @Override
        public boolean isCircularMachine() {
            return this.circularMachine;
        }

        @Override
        public boolean updateMachineOptics() {
            return this.opticUpdate;
        }

        @Override
        public Set<Element> getMonitors(JMadPlane plane) {
            if (!updateStructure(plane)) {
                throw new IllegalArgumentException("No monitor data available for plane [" + plane + "]");
            }
            return this.monitors.get(plane);
        }

        @Override
        public boolean updateStructure(JMadPlane plane) {
            return this.structureUpdate.get(plane);
        }
    }
}

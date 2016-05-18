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

package cern.accsoft.steering.jmad.domain.machine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cern.accsoft.steering.jmad.domain.elem.Element;
import cern.accsoft.steering.jmad.domain.elem.JMadElementType;
import cern.accsoft.steering.jmad.domain.machine.filter.NameFilter;
import cern.accsoft.steering.jmad.domain.misalign.MisalignmentConfiguration;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;

/**
 * This class represents a certain range of a MadX sequence. It manages all the element in this range, the misalignments
 * of this elements, the filters to apply (e.g. for BPM reading inversions) and knows about its definition in the
 * {@link JMadModelDefinition}
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class Range {

    /** the listeners */
    private final List<RangeListener> listeners = new ArrayList<RangeListener>();

    /** All Elements in this sequence */
    private final List<Element> elements = new ArrayList<Element>();

    /** The keys for retrieval of elements by name */
    private final Map<String, Integer> keys = new HashMap<String, Integer>();

    /** all misalignments for elements of this Range */
    private final List<MisalignmentConfiguration> misalignments = new ArrayList<MisalignmentConfiguration>();

    /** the definition of this range */
    private RangeDefinition rangeDefinition = null;

    private List<NameFilter> monitorInvertFilters = new ArrayList<NameFilter>();
    private List<NameFilter> correctorInvertFilter = new ArrayList<NameFilter>();
    private String name = null;

    /**
     * The constructor.
     * 
     * @param rangeDefinition the definition for the range.
     */
    public Range(RangeDefinition rangeDefinition) {
        this(rangeDefinition.getName());
        this.rangeDefinition = rangeDefinition;
        this.monitorInvertFilters = new ArrayList<NameFilter>(rangeDefinition.getMonitorInvertFilters());
        this.correctorInvertFilter = new ArrayList<NameFilter>(rangeDefinition.getCorrectorInvertFilters());
    }

    public Range(String name) {
        this.name = name;
    }

    /**
     * @param misalignmentConfiguration the {@link MisalignmentConfiguration} to add
     */
    public void addMisalignment(MisalignmentConfiguration misalignmentConfiguration) {
        this.misalignments.add(misalignmentConfiguration);
        fireAddedMisalignments(Collections.singletonList(misalignmentConfiguration));
    }

    /**
     * @param misalignmentConfigurations
     */
    public void addMisalignments(List<MisalignmentConfiguration> misalignmentConfigurations) {
        misalignmentConfigurations.addAll(misalignmentConfigurations);
        fireAddedMisalignments(misalignmentConfigurations);
    }

    /**
     * @return all misalignments in this range
     */
    public List<MisalignmentConfiguration> getMisalignmentConfigurations() {
        return this.misalignments;
    }

    /**
     * add one element
     * 
     * @param element the element to add.
     */
    public void add(Element element) {
        elements.add(element);
        keys.put(element.getName(), elements.size() - 1);
    }

    /**
     * remove all elements
     */
    public void clear() {
        elements.clear();
        keys.clear();
    }

    /**
     * get the element with the specific name
     * 
     * @param elementName the name of the element to retrieve.
     * @return the element.
     */
    public Element getElement(String elementName) {
        Integer index = keys.get(elementName);

        if (index == null) {
            return null;
        } else {
            return elements.get(index);
        }
    }

    /**
     * get all elements
     * 
     * @return all elements
     */

    public List<Element> getElements() {
        return elements;
    }

    /**
     * get all elements of a specific type.
     * 
     * @param type the type of the elements to retrieve.
     * @return a Collection of the elements.
     * @deprecated Use getElements(Element.class)
     */
    @Deprecated
    public List<Element> getElements(JMadElementType type) {
        ArrayList<Element> selectedElements = new ArrayList<Element>();

        for (Element element : elements) {
            if (type.isTypeOf(element)) {
                selectedElements.add(element);
            }
        }

        return selectedElements;
    }

    /**
     * @param elemtClass
     * @param <C>
     * @return
     * @return
     */
    @SuppressWarnings("unchecked")
    public <C extends Element> List<C> getElements(Class<C> elemtClass) {
        List<C> selectedElements = new ArrayList<C>();

        for (Element element : elements) {
            if (element.getClass().equals(elemtClass)) {
                selectedElements.add((C) element);
            }
        }
        return selectedElements;
    }

    /**
     * @param type the type for which to search the element names
     * @return all element-names of the given type.
     */
    public List<String> getElementNames(JMadElementType type) {
        List<Element> selectedElements = getElements(type);
        ArrayList<String> elementNames = new ArrayList<String>();
        for (Element element : selectedElements) {
            elementNames.add(element.getName());
        }
        return elementNames;
    }

    /**
     * @return the name of the Range
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the filters for all correctors to invert.
     * @see cern.accsoft.steering.jmad.domain.machine.RangeDefinitionImpl#getCorrectorInvertFilters()
     */
    public final List<NameFilter> getCorrectorInvertFilters() {
        return this.correctorInvertFilter;
    }

    /**
     * @return the filters for monitors to invert
     * @see cern.accsoft.steering.jmad.domain.machine.RangeDefinitionImpl#getMonitorInvertFilters()
     */
    public final List<NameFilter> getMonitorInvertFilters() {
        return this.monitorInvertFilters;
    }

    @Override
    public String toString() {
        return getName();
    }

    /**
     * @return the rangeDefinition
     */
    public RangeDefinition getRangeDefinition() {
        return rangeDefinition;
    }

    /*
     * methods concerning listeners
     */

    /**
     * notify the listeners, that a {@link MisalignmentConfiguration} has been added
     * 
     * @param config {@link MisalignmentConfiguration} which was added
     */
    private void fireAddedMisalignments(MisalignmentConfiguration config) {
        for (RangeListener listener : this.listeners) {
            listener.addedMisalignments(config);
        }
    }

    private void fireAddedMisalignments(List<MisalignmentConfiguration> configs) {
        for (RangeListener listener : this.listeners) {
            listener.addedMisalignments(configs);
        }
    }

    /**
     * @param listener the listener to add
     */
    public void addListener(RangeListener listener) {
        this.listeners.add(listener);
    }

    /**
     * @param listener the listener to remove
     */
    public void removeListener(RangeListener listener) {
        this.listeners.remove(listener);
    }

}

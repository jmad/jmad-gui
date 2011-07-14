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
package cern.accsoft.steering.jmad.domain.optics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cern.accsoft.steering.jmad.JMadConstants;
import cern.accsoft.steering.jmad.domain.elem.Element;
import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;
import cern.accsoft.steering.jmad.domain.var.enums.JMadTwissVariable;
import cern.accsoft.steering.jmad.domain.var.enums.MadxTwissVariable;
import cern.accsoft.steering.jmad.util.ListUtil;
import cern.accsoft.steering.jmad.util.ListUtil.Mapper;

/**
 * Represents a collection of values which can be referred to as the actual state of the optics of the accelerator. It
 * e.g. contains the values of the beta-functions, dispersions and reference orbits. The values can be retrieved in two
 * different manners: As objects representing the values at one element of the accelerator and as lists of values
 * representing all the values along the accelerator.
 * 
 * @author kaifox
 */
public class OpticImpl implements Optic {

    /** the element indizes by name */
    private final Map<String, Integer> indizes = new HashMap<String, Integer>();

    /** all the optics points */
    private final List<OpticPoint> points = new ArrayList<OpticPoint>();

    /** all the optics values as lists, just as from the tfs-result */
    private final Map<MadxTwissVariable, List<Double>> doubleLists = new HashMap<MadxTwissVariable, List<Double>>();

    /** the names of the optics-points (Elements) */
    private List<String> names = new ArrayList<String>();

    /**
     * adds values for all elements, representing the given variable
     * 
     * @param var the variable that defines the meaning of the values
     * @param values the values for all elements along the accelerator
     */
    public void add(MadxTwissVariable var, List<Double> values) {
        this.doubleLists.put(var, values);
    }

    /**
     * adds one optics point, which represents all values at one element
     * 
     * @param point the optics point to add
     */
    public void add(OpticPoint point) {
        points.add(point);
        indizes.put(unifyKey(point.getName()), points.size() - 1);
    }

    @Override
    public List<OpticPoint> getAllPoints() {
        return points;
    }

    @Override
    public OpticPoint getPointByName(String name) {
        Integer index = indizes.get(unifyKey(name));
        if (index != null) {
            return points.get(index);
        }
        return null;
    }

    @Override
    public OpticPoint getPoint(Element element) {
        return getPointByName(element.getName());
    }

    /**
     * determines the index in the lists for the element of the given name
     * 
     * @param name the name of the element for which to get the index
     * @return the index
     */
    private Integer getIndex(String name) {
        return indizes.get(unifyKey(name));
    }

    /**
     * creates a standard key which is used in the maps
     * 
     * @param key the key to unify
     * @return a key in a unified form
     */
    private static final String unifyKey(String key) {
        return key.toUpperCase(JMadConstants.DEFAULT_LOCALE);
    }

    /**
     * converts a list of elements into a list of indizes
     * 
     * @param elements the elements whose indizes shall be determined
     * @return the names of the elements in a list
     */
    private final List<Integer> getElementIndizes(List<Element> elements) {
        return ListUtil.map(elements, new Mapper<Element, Integer>() {

            @Override
            public Integer map(Element element) {
                return getIndex(element.getName());
            }
        });
    }

    /**
     * determines the indizes for the elements of the given names
     * 
     * @param names the names of the elements for which to find the indizes
     * @return a list of indizes
     */
    private List<Integer> getIndizes(List<String> names) {
        return ListUtil.map(names, new Mapper<String, Integer>() {

            @Override
            public Integer map(String name) {
                return getIndex(name);
            }
        });
    }

    @Override
    public List<Double> getAllValues(MadxTwissVariable variable) {
        return this.doubleLists.get(variable);
    }

    @Override
    public List<Double> getAllValues(JMadTwissVariable variable, JMadPlane plane) {
        return getAllValues(variable.getMadxTwissVariable(plane));
    }

    /**
     * collects the optics points for the given indizes
     * 
     * @param pointIndizes the list-indizes of the points to collect
     * @return a list containing all the wanted optics-pointss
     */
    private List<OpticPoint> getPointsByIndizes(List<Integer> pointIndizes) {
        List<OpticPoint> foundPoints = new ArrayList<OpticPoint>();
        for (Integer index : pointIndizes) {
            foundPoints.add(this.points.get(index));
        }
        return foundPoints;
    }

    @Override
    public List<OpticPoint> getPoints(List<Element> elements) {
        return getPointsByIndizes(getElementIndizes(elements));
    }

    @Override
    public List<OpticPoint> getPointsByNames(List<String> names) {
        return getPointsByIndizes(getIndizes(names));
    }

    /**
     * collects all the values for the given variable and the given indizes in a list.
     * 
     * @param variable the variable for which to retrieve the values
     * @param pointIndizes the indizes of the optics points for which to find the values
     * @return a list of values
     */
    private List<Double> getValuesByIndizes(MadxTwissVariable variable, List<Integer> pointIndizes) {
        List<Double> allValues = getAllValues(variable);
        List<Double> selectedValues = new ArrayList<Double>();
        for (Integer index : pointIndizes) {
            selectedValues.add(allValues.get(index));
        }
        return selectedValues;
    }

    @Override
    public List<Double> getValues(MadxTwissVariable variable, List<Element> elements) {
        return getValuesByIndizes(variable, getElementIndizes(elements));
    }

    @Override
    public List<Double> getValues(JMadTwissVariable variable, JMadPlane plane, List<Element> elements) {
        return getValuesByIndizes(variable.getMadxTwissVariable(plane), getElementIndizes(elements));
    }

    @Override
    public List<Double> getValuesByNames(MadxTwissVariable variable, List<String> names) {
        return getValuesByIndizes(variable, getIndizes(names));
    }

    @Override
    public List<Double> getValuesByNames(JMadTwissVariable variable, JMadPlane plane, List<String> names) {
        return getValuesByIndizes(variable.getMadxTwissVariable(plane), getIndizes(names));
    }

    @Override
    public List<String> getNames() {
        return this.names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }
}

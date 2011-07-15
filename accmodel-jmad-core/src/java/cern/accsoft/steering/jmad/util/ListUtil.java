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
package cern.accsoft.steering.jmad.util;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides some utility-methods for generic lists.
 * 
 * @author kfuchsbe
 */
public final class ListUtil {

    /**
     * private constructor to avoid instantiation
     */
    private ListUtil() {
        /* only static methods */
    }

    /**
     * converts a list of one tye into a list of another type using the provided mapper
     * 
     * @param <IN> the type of the input-list items
     * @param <OUT> the type of the output-list items
     * @param inlist the list of items to convert
     * @param mapper the mapper which converts the types
     * @return the new list.
     */
    public static <IN, OUT> List<OUT> map(List<IN> inlist, Mapper<IN, OUT> mapper) {
        List<OUT> outlist = new ArrayList<OUT>(inlist.size());
        for (IN item : inlist) {
            outlist.add(mapper.map(item));
        }
        return outlist;
    }

    /**
     * this interface just defines one method to convert one object into another.
     * 
     * @param <IN> The type of object used as source for the conversion
     * @param <OUT> The type of the resulting object
     */
    public interface Mapper<IN, OUT> {
        /**
         * converts the input object to the output
         * 
         * @param inobject the object to convert
         * @return the converted object
         */
        public abstract OUT map(IN inobject);
    }

    /**
     * this method converts any List to a list of strings.
     * 
     * @param objects the objects that shall be converted to strings
     * @return the list of strings
     */
    public static List<String> toString(List<? extends Object> objects) {
        List<String> strings = new ArrayList<String>();
        for (Object object : objects) {
            strings.add(object.toString());
        }
        return strings;
    }

    /**
     * creates an array list with the one given element.
     * 
     * @param <T> the type of objects for the list
     * @param element the single element that shall be contained in the new list
     * @return a list containing the given element
     */
    public static <T> List<T> createOneElementList(T element) {
        List<T> list = new ArrayList<T>();
        list.add(element);
        return list;
    }

    /**
     * creates a new list of the given size, containing the same element in every item
     * 
     * @param <T> the type of the list items
     * @param size the size of the new list
     * @param defaultValue the value that shall be contained in all items
     * @return the new list, containing the defaultValue in all items
     */
    public static <T> List<T> createDefaultValueList(int size, T defaultValue) {
        List<T> list = new ArrayList<T>(size);
        for (int i = 0; i < size; i++) {
            list.add(defaultValue);
        }
        return list;
    }

    /**
     * returns a list with the values difference values doublesA-doublesB
     * 
     * @param doublesA the values from which to subtract
     * @param doublesB the values which shall be subtracted
     * @return a list of differences
     */
    public static List<Double> diff(List<Double> doublesA, List<Double> doublesB) {
        if (doublesA.size() != doublesB.size()) {
            throw new IllegalArgumentException("The two lists must be of same size!");
        }
        List<Double> diff = new ArrayList<Double>(doublesA.size());
        for (int i = 0; i < doublesA.size(); i++) {
            diff.add(doublesA.get(i) - doublesB.get(i));
        }
        return diff;
    }

    /**
     * performs an element wise devision of the lists (doublesA / doublesB).
     * 
     * @param doublesA the list of values that act as dividends
     * @param doublesB the list of values that act as divisors
     * @return element wise doublesA / doublesB
     */
    public static List<Double> divide(List<Double> doublesA, List<Double> doublesB) {
        if (doublesA.size() != doublesB.size()) {
            throw new IllegalArgumentException("The two lists must be of same size!");
        }
        List<Double> divide = new ArrayList<Double>(doublesA.size());
        for (int i = 0; i < doublesA.size(); i++) {
            divide.add(doublesA.get(i) / doublesB.get(i));
        }
        return divide;
    }

}

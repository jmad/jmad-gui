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
 * $Id: BeanVariationParameter.java,v 1.2 2009-01-20 19:43:09 kfuchsbe Exp $
 * 
 * $Date: 2009-01-20 19:43:09 $ $Revision: 1.2 $ $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.domain.knob.bean;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.domain.knob.AbstractKnob;
import cern.accsoft.steering.jmad.util.bean.NamedBean;

/**
 * this class provides a knob, which just needs a bean and a property of it.
 * <p>
 * <b>Be careful:</b> Remove these Knobs from all lists, when the bean shall be destroyed!
 * <p>
 * This class is designed to be sub classed by more dedicated knobs, that have to provide the actual bean by the
 * {@link #getBean()} method.
 * 
 * @author kfuchsbe
 */
public abstract class BeanPropertyKnob extends AbstractKnob {

    /** the logger for the class */
    private static final Logger LOGGER = Logger.getLogger(BeanPropertyKnob.class);

    /** the separator between bean-name and property-name, which is used when creating keys */
    public static final String NAME_PROPETY_SEPARATOR = ".";

    /** the name of the property we vary */
    private final String propertyName;

    /**
     * the constructor, which needs a property-name.
     * 
     * @param propertyName the name of the property, which we will vary
     */
    public BeanPropertyKnob(String propertyName) {
        this.propertyName = propertyName;
    }

    /*
     * some simple helper methods
     */

    /**
     * Gets a double value from a bean-property with the given name.
     * 
     * @param bean the bean from which to get the value
     * @param propertyName the name of the property
     * @return the actual value of the varied property of the bean
     */
    private static final double getBeanValue(Object bean, String propertyName) {
        try {
            Double value = (Double) PropertyUtils.getSimpleProperty(bean, propertyName);
            if (value != null) {
                return value.doubleValue();
            }
        } catch (Exception e) {
            LOGGER.error("Cannot read property '" + propertyName + "' of bean '" + bean + "'.", e);
        }
        return 0.0;
    }

    /**
     * @return the actual value of the property
     */
    private double getBeanValue() {
        return getBeanValue(getBean(), this.propertyName);
    }

    /**
     * create a key for this bean- propertyName combination
     * 
     * @param bean the on which to use the property
     * @param propertyName the name of the property
     * @return a combined key
     */
    public static final String createKey(Object bean, String propertyName) {
        String name;
        if (bean instanceof NamedBean) {
            name = ((NamedBean) bean).getName();
        } else {
            name = bean.toString();
        }
        return name + NAME_PROPETY_SEPARATOR + propertyName;
    }

    /**
     * set the property to a given value
     * 
     * @param value the value to set the property to
     */
    private void setBeanValue(double value) {
        try {
            PropertyUtils.setSimpleProperty(getBean(), this.propertyName, value);
        } catch (Exception e) {
            LOGGER.error("Cannot set property '" + this.propertyName + "' of bean '" + getBean() + "'.", e);
        }
    }

    /**
     * extracts the property-name from the key
     * 
     * @param key the key from which to extract the property name
     * @return the name of the property
     */
    public static final String getPropertyNameFromKey(String key) {
        int index = key.lastIndexOf(NAME_PROPETY_SEPARATOR);
        if (index >= 0) {
            return key.substring(index + NAME_PROPETY_SEPARATOR.length());
        }
        return null;
    }

    @Override
    protected final void doSetTotalValue(double value) {
        this.setBeanValue(value);
    }

    @Override
    public String getKey() {
        return createKey(getBean(), this.propertyName);
    }

    @Override
    public String getName() {
        return this.getKey();
    }

    @Override
    public double getTotalValue() {
        return getBeanValue();
    }

    /**
     * must be implemented by subclass in order to provide the bean instance to/from which to get/set values.
     * 
     * @return the bean
     */
    protected abstract Object getBean();

}

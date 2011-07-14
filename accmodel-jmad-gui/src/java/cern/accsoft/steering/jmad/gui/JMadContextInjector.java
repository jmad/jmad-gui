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
package cern.accsoft.steering.jmad.gui;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;

/**
 * This is some special-trick bean to get some preconfigured beans into the
 * application-context.
 * 
 * @author kfuchsbe
 * 
 */
public class JMadContextInjector implements BeanNameAware, FactoryBean {

	/** The beans to be set to the application context have to be put here */
	private static Map<String, Object> beansByName = new HashMap<String, Object>();

	/**
	 * The name which is setted by spring and then used to retrieve the actuall
	 * object
	 */
	private String beanName;

	/**
	 * adds a bean, which are then looked up by the application-context.
	 * 
	 * @param beanName
	 *            the name of the bean used then in the app-ctx
	 * @param bean
	 *            the bean to add
	 */
	public final static void putBean(String beanName, Object bean) {
		beansByName.put(beanName, bean);
	}

	@Override
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	@Override
	public Object getObject() {
		return beansByName.get(beanName);
	}

	@Override
	public Class<?> getObjectType() {
		return beansByName.get(beanName).getClass();
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}

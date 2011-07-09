/**
 * 
 */
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

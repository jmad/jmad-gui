package cern.accsoft.steering.jmad.modeldefs.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;

/**
 * This is some special-trick bean to get some pre-configured beans into the application-context. Inspired by <a
 * href="http://stackoverflow.com/questions/496711/adding-a-pre-constructed-bean-to-a-spring-application-context"
 * >link</a>
 * 
 * @author kfuchsbe
 */
public class ContextBeanInjector implements BeanNameAware, FactoryBean {

    /** The beans to be set to the application context have to be put here */
    private static Map<String, Object> beansByName = new HashMap<String, Object>();
    private static Map<String, Class<?>> beanClassesByName = new HashMap<String, Class<?>>();

    /**
     * The name which is set by spring and then used to retrieve the actual object
     */
    private String beanName;

    /**
     * adds a bean to be available in the context injector.
     * 
     * @param beanName the name of the bean as used in the application context
     * @param bean the bean to add
     * @param clazz the class of the bean added
     */
    public final static void putBean(String beanName, Object bean, Class<?> clazz) {
        beansByName.put(beanName, bean);
        beanClassesByName.put(beanName, clazz);
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
        return beanClassesByName.get(beanName);
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}

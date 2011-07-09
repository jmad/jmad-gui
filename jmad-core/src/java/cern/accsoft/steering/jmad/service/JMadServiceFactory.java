/**
 * 
 */
package cern.accsoft.steering.jmad.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This is a static factory for a jmad service.
 * 
 * @author kfuchsbe
 */
public final class JMadServiceFactory {

    private JMadServiceFactory() {
        /* only static methods */
    }

    /**
     * creates a new jmad-service.
     * 
     * @return the new service
     */
    public static JMadService createJMadService() {
        /* creating the application - context. */
        ApplicationContext appCtx = new ClassPathXmlApplicationContext(new String[] { "app-ctx-jmad-service.xml" });
        return (JMadService) appCtx.getBean("jmadService");
    }
}

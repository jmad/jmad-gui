/*
 * $Id $
 * 
 * $Date$ $Revision$ $Author$
 * 
 * Copyright CERN ${year}, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.modeldefs;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cern.accsoft.steering.jmad.model.JMadModelStartupConfiguration;
import cern.accsoft.steering.jmad.modeldefs.impl.ContextBeanInjector;
import cern.accsoft.steering.jmad.modeldefs.lhc.LhcModelManager;

public class ModelManagerFactory {

    private static ApplicationContext APPLICATION_CONTEXT;
    private static final String START_UP_CONFIG_BEAN_NAME = "startupConfiguration";

    static {
        APPLICATION_CONTEXT = new ClassPathXmlApplicationContext(new String[] { "jmad-model-lsa-ctx.xml" });
    }

    private ModelManagerFactory() {
    }

    /**
     * creates a new {@link LhcModelManager}
     * 
     * @return the new {@link LhcModelManager}
     */
    public static LhcModelManager createLhcModelManager() {
        return (LhcModelManager) APPLICATION_CONTEXT.getBean("lhcModelManager");
    }

    /**
     * Define the behavior of the start-up of the underlying jmad models
     * 
     * @param configuration the {@link JMadModelStartupConfiguration} to use
     */
    public static void setModelStartUpConfiguration(JMadModelStartupConfiguration configuration) {
        ContextBeanInjector.putBean(START_UP_CONFIG_BEAN_NAME, configuration, JMadModelStartupConfiguration.class);
    }

    /**
     * creates a new {@link LhcModelManager}
     * 
     * @return the new {@link LhcModelManager}
     */
    public static LhcModelManager createLhcModelManagerWithStartUpConfig(JMadModelStartupConfiguration startConfig) {
        ModelManagerFactory.setModelStartUpConfiguration(startConfig);
        return (LhcModelManager) APPLICATION_CONTEXT.getBean("configuredLhcModelManager");
    }
}

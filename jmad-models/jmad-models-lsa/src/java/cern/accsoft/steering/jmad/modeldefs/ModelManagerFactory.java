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
     * creates a new {@link LhcModelManager}
     * 
     * @return the new {@link LhcModelManager}
     */
    public static LhcModelManager createLhcModelManagerWithStartUpConfig(JMadModelStartupConfiguration startConfig) {
        ContextBeanInjector.putBean(START_UP_CONFIG_BEAN_NAME, startConfig, startConfig.getClass());
        return (LhcModelManager) APPLICATION_CONTEXT.getBean("configuredLhcModelManager");
    }
}

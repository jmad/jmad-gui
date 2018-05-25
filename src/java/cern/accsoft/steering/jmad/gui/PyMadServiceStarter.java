package cern.accsoft.steering.jmad.gui;

import java.lang.reflect.Method;

import cern.accsoft.steering.jmad.service.JMadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides methods to initialize a PyMadService, in case the PyMadService is in the classpath. If this is not the case,
 * then it will simply log an info message, and continue.
 *
 * @author kfuchsbe
 */
public class PyMadServiceStarter {
    private final static Logger LOGGER = LoggerFactory.getLogger(PyMadServiceStarter.class);

    private final static String PYMAD_SERVICE_CLASS_NAME = "cern.accmodel.jmad.pymadservice.PyMadService";

    private JMadService jmadService;

    /**
     * Initializes the pymad service, if the PymadServicClass can be found in the classpath.
     */
    public void init() {
        Class<?> pymadServiceClass = null;
        try {
            pymadServiceClass = this.getClass().getClassLoader().loadClass(PYMAD_SERVICE_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            LOGGER.info("The class '" + PYMAD_SERVICE_CLASS_NAME
                    + "' could not be found in the classpath. Therefore the PyMadService will not be started.", e);
            return;
        }

        /*
         * pymadService.setJmadService(jmadService); pymadService.init();
         */

        try {
            Object pymadService = pymadServiceClass.newInstance();
            Method setter = pymadServiceClass.getMethod("setJmadService", JMadService.class);
            setter.invoke(pymadService, this.jmadService);
            Method initMethod = pymadServiceClass.getMethod("init");
            initMethod.invoke(pymadService);
        } catch (Exception e) {
            LOGGER.warn("PyMadService could not be instantiated.", e);
        }
    }

    public void setJmadService(JMadService jmadService) {
        this.jmadService = jmadService;
    }

}

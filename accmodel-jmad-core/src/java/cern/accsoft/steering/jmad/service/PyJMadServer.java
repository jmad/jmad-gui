package cern.accsoft.steering.jmad.service;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * 
 */

/**
 * creates a JMadService and publish it via py4j. This way it will be possible by any python program to use it.
 * 
 * @author kaifox
 */
public class PyJMadServer {

    /** The logger for the class */
    private static final Logger LOGGER = Logger.getLogger(PyJMadServer.class);

    /**
     * @param args
     */
    public void main(String[] args) {

        /* configure the log4j - system */
        BasicConfigurator.configure();

        @SuppressWarnings("unused")
        JMadService jMadService = JMadServiceFactory.createJMadService();

        LOGGER.info("PyJMadServer started.");
    }

}

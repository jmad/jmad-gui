package cern.accmodel.jmad.pymadservice;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

import py4j.GatewayServer;
import cern.accsoft.steering.jmad.service.JMadService;
import cern.accsoft.steering.jmad.service.JMadServiceFactory;

/**
 * Provides a service which allows python to connect to the JMadService. It can be instantiated from outside and also
 * contains a main method which allows to run a standalone-service.
 * 
 * @author kfuchsbe
 */
public class PyMadService {

    private final static Logger LOGGER = Logger.getLogger(PyMadService.class);

    private GatewayServer gatewayServer = null;

    private JMadService jmadService;

    /** The property which is read to determine which file to write as soon as the service started up */
    private static final String READY_FILE_PROPERTY_NAME = "pymad.service.ready.file";

    /** The default name of the file to be written when the service started up */
    private static final String DEFAULT_READY_FILE = "pymad-service-ready.out";

    /**
     * Main method which creates a standalone gateway server for pymad
     * 
     * @param args
     */
    public static void main(String[] args) {
        JMadService jmadService = JMadServiceFactory.createJMadService();
        PyMadService pymadService = new PyMadService();
        pymadService.setJmadService(jmadService);
        pymadService.init();
    }

    public void init() {
        if (this.gatewayServer == null) {
            this.gatewayServer = new GatewayServer(this);
            this.gatewayServer.start();
            File readyFile = getReadyFile();
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(readyFile));
                writer.write("ready");
                writer.flush();
                writer.close();
            } catch (IOException e) {
                LOGGER.error("Could not write ready-file '" + readyFile.getAbsolutePath() + "'", e);
            }
            LOGGER.debug("Java PyMadService started. Wrote ready-file '"+readyFile.getAbsolutePath()+"'");
        }
    }

    /**
     * this can be used by pymad to close the service
     */
    public void end() {
        System.exit(0);
    }

    private File getReadyFile() {
        String fileName = System.getProperty(READY_FILE_PROPERTY_NAME, DEFAULT_READY_FILE);
        return new File(fileName);
    }

    public GatewayServer getGatewayServer() {
        return gatewayServer;
    }

    public void setJmadService(JMadService jmadService) {
        this.jmadService = jmadService;
    }

    public JMadService getJmadService() {
        return jmadService;
    }

}

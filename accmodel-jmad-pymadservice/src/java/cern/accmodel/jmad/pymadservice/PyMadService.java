package cern.accmodel.jmad.pymadservice;

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

    private GatewayServer gatewayServer;

    public PyMadService(JMadService jmadService) {
        this.gatewayServer = new GatewayServer(jmadService);
    }

    /**
     * Main method which creates a standalone gateway server for pymad
     * 
     * @param args
     */
    public static void main(String[] args) {
        JMadService jmadService = JMadServiceFactory.createJMadService();
        new PyMadService(jmadService);
    }

    public GatewayServer getGatewayServer() {
        return gatewayServer;
    }

}

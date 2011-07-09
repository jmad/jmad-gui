package cern.accsoft.steering.jmad.modeldefs.test;

import org.apache.log4j.BasicConfigurator;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class LoggedTestCase {

    @BeforeClass
    public static void initLog4J() {
        BasicConfigurator.configure();
    }

    @AfterClass
    public static void resetLog4J() {
        BasicConfigurator.resetConfiguration();
    }
}

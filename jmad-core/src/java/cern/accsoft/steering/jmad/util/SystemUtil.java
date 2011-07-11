/*
 * $Id: SystemUtil.java,v 1.1 2008-09-09 23:41:42 kfuchsbe Exp $
 * 
 * $Date: 2008-09-09 23:41:42 $ $Revision: 1.1 $ $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * This class contains various methods to access system properties.
 * 
 * @author kfuchsbe
 */
public final class SystemUtil {

    private SystemUtil() {
        /* only static methods */
    }

    /*
     * the names of the properties
     */
    private static final String PROPERTY_NAME_USERNAME = "user.name";
    private static final String PROPERTY_NAME_TEMP_DIR = "java.io.tmpdir";

    /**
     * @return the name of the currently logged in user.
     */
    public static String getUserName() {
        String username;
        username = System.getProperty(PROPERTY_NAME_USERNAME);
        return username;
    }

    /**
     * @return the name of the local host
     */
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "unknown-host";
        }
    }
    
    /**
     * @return the path of the systems temporary directory
     */
    public static String getSystemTempDirectoryPath() {
        String systemTempPath;
        systemTempPath = System.getProperty(PROPERTY_NAME_TEMP_DIR);
        return systemTempPath;
    }
}
/*
 * $Id: OsUtil.java,v 1.3 2009-02-25 18:48:26 kfuchsbe Exp $
 * 
 * $Date: 2009-02-25 18:48:26 $ $Revision: 1.3 $ $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.util;

/**
 * provides some methods to determine the operating-system
 * 
 * @author kfuchsbe
 */
public final class OsUtil {
    /* the name of the system-property */
    private static final String OSNAME_PROPERTY = "os.name";

    /*
     * the prefixes for different operating systems. Use lower case strings here!
     */
    private static final String PREFIX_WINDOWS = "windows";
    private static final String PREFIX_LINUX = "linux";
    private static final String PREFIX_OSX = "mac os x";

    /* the name of the OS in lower case */
    private static final String OS_NAME = System.getProperty(OSNAME_PROPERTY).toLowerCase();

    private OsUtil() {
        /* only static methods */
    }

    /**
     * @return true, if VM is running on a windows - platform, false otherwise
     */
    public static boolean isWindows() {
        return OS_NAME.startsWith(PREFIX_WINDOWS);
    }

    /**
     * @return true, if the VM is running on a mac-osx platform, false otherwise.
     */
    public static boolean isOsX() {
        return OS_NAME.startsWith(PREFIX_OSX);
    }

    /**
     * @return true if VM is running on aLinux platform, false otherwise
     */
    public static boolean isLinux() {
        return OS_NAME.startsWith(PREFIX_LINUX);
    }

    /**
     * @return the name of the OS
     */
    public static String getOsName() {
        return OS_NAME;
    }
}

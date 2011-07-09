/*
 * $Id: ResourceUtil.java,v 1.5 2009-02-25 18:48:27 kfuchsbe Exp $
 * 
 * $Date: 2009-02-25 18:48:27 $ $Revision: 1.5 $ $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.util;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * This class provides methods to handle resource files
 * 
 * @author kfuchsbe
 */
public final class ResourceUtil {

    /** the logger for the class */
    private static final Logger LOGGER = Logger.getLogger(ResourceUtil.class);

    /** the file extension to detect a jar */
    private static final String JAR_EXT = ".jar";

    private ResourceUtil() {
        /* only static methods */
    }

    /**
     * searches all the resources in the classpath to get a list of all the resources in the given package.
     * 
     * @param packageName the package name in which to search for the resources.
     * @return the names of the resources (without leading package names)
     */
    public static Collection<String> getResourceNames(String packageName) {
        String patternString = ".*" + packageName.replaceAll("\\/", "\\\\/").replaceAll("\\.", "\\\\/") + "\\/[^\\/]+";
        LOGGER.debug("Searching for pattern:" + patternString);
        Pattern pattern = Pattern.compile(patternString);
        Collection<String> resources = getResources(pattern);
        List<String> names = new ArrayList<String>();
        for (String resource : resources) {
            File file = new File(resource);
            names.add(file.getName());
        }
        return names;
    }

    /**
     * just adds a prefix to the path
     * 
     * @param filepath the path to which to add the prefix
     * @param offset the offset to add as prefix
     * @return the whole path
     */
    public static String prependPathOffset(String filepath, String offset) {
        if ((offset != null) && (offset.length() > 0)) {
            /*
             * IMPORTANT: Do NOT use File.separator here, since this is also used for resource-paths!
             */
            return offset + "/" + filepath;
        } else {
            return filepath;
        }
    }

    /*
     * The following code was found on the net at http://forums.devx.com/showthread.php?t=153784
     */

    /**
     * for all elements of java.class.path get a Collection of resources Pattern pattern = Pattern.compile(".*"); gets
     * all resources
     * 
     * @param pattern the pattern to match
     * @return the resources in the order they are found
     */
    public static Collection<String> getResources(Pattern pattern) {
        ArrayList<String> retval = new ArrayList<String>();

        /*
         * first we check the classpath, only directories and ignore the jars
         */
        retval.addAll(getResourcesFromClassPath(pattern, true));

        /*
         * Then we go through all loaded jars to search the files. This is necessary especially when running from jnlp.
         */
        retval.addAll(getResourcesFromLoadedJars(ResourceUtil.class, pattern));
        return retval;
    }

    /**
     * @param pattern a regular expression pattern to search
     * @param ignoreJars if <code>true</code> then all jars in the classpath are ignored, if <code>false</code> they are
     *            included.
     * @return all names of the resources included in the class-path (jars and directories)
     */
    private static Collection<String> getResourcesFromClassPath(Pattern pattern, boolean ignoreJars) {
        List<String> retval = new ArrayList<String>();
        String classPath = System.getProperty("java.class.path", ".");
        String[] classPathElements = classPath.split(File.pathSeparator);
        for (String element : classPathElements) {
            LOGGER.debug("Processing classpath entry '" + element + "'");
            retval.addAll(getResources(element, pattern, ignoreJars));
        }
        return retval;
    }

    private static Collection<String> getResources(String element, Pattern pattern, boolean ignoreJars) {
        ArrayList<String> retval = new ArrayList<String>();
        File file = new File(element);
        if (file.isDirectory()) {
            retval.addAll(getResourcesFromDirectory(file, pattern));
        } else {
            if (!ignoreJars) {
                retval.addAll(getResourcesFromJarFile(file, pattern));
            }
        }
        return retval;
    }

    private static Collection<String> getResourcesFromJarFile(File file, Pattern pattern) {
        return ZipUtil.getFileNames(file, pattern);
    }

    private static Collection<String> getResourcesFromDirectory(File directory, Pattern pattern) {
        ArrayList<String> retval = new ArrayList<String>();
        File[] fileList = directory.listFiles();
        for (File file : fileList) {
            if (file.isDirectory()) {
                retval.addAll(getResourcesFromDirectory(file, pattern));
            } else {
                try {
                    String fileName = file.getCanonicalPath().replaceAll("\\\\", "/");
                    boolean accept = pattern.matcher(fileName).matches();
                    if (accept) {
                        retval.add(fileName);
                    }
                } catch (IOException e) {
                    throw new Error(e);
                }
            }
        }
        return retval;
    }

    private static List<String> getResourcesFromLoadedJars(Class<?> clazz, Pattern pattern) {
        List<String> retval = new ArrayList<String>();
        ClassLoader classLoader = clazz.getClassLoader();
        while (classLoader != null) {
            if (classLoader instanceof URLClassLoader) {
                URL[] urls = ((URLClassLoader) classLoader).getURLs();
                for (int i = 0; i < urls.length; i++) {
                    if (urls[i] != null && urls[i].toString().endsWith(JAR_EXT)) {
                        JarFile jarFile = null;
                        try {
                            /*
                             * Create a URL that refers to a jar file in the file system
                             */
                            URL url = new URL("jar:" + urls[i].toExternalForm() + "!/");

                            /* Get the jar file */
                            JarURLConnection conn = (JarURLConnection) url.openConnection();
                            jarFile = conn.getJarFile();

                            Collection<String> jarResources = ZipUtil.getFileNames(jarFile, pattern);
                            retval.addAll(jarResources);
                        } catch (MalformedURLException e) {
                            LOGGER.error("Could not open jar file.", e);
                        } catch (IOException e) {
                            LOGGER.error("Could not open jar file.", e);
                        } finally {
                            if (jarFile != null) {
                                try {
                                    jarFile.close();
                                } catch (IOException e) {
                                    LOGGER.error("Erro while closing jar file.", e);
                                }
                            }
                        }
                    }
                }
            }
            classLoader = classLoader.getParent();
        }
        return retval;
    }
}

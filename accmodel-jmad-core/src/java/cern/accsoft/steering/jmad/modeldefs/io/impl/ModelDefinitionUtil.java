/**
 * 
 */
package cern.accsoft.steering.jmad.modeldefs.io.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cern.accsoft.steering.jmad.JMadConstants;
import cern.accsoft.steering.jmad.domain.file.ModelFile;
import cern.accsoft.steering.jmad.domain.file.ModelFileDependant;
import cern.accsoft.steering.jmad.modeldefs.ClassPathModelDefinitionFinder;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;

/**
 * This class contains some utility methods for model definitions
 * 
 * @author kfuchsbe
 */
public final class ModelDefinitionUtil {

    /**
     * this is the class relative to which the model-definitions and files will be searched.
     */
    public static final Class<?> BASE_CLASS = ClassPathModelDefinitionFinder.class;

    /**
     * the package name relative to the base class where to search for model-definitions and files
     */
    public static final String PACKAGE_OFFSET = "defs";

    /** the default file extension for xml files */
    public static final String XML_FILE_EXTENSION = ".jmd.xml";

    /** The extension used for jmad zip files */
    public static final String ZIP_FILE_EXTENSION = ".jmd.zip";

    /**
     * the private constructor to prevent instantiation
     */
    private ModelDefinitionUtil() {
        /* Only static methods */
    }

    /**
     * creates a file name for the given model definition
     * 
     * @param modelDefinition the model definition for which to create the filename
     * @return the filename
     */
    public static String getProposedXmlFileName(JMadModelDefinition modelDefinition) {
        return modelDefinition.getName().replaceAll("\\(", "").replaceAll("\\)", "").toLowerCase().replaceAll(" ", "-")
                + XML_FILE_EXTENSION;
    }

    /**
     * determines if the given name is a model definition xml file
     * 
     * @param fileName the file name to check
     * @return true if it is an xml file name, false if not
     */
    public static boolean isXmlFileName(String fileName) {
        return fileName.toLowerCase().endsWith(XML_FILE_EXTENSION);
    }

    /**
     * adds the correct file extension for jmad zip files if necessary
     * 
     * @param file the file which shall be a correct jmad zip file
     * @return the corrected file
     */
    public static File ensureZipFileExtension(File file) {
        if (isZipFileName(file.getName())) {
            return file;
        } else {
            return new File(file.getAbsolutePath() + ZIP_FILE_EXTENSION);
        }
    }

    /**
     * determines if the given name is a model definition zip file
     * 
     * @param fileName the file name to check
     * @return true if it is a jmad zip file name, false if not
     */
    public static boolean isZipFileName(String fileName) {
        return fileName.toLowerCase(JMadConstants.DEFAULT_LOCALE).endsWith(ZIP_FILE_EXTENSION);
    }

    /**
     * collects all the model files which are required by a model definition and its parts
     * 
     * @param modelDefinition the {@link JMadModelDefinition} for which to collect the model files
     * @return all the required model files
     */
    public static final Collection<ModelFile> getRequiredModelFiles(JMadModelDefinition modelDefinition) {

        Set<ModelFile> requiredFiles = new HashSet<ModelFile>();
        for (ModelFileDependant modelFileDependant : getModelFileDependants(modelDefinition)) {
            requiredFiles.addAll(modelFileDependant.getRequiredFiles());
        }
        return requiredFiles;
    }

    /**
     * collects all the parts of a model definition that potentially depend on model files
     * 
     * @param modelDefinition the model definition for which to collect the dependent classes
     * @return all the model-file dependent classes
     */
    private static final Collection<ModelFileDependant> getModelFileDependants(JMadModelDefinition modelDefinition) {
        List<ModelFileDependant> modelFileDependants = new ArrayList<ModelFileDependant>();

        /*
         * XXX: not very nice. Here all parts of a model definition which potentially depend on model files are added
         * manually to the collection.
         */
        modelFileDependants.add(modelDefinition);
        modelFileDependants.addAll(modelDefinition.getRangeDefinitions());
        modelFileDependants.addAll(modelDefinition.getOpticsDefinitions());
        return modelFileDependants;
    }
}

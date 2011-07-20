/*
 * $Id $
 * 
 * $Date$ $Revision$ $Author$
 * 
 * Copyright CERN ${year}, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.modeldefs.create;

import java.util.List;

import cern.accsoft.steering.jmad.domain.file.ModelFile;

public interface OpticDefinitionSet {

    /**
     * @return the ordered list of optic files that are called as FIRST files for all optic definitions in this set
     */
    List<ModelFile> getInitialCommonOpticFiles();

    /**
     * @return the ordered list of optic files that are called as FINAL files for all optic definitions in this set
     */
    List<ModelFile> getFinalCommonOpticFiles();

    /**
     * @return get the names of the optics defined in this definition set
     */
    List<String> getDefinedOpticNames();

    /**
     * Retrieve the model files required for the given optic.
     * 
     * @param opticName the name of the optic as retrieved by {@link #getDefinedOpticNames()}
     * @return the list of model files
     */
    List<ModelFile> getOpticModelFiles(String opticName);
}

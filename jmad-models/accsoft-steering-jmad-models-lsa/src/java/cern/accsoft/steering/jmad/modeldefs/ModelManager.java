/*
fro82CERN * $Id $
 * 
 * $Date$ $Revision$ $Author$
 * 
 * Copyright CERN ${year}, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.modeldefs;

import java.util.Collection;

import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.service.JMadService;

/**
 * The manager of the available JMad model instances. e.g. one model for each LHC beam.
 * 
 * @author muellerg
 */
public interface ModelManager {

    /**
     * @return all the available JMad-model instances
     */
    public Collection<JMadModel> getModels();

    /**
     * Reset all {@link JMadModel}s belonging to this {@link ModelManager}. This should be called after a given
     * time/number of operations in MadX as otherwise MadX consumes too much memory and gets stuck eventually.
     */
    public abstract void resetModels();

    /**
     * @return the {@link JMadService} providing access to JMad
     */
    public JMadService getJMadService();

    /**
     * @return the optic names that are available for all Models in provided by the Manager
     */
    public Collection<String> availableCommonOpticNames();

    /**
     * Set the {@link cern.accsoft.steering.jmad.modeldefs.domain.OpticsDefinition} with the given name in all available
     * Models. The user should only use Optic Names as provided by the call {@link #availableCommonOpticNames()}
     * otherwise either nothing is done and an error written to the logging, and/or only a part of the models which
     * actually support/contain this optics are changed. The models are only updated, if the current optic definition
     * loaded has a different name then the optic name provided.
     * 
     * @param opticName the name of the {@link cern.accsoft.steering.jmad.modeldefs.domain.OpticsDefinition} to set in
     *            all available models.
     */
    public void setActiveOptics(String opticName);
}

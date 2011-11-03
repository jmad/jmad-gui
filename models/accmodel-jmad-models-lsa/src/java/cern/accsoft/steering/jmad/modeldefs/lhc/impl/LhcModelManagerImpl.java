/*
 * $Id $
 * 
 * $Date$ $Revision$ $Author$
 * 
 * Copyright CERN ${year}, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.modeldefs.lhc.impl;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.domain.machine.RangeDefinition;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.modeldefs.impl.AbstractModelManager;
import cern.accsoft.steering.jmad.modeldefs.lhc.LhcModelManager;
import cern.accsoft.steering.jmad.modeldefs.lhc.LhcUtil;
import cern.accsoft.steering.util.acc.BeamNumber;

/**
 * The Manager for LHC models. It has two models: one for each beam.
 * 
 * @author muellerg
 */
public class LhcModelManagerImpl extends AbstractModelManager implements LhcModelManager {

    /** one model for each beam */
    private Map<BeamNumber, JMadModel> models = new EnumMap<BeamNumber, JMadModel>(BeamNumber.class);

    public static LhcModelManager createModelManager() throws JMadModelException {
        LhcModelManagerImpl lhcModelManager = new LhcModelManagerImpl();
        lhcModelManager.init();
        return lhcModelManager;
    }

    /**
     * creates all models. Will be called by spring
     * 
     * @throws JMadModelException if the creation of at least one model fails
     */
    public void init() throws JMadModelException {
        this.initializeModels(LhcUtil.NOMINAL_MODEL_DEFINITION_NAME);
    }

    private void initializeModels(String modelDefinitionName) throws JMadModelException {
        if (getJMadService().getModelManager().getActiveModel() != null && //
                getJMadService().getModelManager().getActiveModel()//
                        .getModelDefinition().getName().compareTo(modelDefinitionName) == 0) {
            return;
        }

        for (BeamNumber beamNumber : BeamNumber.values()) {
            JMadModel model = this.models.put(beamNumber, createBeamModel(modelDefinitionName, LhcUtil
                    .getSequenceName(beamNumber)));
            if (model != null) {
                /* there was already a model defined for the given beam */
                model.cleanup();
                getJMadService().getModelManager().removeModel(model);
            }
        }

        getJMadService().getModelManager().setActiveModel(this.models.get(BeamNumber.BEAM_1));
    }

    /**
     * creates the JMad model for one beam.
     * 
     * @param modelDefinitionName the name of the model definition to load
     * @param sequenceName the name of the sequence to load by default
     * @return the fully initialized model
     * @throws JMadModelException if loading of the model fails
     */
    private JMadModel createBeamModel(String modelDefinitionName, String sequenceName) throws JMadModelException {
        /* then find a model definition */
        JMadModelDefinition modelDefinition = getJMadService().getModelDefinitionManager().getModelDefinition(
                modelDefinitionName);

        RangeDefinition rangeDefinition = modelDefinition.getSequenceDefinition(sequenceName).getRangeDefinition(
                LhcUtil.DEFAULT_RANGE_NAME);

        /* create the model, add to model manager and initialize it */
        JMadModel model = getJMadService().createModel(modelDefinition);
        if (super.getStartupConfiguration() != null) {
            /* transfer the start-up configuration */
            model.getStartupConfiguration().setInitialOpticsDefinition(
                    super.getStartupConfiguration().getInitialOpticsDefinition());
            model.getStartupConfiguration().setLoadDefaultOptics(super.getStartupConfiguration().isLoadDefaultOptics());
            model.getStartupConfiguration().setLoadDefaultRange(super.getStartupConfiguration().isLoadDefaultRange());
        }

        model.getStartupConfiguration().setInitialRangeDefinition(rangeDefinition);

        model.init();
        return model;
    }

    @Override
    public JMadModel getModel(BeamNumber beam) {
        return this.models.get(beam);
    }

    @Override
    public Collection<JMadModel> getModels() {
        return models.values();
    }

    @Override
    public void loadModelDefinition(String modelDefinitionName) throws JMadModelException {
        this.initializeModels(modelDefinitionName);
    }
}

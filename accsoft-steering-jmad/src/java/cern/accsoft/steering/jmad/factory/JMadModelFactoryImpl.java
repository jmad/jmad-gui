package cern.accsoft.steering.jmad.factory;

import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.JMadModelImpl;
import cern.accsoft.steering.jmad.model.KnobManager;
import cern.accsoft.steering.jmad.model.knob.custom.DeltaPKnob;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;

public abstract class JMadModelFactoryImpl implements JMadModelFactory {

    @Override
    public final JMadModel createModel(JMadModelDefinition modelDefinition) {
        JMadModelImpl model = createJMadModelImpl();
        model.setModelDefinition(modelDefinition);
        createDefaultKnobs(model);
        return model;
    }

    @Override
    public final void createDefaultKnobs(JMadModel model) {
        KnobManager knobManager = model.getKnobManager();
        knobManager.addCustomKnob(new DeltaPKnob(model));
    }

    /**
     * This method will be injected by spring in order to create a preconfigured model
     * 
     * @return the preinitialized instance of a model
     */
    protected abstract JMadModelImpl createJMadModelImpl();

}

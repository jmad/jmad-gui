/**
 * 
 */
package cern.accsoft.steering.jmad.modeldefs.defs.lhc;

import cern.accsoft.steering.jmad.domain.file.CallableModelFileImpl;
import cern.accsoft.steering.jmad.domain.file.ModelPathOffsets;
import cern.accsoft.steering.jmad.domain.file.ModelPathOffsetsImpl;
import cern.accsoft.steering.jmad.domain.file.ModelFile.ModelFileLocation;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinitionImpl;
import cern.accsoft.steering.jmad.modeldefs.lhc.LhcUtil;

/**
 * The model definition for the LHC model
 * 
 * @author kfuchsbe
 */
public class LhcNominalModelDefinitionFactory extends AbstractLhcNominalModelDefinitionFactory {

    @Override
    protected void addInitFiles(JMadModelDefinitionImpl modelDefinition) {
        modelDefinition.addInitFile(new CallableModelFileImpl("init-constants.madx", ModelFileLocation.RESOURCE));
        modelDefinition.addInitFile(new CallableModelFileImpl("V6.5.seq"));
        modelDefinition.addInitFile(new CallableModelFileImpl("install_additional_elements.madx"));
        modelDefinition.addInitFile(new CallableModelFileImpl("install-ac-dipole.madx", ModelFileLocation.RESOURCE));
    }

    @Override
    protected ModelPathOffsets createModelPathOffsets() {
        ModelPathOffsetsImpl offsets = new ModelPathOffsetsImpl();
        offsets.setResourceOffset("lhc");
        offsets.setRepositoryOffset("lhc/optics/V6.503");
        return offsets;
    }

    @Override
    protected String getModelDefinitionName() {
        return LhcUtil.NOMINAL_MODEL_DEFINITION_NAME;
    }

}

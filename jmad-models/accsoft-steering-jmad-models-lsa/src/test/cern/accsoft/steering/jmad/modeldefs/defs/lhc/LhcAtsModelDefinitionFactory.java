/**
 * 
 */
package cern.accsoft.steering.jmad.modeldefs.defs.lhc;

import cern.accsoft.steering.jmad.domain.file.CallableModelFileImpl;
import cern.accsoft.steering.jmad.domain.file.ModelPathOffsets;
import cern.accsoft.steering.jmad.domain.file.ModelPathOffsetsImpl;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinitionImpl;
import cern.accsoft.steering.jmad.modeldefs.lhc.LhcUtil;

/**
 * The model definition for the ats optics model
 * 
 * @author muellerg
 */
public class LhcAtsModelDefinitionFactory extends AbstractLhcAtsModelDefinitionFactory {

    @Override
    protected void addInitFiles(JMadModelDefinitionImpl modelDefinition) {
        modelDefinition.addInitFile(new CallableModelFileImpl("V6.503/V6.5.seq"));
        modelDefinition.addInitFile(new CallableModelFileImpl("V6.503/install_additional_elements.madx"));
    }

    @Override
    protected ModelPathOffsets createModelPathOffsets() {
        ModelPathOffsetsImpl offsets = new ModelPathOffsetsImpl();
        offsets.setResourceOffset("lhc/ats");
        offsets.setRepositoryOffset("lhc/optics");
        return offsets;
    }

    @Override
    protected String getModelDefinitionName() {
        return LhcUtil.ATS_MODEL_DEFINITION_NAME;
    }
}

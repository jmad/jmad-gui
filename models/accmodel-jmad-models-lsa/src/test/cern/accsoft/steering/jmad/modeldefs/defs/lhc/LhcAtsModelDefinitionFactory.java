/**
 * 
 */
package cern.accsoft.steering.jmad.modeldefs.defs.lhc;

import java.util.Collections;
import java.util.List;

import cern.accsoft.steering.jmad.domain.file.CallableModelFileImpl;
import cern.accsoft.steering.jmad.domain.file.ModelPathOffsets;
import cern.accsoft.steering.jmad.domain.file.ModelPathOffsetsImpl;
import cern.accsoft.steering.jmad.domain.machine.RangeDefinitionImpl;
import cern.accsoft.steering.jmad.modeldefs.create.OpticDefinitionSet;
import cern.accsoft.steering.jmad.modeldefs.create.OpticDefinitionSetBuilder;
import cern.accsoft.steering.jmad.modeldefs.create.OpticModelFileBuilder;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinitionImpl;
import cern.accsoft.steering.jmad.modeldefs.lhc.LhcUtil;

/**
 * The model definition for the ats optics model
 * 
 * @author muellerg
 */
public class LhcAtsModelDefinitionFactory extends AbstractLhcModelDefinitionFactory {

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

    @Override
    protected void addPostUseFiles(RangeDefinitionImpl rangeDefinition) {
        /* nothing to do here */
    }

    @Override
    protected List<OpticDefinitionSet> getOpticDefinitionSets() {
        OpticDefinitionSetBuilder builder = OpticDefinitionSetBuilder.newInstance();
        builder.addOptic("A1100C1100A1000L1000_2011_ATS_INJ", OpticModelFileBuilder
                .createInstance("ATS_V6.503/OPTICS_MD2011/opticsfile.1"));
        builder.addOptic("A1100C1100A1000L1000_2011_ATS", OpticModelFileBuilder
                .createInstance("ATS_V6.503/OPTICS_MD2011/opticsfile.2"));
        builder.addOptic("A1000C1000A1000L1000_2011_ATS", OpticModelFileBuilder
                .createInstance("ATS_V6.503/OPTICS_MD2011/opticsfile.3"));
        builder.addOptic("A800C800A1000L1000_2011_ATS", OpticModelFileBuilder
                .createInstance("ATS_V6.503/OPTICS_MD2011/opticsfile.4"));
        builder.addOptic("A600C600A1000L1000_2011_ATS", OpticModelFileBuilder
                .createInstance("ATS_V6.503/OPTICS_MD2011/opticsfile.5"));
        builder.addOptic("A440C440A1000L1000_2011_ATS", OpticModelFileBuilder
                .createInstance("ATS_V6.503/OPTICS_MD2011/opticsfile.6"));
        builder.addOptic("A300C300A1000L1000_2011_ATS", OpticModelFileBuilder
                .createInstance("ATS_V6.503/OPTICS_MD2011/opticsfile.7"));
        builder.addOptic("A240C240A1000L1000_2011_ATS", OpticModelFileBuilder
                .createInstance("ATS_V6.503/OPTICS_MD2011/opticsfile.8"));
        builder.addOptic("A200C200A1000L1000_2011_ATS", OpticModelFileBuilder
                .createInstance("ATS_V6.503/OPTICS_MD2011/opticsfile.9"));
        builder.addOptic("A160C160A1000L1000_2011_ATS", OpticModelFileBuilder
                .createInstance("ATS_V6.503/OPTICS_MD2011/opticsfile.10"));
        builder.addOptic("A120C120A1000L1000_2011_ATS", OpticModelFileBuilder
                .createInstance("ATS_V6.503/OPTICS_MD2011/opticsfile.11"));
        builder.addOptic("A117C120A1000L1000_2011_ATS", OpticModelFileBuilder
                .createInstance("ATS_V6.503/OPTICS_MD2011/opticsfile.12"));
        builder.addOptic("A114C120A1000L1000_2011_ATS", OpticModelFileBuilder
                .createInstance("ATS_V6.503/OPTICS_MD2011/opticsfile.13"));
        builder.addOptic("A111C120A1000L1000_2011_ATS", OpticModelFileBuilder
                .createInstance("ATS_V6.503/OPTICS_MD2011/opticsfile.14"));
        builder.addOptic("A109C120A1000L1000_2011_ATS", OpticModelFileBuilder
                .createInstance("ATS_V6.503/OPTICS_MD2011/opticsfile.15"));
        builder.addOptic("A108C120A1000L1000_2011_ATS", OpticModelFileBuilder
                .createInstance("ATS_V6.503/OPTICS_MD2011/opticsfile.16"));
        builder.addOptic("A105C120A1000L1000_2011_ATS", OpticModelFileBuilder
                .createInstance("ATS_V6.503/OPTICS_MD2011/opticsfile.17"));
        builder.addOptic("A94C120A1000L1000_2011_ATS", OpticModelFileBuilder
                .createInstance("ATS_V6.503/OPTICS_MD2011/opticsfile.18"));
        builder.addOptic("A84C120A1000L1000_2011_ATS", OpticModelFileBuilder
                .createInstance("ATS_V6.503/OPTICS_MD2011/opticsfile.19"));
        builder.addOptic("A72C120A1000L1000_2011_ATS", OpticModelFileBuilder
                .createInstance("ATS_V6.503/OPTICS_MD2011/opticsfile.20"));
        builder.addOptic("A60C120A1000L1000_2011_ATS", OpticModelFileBuilder
                .createInstance("ATS_V6.503/OPTICS_MD2011/opticsfile.21"));
        builder.addOptic("A54C120A1000L1000_2011_ATS", OpticModelFileBuilder
                .createInstance("ATS_V6.503/OPTICS_MD2011/opticsfile.22"));
        builder.addOptic("A48C120A1000L1000_2011_ATS", OpticModelFileBuilder
                .createInstance("ATS_V6.503/OPTICS_MD2011/opticsfile.23"));
        builder.addOptic("A42C120A1000L1000_2011_ATS", OpticModelFileBuilder
                .createInstance("ATS_V6.503/OPTICS_MD2011/opticsfile.24"));
        builder.addOptic("A36C120A1000L1000_2011_ATS", OpticModelFileBuilder
                .createInstance("ATS_V6.503/OPTICS_MD2011/opticsfile.25"));
        builder.addOptic("A30C120A1000L1000_2011_ATS", OpticModelFileBuilder
                .createInstance("ATS_V6.503/OPTICS_MD2011/opticsfile.26"));
        builder.addOptic("A1100C1100A1000L1000_2011_ATS_INJ_OLD", new OpticModelFileBuilder[] {
                OpticModelFileBuilder.createInstance("ATS_V6.503/ats_V6.503.inj.str"),
                OpticModelFileBuilder.createInstance("ATS_V6.503/errors/Presetandknob_B1_inj_450.madx"),
                OpticModelFileBuilder.createInstance("ATS_V6.503/errors/Presetandknob_B2_inj_450.madx"),
                OpticModelFileBuilder.createInstance("tc_re-match_inj.madx").isResource().doNotParse() });

        return Collections.singletonList(builder.build());
    }
}

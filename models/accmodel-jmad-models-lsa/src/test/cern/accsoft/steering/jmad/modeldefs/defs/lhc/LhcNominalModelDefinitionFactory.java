/**
 * 
 */
package cern.accsoft.steering.jmad.modeldefs.defs.lhc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cern.accsoft.steering.jmad.domain.file.CallableModelFileImpl;
import cern.accsoft.steering.jmad.domain.file.ModelPathOffsets;
import cern.accsoft.steering.jmad.domain.file.ModelPathOffsetsImpl;
import cern.accsoft.steering.jmad.domain.file.ModelFile.ModelFileLocation;
import cern.accsoft.steering.jmad.domain.machine.RangeDefinitionImpl;
import cern.accsoft.steering.jmad.modeldefs.create.OpticDefinitionSet;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinitionImpl;
import cern.accsoft.steering.jmad.modeldefs.lhc.LhcUtil;

/**
 * The model definition for the LHC model
 * 
 * @author kfuchsbe
 */
public class LhcNominalModelDefinitionFactory extends AbstractLhcModelDefinitionFactory {

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

    @Override
    protected void addPostUseFiles(RangeDefinitionImpl rangeDefinition) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected List<OpticDefinitionSet> getOpticDefinitionSets() {
        private enum OpticsDef {
            A1100C1100A1000L1000_FLAT_INJ(new String[] { "match-chroma.madx" }), // for Ramp using still old optics
            A1100C1100A1000L1000_INJ_2011(new String[] { "match-chroma.madx" }), //
            /* Tune shift Optic */
            A1100C1100A1000L1000_2011(
                    new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str", "match-chroma.madx" }), //
            /* Full combined Squeeze to 2m b* in all IP's */
            A1100C1100A1000_0x00951L1000_0x00951_FLAT(new String[] { "IR1/IP1_beta11.00.str", "IR5/IP5_beta11.00.str",
                    "IR8/3.5TeV/special/ip8_0.00951_beta10.00m.str", "IR2/3.5TeV/special/ip2_0.00951_beta10.00m.str",
                    "match-chroma.madx" }), //
            A1100C1100A982_0x00941L1000_0x00951_FLAT(new String[] { "IR1/IP1_beta11.00.str", "IR5/IP5_beta11.00.str",
                    "IR8/3.5TeV/special/ip8_0.00951_beta10.00m.str", "IR2/3.5TeV/special/ip2_0.00941_beta9.82m.str",
                    "match-chroma.madx" }), //
            A1100C1100A950_0x00928L950_0x00949_FLAT(new String[] { "IR1/IP1_beta11.00.str", "IR5/IP5_beta11.00.str",
                    "IR8/3.5TeV/special/ip8_0.00949_beta9.50m.str", "IR2/3.5TeV/special/ip2_0.00928_beta9.50m.str",
                    "match-chroma.madx" }), //
            A900C900A900_0x00915L900_0x00949_FLAT(new String[] { "IR1/IP1_beta_9.00.str", "IR5/IP5_beta_9.00.str",
                    "IR8/3.5TeV/special/ip8_0.00949_beta9.00m.str", "IR2/3.5TeV/special/ip2_0.00915_beta9.00m.str",
                    "match-chroma.madx" }), //
            A900C900A850_0x00907L850_0x00945_FLAT(new String[] { "IR1/IP1_beta_9.00.str", "IR5/IP5_beta_9.00.str",
                    "IR8/3.5TeV/special/ip8_0.00945_beta8.50m.str", "IR2/3.5TeV/special/ip2_0.00907_beta8.50m.str",
                    "match-chroma.madx" }), //
            A900C900A800_0x00901L800_0x00942_FLAT(new String[] { "IR1/IP1_beta_9.00.str", "IR5/IP5_beta_9.00.str",
                    "IR8/3.5TeV/special/ip8_0.00942_beta8.00m.str", "IR2/3.5TeV/special/ip2_0.00901_beta8.00m.str",
                    "match-chroma.madx" }), //
            A900C900A750_0x00897L750_0x00932_FLAT(new String[] { "IR1/IP1_beta_9.00.str", "IR5/IP5_beta_9.00.str",
                    "IR8/3.5TeV/special/ip8_0.00932_beta7.50m.str", "IR2/3.5TeV/special/ip2_0.00897_beta7.50m.str",
                    "match-chroma.madx" }), //
            A700C700A700_0x00893L700_0x00923_FLAT(new String[] { "IR5/IP5_beta_7.00.str", "IR1/IP1_beta_7.00.str",
                    "IR8/3.5TeV/special/ip8_0.00923_beta7.00m.str", "IR2/3.5TeV/special/ip2_0.00893_beta7.00m.str",
                    "match-chroma.madx" }), //
            A700C700A650_0x00891L650_0x00915_FLAT(new String[] { "IR5/IP5_beta_7.00.str", "IR1/IP1_beta_7.00.str",
                    "IR8/3.5TeV/special/ip8_0.00915_beta6.50m.str", "IR2/3.5TeV/special/ip2_0.00891_beta6.50m.str",
                    "match-chroma.madx" }), //
            A700C700A600_0x00889L600_0x00909_FLAT(new String[] { "IR5/IP5_beta_7.00.str", "IR1/IP1_beta_7.00.str",
                    "IR8/3.5TeV/special/ip8_0.00909_beta6.00m.str", "IR2/3.5TeV/special/ip2_0.00889_beta6.00m.str",
                    "match-chroma.madx" }), //
            A700C700A550_0x00889L550_0x00904_FLAT(new String[] { "IR5/IP5_beta_7.00.str", "IR1/IP1_beta_7.00.str",
                    "IR8/3.5TeV/special/ip8_0.00904_beta5.50m.str", "IR2/3.5TeV/special/ip2_0.00889_beta5.50m.str",
                    "match-chroma.madx" }), //
            A500C500A500_0x00889L500_0x00900_FLAT(new String[] { "IR1/IP1_beta_5.00.str", "IR5/IP5_beta_5.00.str",
                    "IR8/3.5TeV/special/ip8_0.00900_beta5.00m.str", "IR2/3.5TeV/special/ip2_0.00889_beta5.00m.str",
                    "match-chroma.madx" }), //
            A500C500A450_0x00889L450_0x00896_FLAT(new String[] { "IR1/IP1_beta_5.00.str", "IR5/IP5_beta_5.00.str",
                    "IR8/3.5TeV/special/ip8_0.00896_beta4.50m.str", "IR2/3.5TeV/special/ip2_0.00889_beta4.50m.str",
                    "match-chroma.madx" }), //
            A400C400A400_0x00889L400_0x00893_FLAT(new String[] { "IR5/IP5_beta_4.00.str", "IR1/IP1_beta_4.00.str",
                    "IR8/3.5TeV/special/ip8_0.00893_beta4.00m.str", "IR2/3.5TeV/special/ip2_0.00889_beta4.00m.str",
                    "match-chroma.madx" }), //
            A400C400A400_0x00889L375_0x00888_FLAT(new String[] { "IR5/IP5_beta_4.00.str", "IR1/IP1_beta_4.00.str",
                    "IR2/3.5TeV/special/ip2_0.00889_beta4.00m.str", "IR8/3.5TeV/special/ip8_0.00888_beta3.75m.str",
                    "match-chroma.madx" }), //
            A350C350A350_0x00889L350_0x00882_FLAT(new String[] { "IR1/IP1_beta_3.50.str", "IR5/IP5_beta_3.50.str",
                    "IR8/3.5TeV/special/ip8_0.00882_beta3.50m.str", "IR2/3.5TeV/special/ip2_0.00889_beta3.50m.str",
                    "match-chroma.madx" }), //
            A350C350A350_0x00889L325_0x00878_FLAT(new String[] { "IR1/IP1_beta_3.50.str", "IR5/IP5_beta_3.50.str",
                    "IR2/3.5TeV/special/ip2_0.00889_beta3.50m.str", "IR8/3.5TeV/special/ip8_0.00878_beta3.25m.str",
                    "match-chroma.madx" }), //
            A350C350A300_0x00889L300_0x00875_FLAT(new String[] { "IR1/IP1_beta_3.50.str", "IR5/IP5_beta_3.50.str",
                    "IR8/3.5TeV/special/ip8_0.00875_beta3.00m.str", "IR2/3.5TeV/special/ip2_0.00889_beta3.00m.str",
                    "match-chroma.madx" }), //
            A250C250A250_0x00889L250_0x00872_FLAT(new String[] { "IR5/IP5_beta_2.50.str", "IR1/IP1_beta_2.50.str",
                    "IR8/3.5TeV/special/ip8_0.00872_beta2.50m.str", "IR2/3.5TeV/special/ip2_0.00889_beta2.50m.str",
                    "match-chroma.madx" }), //
            A200C200A200_0x00889L200_0x00872_FLAT(new String[] { "IR1/IP1_beta_2.00.str", "IR5/IP5_beta_2.00.str",
                    "IR8/3.5TeV/special/ip8_0.00872_beta2.00m.str", "IR2/3.5TeV/special/ip2_0.00889_beta2.00m.str",
                    "match-chroma.madx" }), //
            /* TOTEM unsqueeze Optics */
            A1100C7500A1000L1000_FLAT(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_75.str", "totem-matching-main-quads.madx" }), //
            A1100C6700A1000L1000_FLAT(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_67.str", "totem-matching-main-quads.madx" }), //
            A1100C6000A1000L1000_FLAT(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_60.str", "totem-matching-main-quads.madx" }), //
            A1100C5400A1000L1000_FLAT(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_54.str", "totem-matching-main-quads.madx" }), //
            A1100C5100A1000L1000_FLAT(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_51.str", "totem-matching-main-quads.madx" }), //
            A1100C4600A1000L1000_FLAT(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_46.str", "totem-matching-main-quads.madx" }), //
            A1100C4300A1000L1000_FLAT(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_43.str", "totem-matching-main-quads.madx" }), //
            A1100C4000A1000L1000_FLAT(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_40.str", "totem-matching-main-quads.madx" }), //
            A1100C3600A1000L1000_FLAT(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_36.str", "totem-matching-main-quads.madx" }), //
            A1100C3300A1000L1000_FLAT(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_33.str", "totem-matching-main-quads.madx" }), //
            A1100C3000A1000L1000_FLAT(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_30.str", "totem-matching-main-quads.madx" }), //
            A1100C2500A1000L1000_FLAT(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_25.str", "totem-matching-main-quads.madx" }), //
            A1100C2200A1000L1000_FLAT(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_22.str", "totem-matching-main-quads.madx" }), //
            A1100C1920A1000L1000_FLAT(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_19.str", "totem-matching-main-quads.madx" }), //
            A1100C1670A1000L1000_FLAT(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_16.str", "totem-matching-main-quads.madx" }), //
            A1100C1450A1000L1000_FLAT(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_14.str", "totem-matching-main-quads.madx" }), //
            A1100C1260A1000L1000_FLAT(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_12.str", "totem-matching-main-quads.madx" }), //
            A1100C9000A1000L1000_FLAT(new String[] { "IR1/IP1_beta11.00.str", "IR5/IP5_beta11.00.str",
                    "HiBeta/IP5_beta90_2010.str", "totem-matching-main-quads.madx" }), //
            A1100C7500A1000L1000_FLAT_TQ(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_75.str", "totem-matching-trim-quads.madx" }), //
            A1100C6700A1000L1000_FLAT_TQ(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_67.str", "totem-matching-trim-quads.madx" }), //
            A1100C6000A1000L1000_FLAT_TQ(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_60.str", "totem-matching-trim-quads.madx" }), //
            A1100C5400A1000L1000_FLAT_TQ(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_54.str", "totem-matching-trim-quads.madx" }), //
            A1100C5100A1000L1000_FLAT_TQ(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_51.str", "totem-matching-trim-quads.madx" }), //
            A1100C4600A1000L1000_FLAT_TQ(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_46.str", "totem-matching-trim-quads.madx" }), //
            A1100C4300A1000L1000_FLAT_TQ(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_43.str", "totem-matching-trim-quads.madx" }), //
            A1100C4000A1000L1000_FLAT_TQ(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_40.str", "totem-matching-trim-quads.madx" }), //
            A1100C3600A1000L1000_FLAT_TQ(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_36.str", "totem-matching-trim-quads.madx" }), //
            A1100C3300A1000L1000_FLAT_TQ(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_33.str", "totem-matching-trim-quads.madx" }), //
            A1100C3000A1000L1000_FLAT_TQ(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_30.str", "totem-matching-trim-quads.madx" }), //
            A1100C2500A1000L1000_FLAT_TQ(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_25.str", "totem-matching-trim-quads.madx" }), //
            A1100C2200A1000L1000_FLAT_TQ(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_22.str", "totem-matching-trim-quads.madx" }), //
            A1100C1920A1000L1000_FLAT_TQ(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_19.str", "totem-matching-trim-quads.madx" }), //
            A1100C1670A1000L1000_FLAT_TQ(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_16.str", "totem-matching-trim-quads.madx" }), //
            A1100C1450A1000L1000_FLAT_TQ(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_14.str", "totem-matching-trim-quads.madx" }), //
            A1100C1260A1000L1000_FLAT_TQ(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP5_12.str", "totem-matching-trim-quads.madx" }), //
            A1100C9000A1000L1000_FLAT_TQ(new String[] { "IR1/IP1_beta11.00.str", "IR5/IP5_beta11.00.str",
                    "HiBeta/IP5_beta90_2010.str", "totem-matching-trim-quads.madx" }), //
            /* IP1/5 squeeze to minimum b* 0.55m optics */
            A900C900A1000L1000_2011(new String[] { "IR1/IP1_beta_9.00.str", "IR5/IP5_beta_9.00.str", "match-chroma.madx" }), //
            A700C700A1000L1000_2011(new String[] { "IR5/IP5_beta_7.00.str", "IR1/IP1_beta_7.00.str", "match-chroma.madx" }), //
            A500C500A1000L1000_2011(new String[] { "IR1/IP1_beta_5.00.str", "IR5/IP5_beta_5.00.str", "match-chroma.madx" }), //
            A400C400A1000L1000_2011(new String[] { "IR5/IP5_beta_4.00.str", "IR1/IP1_beta_4.00.str", "match-chroma.madx" }), //
            A350C350A1000L1000_2011(new String[] { "IR1/IP1_beta_3.50.str", "IR5/IP5_beta_3.50.str", "match-chroma.madx" }), //
            A300C300A1000L1000_2011(new String[] { "IR1/IP1_beta_3.00.str", "IR5/IP5_beta_3.00.str", "match-chroma.madx" }), //
            A250C250A1000L1000_2011(new String[] { "IR5/IP5_beta_2.50.str", "IR1/IP1_beta_2.50.str", "match-chroma.madx" }), //
            A200C200A1000L1000_2011(new String[] { "IR1/IP1_beta_2.00.str", "IR5/IP5_beta_2.00.str", "match-chroma.madx" }), //
            A150C150A1000L1000_2011(new String[] { "IR1/IP1_beta_1.50.str", "IR5/IP5_beta_1.50.str", "match-chroma.madx" }), //
            A110C110A1000L1000_2011(new String[] { "IR1/IP1_beta_1.10.str", "IR5/IP5_beta_1.10.str", "match-chroma.madx" }), //
            A80C80A1000L1000_2011(new String[] { "IR1/IP1_beta_0.80.str", "IR5/IP5_beta_0.80.str", "match-chroma.madx" }), //
            A65C65A1000L1000_2011(new String[] { "IR1/IP1_beta_0.65.str", "IR5/IP5_beta_0.65.str", "match-chroma.madx" }), //
            A55C55A1000L1000_2011(new String[] { "IR1/IP1_beta_0.55.str", "IR5/IP5_beta_0.55.str", "match-chroma.madx" }), //
            /* Partly Squeeze IP1/5 down to 0.55 - IP8 to 5m - IP2 unsqueezed */
            A1100C1100A1000L1000_0x00951_2011(new String[] { "IR1/IP1_beta11.00.str", "IR5/IP5_beta11.00.str",
                    "IR8/3.5TeV/special/ip8_0.00951_beta10.00m.str", "match-chroma.madx" }), //
            A1100C1100A1000L950_0x00949_2011(new String[] { "IR1/IP1_beta11.00.str", "IR5/IP5_beta11.00.str",
                    "IR8/3.5TeV/special/ip8_0.00949_beta9.50m.str", "match-chroma.madx" }), //
            A900C900A1000L900_0x00949_2011(new String[] { "IR1/IP1_beta_9.00.str", "IR5/IP5_beta_9.00.str",
                    "IR8/3.5TeV/special/ip8_0.00949_beta9.00m.str", "match-chroma.madx" }), //
            A900C900A1000L850_0x00945_2011(new String[] { "IR1/IP1_beta_9.00.str", "IR5/IP5_beta_9.00.str",
                    "IR8/3.5TeV/special/ip8_0.00945_beta8.50m.str", "match-chroma.madx" }), //
            A900C900A1000L800_0x00942_2011(new String[] { "IR1/IP1_beta_9.00.str", "IR5/IP5_beta_9.00.str",
                    "IR8/3.5TeV/special/ip8_0.00942_beta8.00m.str", "match-chroma.madx" }), //
            A900C900A1000L750_0x00932_2011(new String[] { "IR1/IP1_beta_9.00.str", "IR5/IP5_beta_9.00.str",
                    "IR8/3.5TeV/special/ip8_0.00932_beta7.50m.str", "match-chroma.madx" }), //
            A700C700A1000L700_0x00923_2011(new String[] { "IR5/IP5_beta_7.00.str", "IR1/IP1_beta_7.00.str",
                    "IR8/3.5TeV/special/ip8_0.00923_beta7.00m.str", "match-chroma.madx" }), //
            A700C700A1000L650_0x00915_2011(new String[] { "IR5/IP5_beta_7.00.str", "IR1/IP1_beta_7.00.str",
                    "IR8/3.5TeV/special/ip8_0.00915_beta6.50m.str", "match-chroma.madx" }), //
            A700C700A1000L600_0x00909_2011(new String[] { "IR5/IP5_beta_7.00.str", "IR1/IP1_beta_7.00.str",
                    "IR8/3.5TeV/special/ip8_0.00909_beta6.00m.str", "match-chroma.madx" }), //
            A700C700A1000L550_0x00904_2011(new String[] { "IR5/IP5_beta_7.00.str", "IR1/IP1_beta_7.00.str",
                    "IR8/3.5TeV/special/ip8_0.00904_beta5.50m.str", "match-chroma.madx" }), //
            A500C500A1000L500_0x00900_2011(new String[] { "IR1/IP1_beta_5.00.str", "IR5/IP5_beta_5.00.str",
                    "IR8/3.5TeV/special/ip8_0.00900_beta5.00m.str", "match-chroma.madx" }), //
            A400C400A1000L500_0x00900_2011(new String[] { "IR5/IP5_beta_4.00.str", "IR1/IP1_beta_4.00.str",
                    "IR8/3.5TeV/special/ip8_0.00900_beta5.00m.str", "match-chroma.madx" }), //
            A350C350A1000L500_0x00900_2011(new String[] { "IR1/IP1_beta_3.50.str", "IR5/IP5_beta_3.50.str",
                    "IR8/3.5TeV/special/ip8_0.00900_beta5.00m.str", "match-chroma.madx" }), //
            A300C300A1000L500_0x00900_2011(new String[] { "IR1/IP1_beta_3.00.str", "IR5/IP5_beta_3.00.str",
                    "IR8/3.5TeV/special/ip8_0.00900_beta5.00m.str", "match-chroma.madx" }), //
            A250C250A1000L500_0x00900_2011(new String[] { "IR5/IP5_beta_2.50.str", "IR1/IP1_beta_2.50.str",
                    "IR8/3.5TeV/special/ip8_0.00900_beta5.00m.str", "match-chroma.madx" }), //
            A200C200A1000L500_0x00900_2011(new String[] { "IR1/IP1_beta_2.00.str", "IR5/IP5_beta_2.00.str",
                    "IR8/3.5TeV/special/ip8_0.00900_beta5.00m.str", "match-chroma.madx" }), //
            A150C150A1000L500_0x00900_2011(new String[] { "IR1/IP1_beta_1.50.str", "IR5/IP5_beta_1.50.str",
                    "IR8/3.5TeV/special/ip8_0.00900_beta5.00m.str", "match-chroma.madx" }), //
            A110C110A1000L500_0x00900_2011(new String[] { "IR1/IP1_beta_1.10.str", "IR5/IP5_beta_1.10.str",
                    "IR8/3.5TeV/special/ip8_0.00900_beta5.00m.str", "match-chroma.madx" }), //
            A80C80A1000L500_0x00900_2011(new String[] { "IR1/IP1_beta_0.80.str", "IR5/IP5_beta_0.80.str",
                    "IR8/3.5TeV/special/ip8_0.00900_beta5.00m.str", "match-chroma.madx" }), //
            A65C65A1000L500_0x00900_2011(new String[] { "IR1/IP1_beta_0.65.str", "IR5/IP5_beta_0.65.str",
                    "IR8/3.5TeV/special/ip8_0.00900_beta5.00m.str", "match-chroma.madx" }), //
            A55C55A1000L500_0x00900_2011(new String[] { "IR1/IP1_beta_0.55.str", "IR5/IP5_beta_0.55.str",
                    "IR8/3.5TeV/special/ip8_0.00900_beta5.00m.str", "match-chroma.madx" }), //
            A500C500A1000L450_0x00896_2011(new String[] { "IR1/IP1_beta_5.00.str", "IR5/IP5_beta_5.00.str",
                    "IR8/3.5TeV/special/ip8_0.00896_beta4.50m.str", "match-chroma.madx" }), //
            A400C400A1000L400_0x00893_2011(new String[] { "IR5/IP5_beta_4.00.str", "IR1/IP1_beta_4.00.str",
                    "IR8/3.5TeV/special/ip8_0.00893_beta4.00m.str", "match-chroma.madx" }), //
            A400C400A1000L375_0x00888_2011(new String[] { "IR5/IP5_beta_4.00.str", "IR1/IP1_beta_4.00.str",
                    "IR8/3.5TeV/special/ip8_0.00888_beta3.75m.str", "match-chroma.madx" }), //
            A350C350A1000L350_0x00882_2011(new String[] { "IR1/IP1_beta_3.50.str", "IR5/IP5_beta_3.50.str",
                    "IR8/3.5TeV/special/ip8_0.00882_beta3.50m.str", "match-chroma.madx" }), //
            A350C350A1000L325_0x00878_2011(new String[] { "IR1/IP1_beta_3.50.str", "IR5/IP5_beta_3.50.str",
                    "IR8/3.5TeV/special/ip8_0.00878_beta3.25m.str", "match-chroma.madx" }), //
            A300C300A1000L300_0x00875_2011(new String[] { "IR1/IP1_beta_3.00.str", "IR5/IP5_beta_3.00.str",
                    "IR8/3.5TeV/special/ip8_0.00875_beta3.00m.str", "match-chroma.madx" }), //
            A250C250A1000L300_0x00875_2011(new String[] { "IR5/IP5_beta_2.50.str", "IR1/IP1_beta_2.50.str",
                    "IR8/3.5TeV/special/ip8_0.00875_beta3.00m.str", "match-chroma.madx" }), //
            A200C200A1000L300_0x00875_2011(new String[] { "IR1/IP1_beta_2.00.str", "IR5/IP5_beta_2.00.str",
                    "IR8/3.5TeV/special/ip8_0.00875_beta3.00m.str", "match-chroma.madx" }), //
            A160C160A1000L300_0x00875_2011(new String[] { "IR1/IP1_beta_1.60.str", "IR5/IP5_beta_1.60.str",
                    "IR8/3.5TeV/special/ip8_0.00875_beta3.00m.str", "match-chroma.madx" }), //
            A150C150A1000L300_0x00875_2011(new String[] { "IR1/IP1_beta_1.50.str", "IR5/IP5_beta_1.50.str",
                    "IR8/3.5TeV/special/ip8_0.00875_beta3.00m.str", "match-chroma.madx" }),
            /* combined unsqueeze Optics IR1/5 */
            A7500C7500A1000L1000_2011(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP1_75.str", "HiBeta/un-squeeze-11-to-90m/IP5_75.str",
                    "totem-matching-main-quads.madx" }), //
            A6700C6700A1000L1000_2011(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP1_67.str", "HiBeta/un-squeeze-11-to-90m/IP5_67.str",
                    "totem-matching-main-quads.madx" }), //
            A6000C6000A1000L1000_2011(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP1_60.str", "HiBeta/un-squeeze-11-to-90m/IP5_60.str",
                    "totem-matching-main-quads.madx" }), //
            A5400C5400A1000L1000_2011(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP1_54.str", "HiBeta/un-squeeze-11-to-90m/IP5_54.str",
                    "totem-matching-main-quads.madx" }), //
            A5100C5100A1000L1000_2011(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP1_51.str", "HiBeta/un-squeeze-11-to-90m/IP5_51.str",
                    "totem-matching-main-quads.madx" }), //
            A4600C4600A1000L1000_2011(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP1_46.str", "HiBeta/un-squeeze-11-to-90m/IP5_46.str",
                    "totem-matching-main-quads.madx" }), //
            A4300C4300A1000L1000_2011(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP1_43.str", "HiBeta/un-squeeze-11-to-90m/IP5_43.str",
                    "totem-matching-main-quads.madx" }), //
            A4000C4000A1000L1000_2011(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP1_40.str", "HiBeta/un-squeeze-11-to-90m/IP5_40.str",
                    "totem-matching-main-quads.madx" }), //
            A3600C3600A1000L1000_2011(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP1_36.str", "HiBeta/un-squeeze-11-to-90m/IP5_36.str",
                    "totem-matching-main-quads.madx" }), //
            A3300C3300A1000L1000_2011(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP1_33.str", "HiBeta/un-squeeze-11-to-90m/IP5_33.str",
                    "totem-matching-main-quads.madx" }), //
            A3000C3000A1000L1000_2011(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP1_30.str", "HiBeta/un-squeeze-11-to-90m/IP5_30.str",
                    "totem-matching-main-quads.madx" }), //
            A2500C2500A1000L1000_2011(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP1_25.str", "HiBeta/un-squeeze-11-to-90m/IP5_25.str",
                    "totem-matching-main-quads.madx" }), //
            A2200C2200A1000L1000_2011(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP1_22.str", "HiBeta/un-squeeze-11-to-90m/IP5_22.str",
                    "totem-matching-main-quads.madx" }), //
            A1920C1920A1000L1000_2011(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP1_19.str", "HiBeta/un-squeeze-11-to-90m/IP5_19.str",
                    "totem-matching-main-quads.madx" }), //
            A1670C1670A1000L1000_2011(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP1_16.str", "HiBeta/un-squeeze-11-to-90m/IP5_16.str",
                    "totem-matching-main-quads.madx" }), //
            A1450C1450A1000L1000_2011(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP1_14.str", "HiBeta/un-squeeze-11-to-90m/IP5_14.str",
                    "totem-matching-main-quads.madx" }), //
            A1260C1260A1000L1000_2011(new String[] { "IR5/IP5_beta11.00.str", "IR1/IP1_beta11.00.str",
                    "HiBeta/un-squeeze-11-to-90m/IP1_12.str", "HiBeta/un-squeeze-11-to-90m/IP5_12.str",
                    "totem-matching-main-quads.madx" }), //
            A9000C9000A1000L1000_2011(new String[] { "IR1/IP1_beta11.00.str", "IR5/IP5_beta11.00.str",
                    "HiBeta/IP1_beta90.str", "HiBeta/IP5_beta90_2010.str", "totem-matching-main-quads.madx" });
            protected final static String[] COMMON_FILE_NAMES = new String[] { "V6.5.inj_special.str" };
            protected final static String[] COMMON_FILE_NAMES_RESOURCE = new String[] { "ac-dipole.str" };

            private String[] fileNames;

            private OpticsDef(String[] fileNames) {
                this.fileNames = fileNames;
            }

            public List<String> getFileNames() {
                List<String> fileNames = new ArrayList<String>(Arrays.asList(COMMON_FILE_NAMES));
                fileNames.addAll(Arrays.asList(this.fileNames));
                return fileNames;
            }

            @Override
            public String toString() {
                return this.name().replaceAll("x", ".");
            }
        }
    }

}

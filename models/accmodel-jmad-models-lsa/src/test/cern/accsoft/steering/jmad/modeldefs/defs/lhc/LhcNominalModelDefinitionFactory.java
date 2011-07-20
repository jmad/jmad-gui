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
import cern.accsoft.steering.jmad.modeldefs.create.OpticDefinitionSetBuilder;
import cern.accsoft.steering.jmad.modeldefs.create.OpticModelFileBuilder;
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
      List<OpticDefinitionSet> definitionSetList = new ArrayList<OpticDefinitionSet>();
      
      definitionSetList.add(this.createTotemOpticsSet());
      
      definitionSetList.add(this.createLowBeta2011OpticsSet());

      return definitionSetList;
      
    }

  private OpticDefinitionSet createLowBeta2011OpticsSet() {

    // 1) New optics import for beta* below 1.5 in IP1 and IP5.
    // 2) Also added squeeze in IP1 and IP5 only for Ramp&squeeze studies:
    //    a) Optics available at the beginning of 2011 for beta* 11m -> 1.5m
    //    b) New optics as in (1) for beta* below 1.5m

    OpticDefinitionSetBuilder builder = OpticDefinitionSetBuilder.newInstance();

    builder.addInitialCommonOpticFile(OpticModelFileBuilder.createInstance("V6.5.inj_special.str"));

    // 1)
    builder.addOptic("A55C55A1000L300_0.00875_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("IR1/new_ip1_b2_squeeze/IP1_beta_0.55m.str"),
        OpticModelFileBuilder.createInstance("IR5/new_ip5_b2_squeeze/IP5_beta_0.55m.str"),
        OpticModelFileBuilder.createInstance("IR8/3.5TeV/special/ip8_0.00875_beta3.00m.str")});
    builder.addOptic("A60C60A1000L300_0.00875_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("IR1/new_ip1_b2_squeeze/IP1_beta_0.60m.str"),
        OpticModelFileBuilder.createInstance("IR5/new_ip5_b2_squeeze/IP5_beta_0.60m.str"),
        OpticModelFileBuilder.createInstance("IR8/3.5TeV/special/ip8_0.00875_beta3.00m.str")});
    builder.addOptic("A65C65A1000L300_0.00875_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("IR1/new_ip1_b2_squeeze/IP1_beta_0.65m.str"),
        OpticModelFileBuilder.createInstance("IR5/new_ip5_b2_squeeze/IP5_beta_0.65m.str"),
        OpticModelFileBuilder.createInstance("IR8/3.5TeV/special/ip8_0.00875_beta3.00m.str")});
    builder.addOptic("A70C70A1000L300_0.00875_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("IR1/new_ip1_b2_squeeze/IP1_beta_0.70m.str"),
        OpticModelFileBuilder.createInstance("IR5/new_ip5_b2_squeeze/IP5_beta_0.70m.str"),
        OpticModelFileBuilder.createInstance("IR8/3.5TeV/special/ip8_0.00875_beta3.00m.str")});
    builder.addOptic("A80C80A1000L300_0.00875_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("IR1/new_ip1_b2_squeeze/IP1_beta_0.80m.str"),
        OpticModelFileBuilder.createInstance("IR5/new_ip5_b2_squeeze/IP5_beta_0.80m.str"),
        OpticModelFileBuilder.createInstance("IR8/3.5TeV/special/ip8_0.00875_beta3.00m.str")});
    builder.addOptic("A90C90A1000L300_0.00875_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("IR1/new_ip1_b2_squeeze/IP1_beta_0.90m.str"),
        OpticModelFileBuilder.createInstance("IR5/new_ip5_b2_squeeze/IP5_beta_0.90m.str"),
        OpticModelFileBuilder.createInstance("IR8/3.5TeV/special/ip8_0.00875_beta3.00m.str")});
    builder.addOptic("A100C100A1000L300_0.00875_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("IR1/new_ip1_b2_squeeze/IP1_beta_1.00m.str"),
        OpticModelFileBuilder.createInstance("IR5/new_ip5_b2_squeeze/IP5_beta_1.00m.str"),
        OpticModelFileBuilder.createInstance("IR8/3.5TeV/special/ip8_0.00875_beta3.00m.str")});
    builder.addOptic("A110C110A1000L300_0.00875_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("IR1/new_ip1_b2_squeeze/IP1_beta_1.10m.str"),
        OpticModelFileBuilder.createInstance("IR5/new_ip5_b2_squeeze/IP5_beta_1.10m.str"),
        OpticModelFileBuilder.createInstance("IR8/3.5TeV/special/ip8_0.00875_beta3.00m.str")});
    builder.addOptic("A120C120A1000L300_0.00875_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("IR1/new_ip1_b2_squeeze/IP1_beta_1.20m.str"),
        OpticModelFileBuilder.createInstance("IR5/new_ip5_b2_squeeze/IP5_beta_1.20m.str"),
        OpticModelFileBuilder.createInstance("IR8/3.5TeV/special/ip8_0.00875_beta3.00m.str")});
    builder.addOptic("A130C130A1000L300_0.00875_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("IR1/new_ip1_b2_squeeze/IP1_beta_1.30m.str"),
        OpticModelFileBuilder.createInstance("IR5/new_ip5_b2_squeeze/IP5_beta_1.30m.str"),
        OpticModelFileBuilder.createInstance("IR8/3.5TeV/special/ip8_0.00875_beta3.00m.str")});
    builder.addOptic("A140C140A1000L300_0.00875_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("IR1/new_ip1_b2_squeeze/IP1_beta_1.40m.str"),
        OpticModelFileBuilder.createInstance("IR5/new_ip5_b2_squeeze/IP5_beta_1.40m.str"),
        OpticModelFileBuilder.createInstance("IR8/3.5TeV/special/ip8_0.00875_beta3.00m.str")});

    // 2a)
    builder.addOptic("A150C150A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("IR1/IP1_beta_1.50.str"),
        OpticModelFileBuilder.createInstance("IR5/IP5_beta_1.50.str")});
    builder.addOptic("A160C160A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("IR1/IP1_beta_1.60.str"),
        OpticModelFileBuilder.createInstance("IR5/IP5_beta_1.60.str")});
    builder.addOptic("A200C200A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("IR1/IP1_beta_2.00.str"),
        OpticModelFileBuilder.createInstance("IR5/IP5_beta_2.00.str")});
    builder.addOptic("A250C250A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("IR1/IP1_beta_2.50.str"),
        OpticModelFileBuilder.createInstance("IR5/IP5_beta_2.50.str")});
    builder.addOptic("A300C300A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("IR1/IP1_beta_3.00.str"),
        OpticModelFileBuilder.createInstance("IR5/IP5_beta_3.00.str")});
    builder.addOptic("A350C350A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("IR1/IP1_beta_3.50.str"),
        OpticModelFileBuilder.createInstance("IR5/IP5_beta_3.50.str")});
    builder.addOptic("A400C400A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("IR1/IP1_beta_4.00.str"),
        OpticModelFileBuilder.createInstance("IR5/IP5_beta_4.00.str")});
    builder.addOptic("A500C500A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("IR1/IP1_beta_5.00.str"),
        OpticModelFileBuilder.createInstance("IR5/IP5_beta_5.00.str")});
    builder.addOptic("A700C700A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("IR1/IP1_beta_7.00.str"),
        OpticModelFileBuilder.createInstance("IR5/IP5_beta_7.00.str")});
    builder.addOptic("A900C900A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("IR1/IP1_beta_9.00.str"),
        OpticModelFileBuilder.createInstance("IR5/IP5_beta_9.00.str")});

    // 2b)
    builder.addOptic("A55C55A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("IR1/new_ip1_b2_squeeze/IP1_beta_0.55m.str"),
        OpticModelFileBuilder.createInstance("IR5/new_ip5_b2_squeeze/IP5_beta_0.55m.str")});
    builder.addOptic("A60C60A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("IR1/new_ip1_b2_squeeze/IP1_beta_0.60m.str"),
        OpticModelFileBuilder.createInstance("IR5/new_ip5_b2_squeeze/IP5_beta_0.60m.str")});
    builder.addOptic("A65C65A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("IR1/new_ip1_b2_squeeze/IP1_beta_0.65m.str"),
        OpticModelFileBuilder.createInstance("IR5/new_ip5_b2_squeeze/IP5_beta_0.65m.str")});
    builder.addOptic("A70C70A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("IR1/new_ip1_b2_squeeze/IP1_beta_0.70m.str"),
        OpticModelFileBuilder.createInstance("IR5/new_ip5_b2_squeeze/IP5_beta_0.70m.str")});
    builder.addOptic("A80C80A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("IR1/new_ip1_b2_squeeze/IP1_beta_0.80m.str"),
        OpticModelFileBuilder.createInstance("IR5/new_ip5_b2_squeeze/IP5_beta_0.80m.str")});
    builder.addOptic("A90C90A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("IR1/new_ip1_b2_squeeze/IP1_beta_0.90m.str"),
        OpticModelFileBuilder.createInstance("IR5/new_ip5_b2_squeeze/IP5_beta_0.90m.str")});
    builder.addOptic("A100C100A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("IR1/new_ip1_b2_squeeze/IP1_beta_1.00m.str"),
        OpticModelFileBuilder.createInstance("IR5/new_ip5_b2_squeeze/IP5_beta_1.00m.str")});
    builder.addOptic("A110C110A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("IR1/new_ip1_b2_squeeze/IP1_beta_1.10m.str"),
        OpticModelFileBuilder.createInstance("IR5/new_ip5_b2_squeeze/IP5_beta_1.10m.str")});
    builder.addOptic("A120C120A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("IR1/new_ip1_b2_squeeze/IP1_beta_1.20m.str"),
        OpticModelFileBuilder.createInstance("IR5/new_ip5_b2_squeeze/IP5_beta_1.20m.str")});
    builder.addOptic("A130C130A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("IR1/new_ip1_b2_squeeze/IP1_beta_1.30m.str"),
        OpticModelFileBuilder.createInstance("IR5/new_ip5_b2_squeeze/IP5_beta_1.30m.str")});
    builder.addOptic("A140C140A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("IR1/new_ip1_b2_squeeze/IP1_beta_1.40m.str"),
        OpticModelFileBuilder.createInstance("IR5/new_ip5_b2_squeeze/IP5_beta_1.40m.str")});

    builder.addFinalCommonOpticFile(OpticModelFileBuilder.createInstance("ac-dipole.str"));
    builder.addFinalCommonOpticFile(OpticModelFileBuilder.createInstance("match-chroma.madx"));

    return builder.build();

  }

  private OpticDefinitionSet createTotemOpticsSet() {
    OpticDefinitionSetBuilder builder = OpticDefinitionSetBuilder.newInstance();

    builder.addInitialCommonOpticFile(OpticModelFileBuilder.createInstance("V6.5.inj_special.str"));
    builder.addInitialCommonOpticFile(OpticModelFileBuilder.createInstance("IR5/IP5_beta11.00.str"));
    builder.addInitialCommonOpticFile(OpticModelFileBuilder.createInstance("IR1/IP1_beta11.00.str"));

    builder.addFinalCommonOpticFile(OpticModelFileBuilder.createInstance("ac-dipole.str"));
    builder.addFinalCommonOpticFile(OpticModelFileBuilder.createInstance("totem-matching-main-quads.madx"));

    builder.addOptic("A7500C7500A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP1_75.str"),
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP5_75.str")});

    /* combined unsqueeze Optics IR1/5 */

    builder.addOptic("A6700C6700A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP1_67.str"),
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP5_67.str")});
    builder.addOptic("A6000C6000A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP1_60.str"),
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP5_60.str")});
    builder.addOptic("A5400C5400A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP1_54.str"),
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP5_54.str")});
    builder.addOptic("A5100C5100A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP1_51.str"),
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP5_51.str")});
    builder.addOptic("A4600C4600A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP1_46.str"),
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP5_46.str")});
    builder.addOptic("A4300C4300A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP1_43.str"),
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP5_43.str")});
    builder.addOptic("A4000C4000A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP1_40.str"),
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP5_40.str")});
    builder.addOptic("A3600C3600A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP1_36.str"),
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP5_36.str")});
    builder.addOptic("A3300C3300A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP1_33.str"),
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP5_33.str")});
    builder.addOptic("A3000C3000A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP1_30.str"),
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP5_30.str")});
    builder.addOptic("A2500C2500A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP1_25.str"),
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP5_25.str")});
    builder.addOptic("A2200C2200A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP1_22.str"),
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP5_22.str")});
    builder.addOptic("A1920C1920A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP1_19.str"),
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP5_19.str")});
    builder.addOptic("A1670C1670A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP1_16.str"),
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP5_16.str")});
    builder.addOptic("A1450C1450A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP1_14.str"),
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP5_14.str")});
    builder.addOptic("A1260C1260A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP1_12.str"),
        OpticModelFileBuilder.createInstance("HiBeta/un-squeeze-11-to-90m/IP5_12.str")});
    builder.addOptic("A9000C9000A1000L1000_2011", new OpticModelFileBuilder[]{
        OpticModelFileBuilder.createInstance("HiBeta/IP5_beta90_2010.str"),
        OpticModelFileBuilder.createInstance("HiBeta/IP1_beta90.str")});

    return builder.build();
  }
}

package cern.accsoft.steering.jmad.modeldefs.defs.lhc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cern.accsoft.steering.jmad.domain.file.CallableModelFileImpl;
import cern.accsoft.steering.jmad.domain.file.ModelFile;
import cern.accsoft.steering.jmad.domain.file.CallableModelFile.ParseType;
import cern.accsoft.steering.jmad.domain.file.ModelFile.ModelFileLocation;
import cern.accsoft.steering.jmad.domain.machine.RangeDefinitionImpl;
import cern.accsoft.steering.jmad.modeldefs.domain.OpticsDefinition;
import cern.accsoft.steering.jmad.modeldefs.domain.OpticsDefinitionImpl;

public abstract class AbstractLhcAtsModelDefinitionFactory extends AbstractLhcThickModelDefinitionFactory {

    public AbstractLhcAtsModelDefinitionFactory() {
        super();
    }

    /**
     * @param rangeDefinition
     */
    @Override
    protected void addPostUseFiles(RangeDefinitionImpl rangeDefinition) {
        // TODO Auto-generated method stub

    }

    @Override
    protected List<OpticsDefinition> createOpticsDefinitions() {
        List<OpticsDefinition> opticsDefinitions = new ArrayList<OpticsDefinition>();

        for (OpticsDef opticsDef : OpticsDef.values()) {

            /*
             * first collect the model files
             */
            List<ModelFile> modelFiles = new ArrayList<ModelFile>();
            for (OpticFileDefinition opticFile : opticsDef.getOpticFileDefinitions()) {
                ParseType parseType = ParseType.STRENGTHS;
                ModelFileLocation modelFileLocation = ModelFileLocation.REPOSITORY;
                if (opticFile.getFileName().toLowerCase().endsWith(".madx")) {
                    parseType = ParseType.NONE;
                }

                if (opticFile.isResource()) {
                    modelFileLocation = ModelFileLocation.RESOURCE;
                }

                ModelFile modelFile = new CallableModelFileImpl(opticFile.getFileName(), modelFileLocation, parseType);
                modelFiles.add(modelFile);
            }

            /*
             * next create the actual definition
             */

            OpticsDefinition opticsDefinition = new OpticsDefinitionImpl(opticsDef.toString(), modelFiles
                    .toArray(new ModelFile[modelFiles.size()]));

            opticsDefinitions.add(opticsDefinition);

        }

        return opticsDefinitions;
    }

    private enum OpticsDef {
        A1100C1100A1000L1000_2011_ATS_INJ(new OpticFileDefinition[] { new OpticFileDefinition(
                "ATS_V6.503/OPTICS_MD2011/opticsfile.1") }), //
        A1100C1100A1000L1000_2011_ATS(new OpticFileDefinition[] { new OpticFileDefinition(
                "ATS_V6.503/OPTICS_MD2011/opticsfile.2") }), //
        A1000C1000A1000L1000_2011_ATS(new OpticFileDefinition[] { new OpticFileDefinition(
                "ATS_V6.503/OPTICS_MD2011/opticsfile.3") }), //
        A800C800A1000L1000_2011_ATS(new OpticFileDefinition[] { new OpticFileDefinition(
                "ATS_V6.503/OPTICS_MD2011/opticsfile.4") }), //
        A600C600A1000L1000_2011_ATS(new OpticFileDefinition[] { new OpticFileDefinition(
                "ATS_V6.503/OPTICS_MD2011/opticsfile.5") }), //
        A440C440A1000L1000_2011_ATS(new OpticFileDefinition[] { new OpticFileDefinition(
                "ATS_V6.503/OPTICS_MD2011/opticsfile.6") }), //
        A300C300A1000L1000_2011_ATS(new OpticFileDefinition[] { new OpticFileDefinition(
                "ATS_V6.503/OPTICS_MD2011/opticsfile.7") }), //
        A240C240A1000L1000_2011_ATS(new OpticFileDefinition[] { new OpticFileDefinition(
                "ATS_V6.503/OPTICS_MD2011/opticsfile.8") }), //
        A200C200A1000L1000_2011_ATS(new OpticFileDefinition[] { new OpticFileDefinition(
                "ATS_V6.503/OPTICS_MD2011/opticsfile.9") }), //
        A160C160A1000L1000_2011_ATS(new OpticFileDefinition[] { new OpticFileDefinition(
                "ATS_V6.503/OPTICS_MD2011/opticsfile.10") }), //
        A120C120A1000L1000_2011_ATS(new OpticFileDefinition[] { new OpticFileDefinition(
                "ATS_V6.503/OPTICS_MD2011/opticsfile.11") }), //
        A117C120A1000L1000_2011_ATS(new OpticFileDefinition[] { new OpticFileDefinition(
                "ATS_V6.503/OPTICS_MD2011/opticsfile.12") }), //
        A114C120A1000L1000_2011_ATS(new OpticFileDefinition[] { new OpticFileDefinition(
                "ATS_V6.503/OPTICS_MD2011/opticsfile.13") }), //
        A111C120A1000L1000_2011_ATS(new OpticFileDefinition[] { new OpticFileDefinition(
                "ATS_V6.503/OPTICS_MD2011/opticsfile.14") }), //
        A109C120A1000L1000_2011_ATS(new OpticFileDefinition[] { new OpticFileDefinition(
                "ATS_V6.503/OPTICS_MD2011/opticsfile.15") }), //
        A108C120A1000L1000_2011_ATS(new OpticFileDefinition[] { new OpticFileDefinition(
                "ATS_V6.503/OPTICS_MD2011/opticsfile.16") }), //
        A105C120A1000L1000_2011_ATS(new OpticFileDefinition[] { new OpticFileDefinition(
                "ATS_V6.503/OPTICS_MD2011/opticsfile.17") }), //
        A94C120A1000L1000_2011_ATS(new OpticFileDefinition[] { new OpticFileDefinition(
                "ATS_V6.503/OPTICS_MD2011/opticsfile.18") }), //
        A84C120A1000L1000_2011_ATS(new OpticFileDefinition[] { new OpticFileDefinition(
                "ATS_V6.503/OPTICS_MD2011/opticsfile.19") }), //
        A72C120A1000L1000_2011_ATS(new OpticFileDefinition[] { new OpticFileDefinition(
                "ATS_V6.503/OPTICS_MD2011/opticsfile.20") }), //
        A60C120A1000L1000_2011_ATS(new OpticFileDefinition[] { new OpticFileDefinition(
                "ATS_V6.503/OPTICS_MD2011/opticsfile.21") }), //
        A54C120A1000L1000_2011_ATS(new OpticFileDefinition[] { new OpticFileDefinition(
                "ATS_V6.503/OPTICS_MD2011/opticsfile.22") }), //
        A48C120A1000L1000_2011_ATS(new OpticFileDefinition[] { new OpticFileDefinition(
                "ATS_V6.503/OPTICS_MD2011/opticsfile.23") }), //
        A42C120A1000L1000_2011_ATS(new OpticFileDefinition[] { new OpticFileDefinition(
                "ATS_V6.503/OPTICS_MD2011/opticsfile.24") }), //
        A36C120A1000L1000_2011_ATS(new OpticFileDefinition[] { new OpticFileDefinition(
                "ATS_V6.503/OPTICS_MD2011/opticsfile.25") }), //
        A30C120A1000L1000_2011_ATS(new OpticFileDefinition[] { new OpticFileDefinition(
                "ATS_V6.503/OPTICS_MD2011/opticsfile.26") }), //
        A1100C1100A1000L1000_2011_ATS_INJ_OLD(new OpticFileDefinition[] {
                new OpticFileDefinition("ATS_V6.503/ats_V6.503.inj.str"),
                new OpticFileDefinition("ATS_V6.503/errors/Presetandknob_B1_inj_450.madx", false, true),
                new OpticFileDefinition("ATS_V6.503/errors/Presetandknob_B2_inj_450.madx", false, true),
                new OpticFileDefinition("tc_re-match_inj.madx", true) });

        private final static OpticFileDefinition[] COMMON_OPTIC_FILES = new OpticFileDefinition[] {};
        private OpticFileDefinition[] opticFileDefinitions;

        private OpticsDef(OpticFileDefinition[] opticFileDefinitions) {
            this.opticFileDefinitions = opticFileDefinitions;
        }

        public List<OpticFileDefinition> getOpticFileDefinitions() {
            List<OpticFileDefinition> opticFiles = new ArrayList<OpticFileDefinition>(Arrays.asList(COMMON_OPTIC_FILES));
            opticFiles.addAll(Arrays.asList(this.opticFileDefinitions));
            return opticFiles;
        }

        @Override
        public String toString() {
            return this.name().replaceAll("x", ".");
        }
    }

    static class OpticFileDefinition {
        private String fileName;
        private boolean isResource = false;
        private boolean isParseStrength = false;

        public OpticFileDefinition(String fileName) {
            this.fileName = fileName;
        }

        public OpticFileDefinition(String fileName, boolean isResource) {
            this(fileName);
            this.isResource = isResource;
        }

        public OpticFileDefinition(String fileName, boolean isResource, boolean isParseStrengths) {
            this(fileName, isResource);
            this.isParseStrength = isParseStrengths;
        }

        public String getFileName() {
            return fileName;
        }

        public boolean isResource() {
            return isResource;
        }

        public boolean isParseStrength() {
            return isParseStrength;
        }
    }

}
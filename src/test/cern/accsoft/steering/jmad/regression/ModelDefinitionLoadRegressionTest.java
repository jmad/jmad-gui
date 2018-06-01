/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package cern.accsoft.steering.jmad.regression;

import static cern.accsoft.steering.jmad.domain.beam.Beam.Direction.MINUS;
import static cern.accsoft.steering.jmad.domain.beam.Beam.Particle.PROTON;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import cern.accsoft.steering.jmad.domain.beam.Beam;
import cern.accsoft.steering.jmad.domain.file.ModelFile;
import cern.accsoft.steering.jmad.domain.machine.SequenceDefinition;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.service.JMadService;
import cern.accsoft.steering.jmad.service.JMadServiceFactory;

/**
 * This test tries to cover against regression bugs while loading the model. This was triggered by a bug in the
 * converter which was used in XStream to convert the beam object and did not load correctly the enums included there.
 * <p>
 * Although this is more or less a copy of a test in the core, we also keep it here, to see if this is also find here,
 * since this was a library problem and might react differently in a dependant project.
 * 
 * @author kfuchsbe
 */
@Ignore
public class ModelDefinitionLoadRegressionTest {

    private static final String LHC_2016_MODEL_NAME = "LHC 2016";
    private static final String LHC_B2_SEQUENCE_NAME = "lhcb2";

    private JMadService jmadService;

    @BeforeClass
    public static void setUpBeforeClass() {
        BasicConfigurator.configure();
    }

    @Before
    public void setUp() {
        jmadService = JMadServiceFactory.createJMadService();
    }

    @Test
    public void particleIsProton() {
        assertThat(lhcB2Beam().getParticle()).isEqualTo(PROTON);
    }

    @Test
    public void directionIsMinus() {
        assertThat(lhcB2Beam().getDirection()).isEqualTo(MINUS);
    }

    @Test
    public void energyIs450GeV() {
        assertThat(lhcB2Beam().getEnergy()).isEqualTo(450.0);
    }

    @Test
    public void initFileLocationsAreNotNull() {
        List<ModelFile> initFiles = lhcModelDefinition().getInitFiles();
        for (ModelFile file : initFiles) {
            assertThat(file.getLocation()).isNotNull();
        }
    }

    private Beam lhcB2Beam() {
        SequenceDefinition sequenceDefinition = lhcModelDefinition().getSequenceDefinition(LHC_B2_SEQUENCE_NAME);
        return sequenceDefinition.getBeam();
    }

    private JMadModelDefinition lhcModelDefinition() {
        return jmadService.getModelDefinitionManager().getModelDefinition(LHC_2016_MODEL_NAME);
    }

}

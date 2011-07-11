package cern.accsoft.steering.jmad.modeldefs.defs.ti8;

import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditionsImpl;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinitionImpl;

public class Ti8ModelDefinition08FactoryBefore0823 extends Ti8ModelDefinition08Factory {

    @Override
    protected void addOpticsDefinitions(JMadModelDefinitionImpl modelDefinition) {
        modelDefinition.addOpticsDefinition(createOpticsDefinition("2007-09-13", "ti8-2007-09-13.str"));
        modelDefinition.setDefaultOpticsDefinition(createOpticsDefinition("2008-05-24 21:25", "ti8-2008-05-24-2125.str"));
    }

    @Override
    protected TwissInitialConditionsImpl createTi8Twiss() {
        TwissInitialConditionsImpl twiss = new TwissInitialConditionsImpl("ti8-twiss");

        /*
         * twiss initial conditions as in 2007: betx= 17.06248574, alfx= 0.4588913839, ! bety=124.8219205,
         * alfy=-3.444554843, ! dx = -0.3287900995, dpx = 0.01234144588;
         */
        twiss.setDeltap(0.000);
        twiss.setBetx(17.06248574);
        twiss.setAlfx(0.4588913839);
        twiss.setDx(-0.3287900995);
        twiss.setDpx(0.01234144588);
        twiss.setBety(124.8219205);
        twiss.setAlfy(-3.444554843);
        twiss.setDy(0.0);
        twiss.setDpy(0.0);

        return twiss;
    }

    @Override
    protected String getName() {
        return "TI8 (2008 before 0823)";
    }

}

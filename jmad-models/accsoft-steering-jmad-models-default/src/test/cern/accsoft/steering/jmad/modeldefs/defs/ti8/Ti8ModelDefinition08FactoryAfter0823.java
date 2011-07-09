package cern.accsoft.steering.jmad.modeldefs.defs.ti8;

import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditionsImpl;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinitionImpl;

public class Ti8ModelDefinition08FactoryAfter0823 extends Ti8ModelDefinition08Factory {

    @Override
    protected void addOpticsDefinitions(JMadModelDefinitionImpl modelDefinition) {
        modelDefinition.addOpticsDefinition(createOpticsDefinition("2007-08-23 19:57", "ti8-2008-08-23-1957.str"));
        modelDefinition.addOpticsDefinition(createOpticsDefinition("2007-08-24 03:27", "ti8-2008-08-24-0327.str"));
        modelDefinition.setDefaultOpticsDefinition(createOpticsDefinition("2008-09-18 10:34", "ti8-2008-09-18-1034.str"));
    }


    /*
     * values from malika !betx = 17.06248574,alfx =0.4588913839, !bety=124.8219205, alfy=-3.444554843, !dx=
     * -0.2527900995,dpx= 0.00334144588; ///model May 2008
     */
    protected final TwissInitialConditionsImpl createTi8Twiss() {
        TwissInitialConditionsImpl twiss = new TwissInitialConditionsImpl("ti8-twiss");

        twiss.setDeltap(0.000);
        twiss.setBetx(17.06248574);
        twiss.setAlfx(0.4588913839);
        twiss.setDx(-0.2527900995);
        twiss.setDpx(0.00334144588);
        twiss.setBety(124.8219205);
        twiss.setAlfy(-3.444554843);
        twiss.setDy(0.0);
        twiss.setDpy(0.0);

        return twiss;
    }

    @Override
    protected String getName() {
        return "TI8 (2008 after 0823)";
    }

}

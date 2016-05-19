package cern.jmad.modeldefs.defs;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditionsImpl;

/**
 * the model definition for med-austron
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class MedAustronNominalMebtModelDefinitionFactory extends AbstractMebtModelFactory {

    @Override
    protected String getModelSuffix() {
        return "nominal";
    }

    @Override
    protected List<String> getQuadStrengthFileNames() {
        List<String> names = new ArrayList<>();
        names.add("me");
        return names;
    }

    /**
     * Creates the initial conditions as given by A. Wastl, (2014-22-05):
     * <ul>
     * <li>me.betx := 0.2; // default: 0.2
     * <li>me.alfx := 0; // default: 0
     * <li>me.dx := 0; // default: 0
     * <li>me.dpx := 0; // default: 0
     * <li>me.bety := 0.2; // default: 0.2
     * <li>me.alfy := 0; // default: 0
     * <li>me.dy := 0; // default: 0
     * <li>me.dpy := 0; // default: 0
     * </ul>
     */
    @Override
    protected TwissInitialConditionsImpl createInititalConditions() {
        TwissInitialConditionsImpl inititalConditions = new TwissInitialConditionsImpl();
        inititalConditions.setBetx(0.2);
        inititalConditions.setAlfx(0.0);
        inititalConditions.setDx(0.0);
        inititalConditions.setDpx(0.0);

        inititalConditions.setBety(0.2);
        inititalConditions.setAlfy(0.0);
        inititalConditions.setDy(0.0);
        inititalConditions.setDpy(0.0);
        return inititalConditions;
    }

}

package cern.jmad.modeldefs.defs;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditionsImpl;

/**
 * the model definition for med-austron
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class MedAustronMebtVar3ModelDefinitionFactory extends AbstractMebtModelFactory {

    @Override
    protected String getModelSuffix() {
        return "var3";
    }

    @Override
    protected List<String> getQuadStrengthFileNames() {
        List<String> names = new ArrayList<>();
        names.add("me-var3-ver0-awastl");
        names.add("me-var3-ver0-aga");
        names.add("me-var3-ver1");
        return names;
    }

    /**
     * Creates the initial conditions as given by A. Wastl, (2014-22-05):
     * <ul>
     * <li>me.betx := 0.6; // default: 0.2
     * <li>me.alfx := 0; // default: 0
     * <li>me.dx := 0; // default: 0
     * <li>me.dpx := 0; // default: 0
     * <li>me.bety := 0.7; // default: 0.2
     * <li>me.alfy := 0; // default: 0
     * <li>me.dy := 0; // default: 0
     * <li>me.dpy := 0; // default: 0
     * </ul>
     */
    @Override
    protected TwissInitialConditionsImpl createInititalConditions() {
        TwissInitialConditionsImpl inititalConditions = new TwissInitialConditionsImpl();
        inititalConditions.setBetx(0.6);
        inititalConditions.setAlfx(0.0);
        inititalConditions.setDx(0.0);
        inititalConditions.setDpx(0.0);

        inititalConditions.setBety(0.7);
        inititalConditions.setAlfy(0.0);
        inititalConditions.setDy(0.0);
        inititalConditions.setDpy(0.0);
        return inititalConditions;
    }

}

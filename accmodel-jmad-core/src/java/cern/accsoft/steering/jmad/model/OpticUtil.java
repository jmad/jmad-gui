package cern.accsoft.steering.jmad.model;

import java.util.List;

import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.domain.optics.Optic;
import cern.accsoft.steering.jmad.domain.optics.OpticImpl;
import cern.accsoft.steering.jmad.domain.optics.OpticPoint;
import cern.accsoft.steering.jmad.domain.optics.OpticPointImpl;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsResult;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsResultRequestImpl;
import cern.accsoft.steering.jmad.domain.var.enums.MadxTwissVariable;

/**
 * collectiion of utility methods for the Model
 * 
 * @author kaifox
 */
public final class OpticUtil {

    private OpticUtil() {
        /* only static methods */
    }

    public static Optic calcOptic(JMadModel model) throws JMadModelException {
        /* request all elements */
        TfsResultRequestImpl resultRequest = new TfsResultRequestImpl();
        resultRequest.addElementFilter(".*");

        /*
         * request optics variables both planes.
         */
        List<MadxTwissVariable> variables = OpticPointImpl.MADX_VARIABLES;
        resultRequest.addVariable(MadxTwissVariable.NAME);
        for (MadxTwissVariable var : variables) {
            resultRequest.addVariable(var);
        }

        /*
         * perform the twiss
         */
        TfsResult tfsResult = model.twiss(resultRequest);
        
        /*
         * create and return the Optic
         */
        return createOptic(tfsResult, variables.toArray(new MadxTwissVariable[variables.size()]));
    }

    public static Optic createOptic(TfsResult tfsResult, MadxTwissVariable... variables) {
        OpticImpl optic = new OpticImpl();

        /*
         * store the values from the twiss result
         */
        for (MadxTwissVariable var : variables) {
            optic.add(var, tfsResult.getDoubleData(var));
        }

        /*
         * get the names and check, if the size is still the same.
         */
        List<String> names = tfsResult.getStringData(MadxTwissVariable.NAME);
        optic.setNames(names);

        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            OpticPoint point = new OpticPointImpl(name);
            for (MadxTwissVariable var : variables) {
                ((OpticPointImpl) point).setValue(var, optic.getAllValues(var).get(i));
            }
            optic.add(point);
        }

        return optic;
    }
}
/*
 * $Id $
 * 
 * $Date$ $Revision$ $Author$
 * 
 * Copyright CERN ${year}, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.modeldefs.lhc;

import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.domain.machine.RangeDefinition;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.util.acc.BeamNumber;

public enum ModelUtil {
    ;

    /**
     * Load the correct full range for the provided beam in the given model. Currently this functionality is intended
     * for the LHC only, one has to think about the usability and implementation, if there are other machines that
     * should be supported
     * 
     * @param model
     * @param beam
     * @throws JMadModelException
     */
    public static final void loadBeam(JMadModel model, BeamNumber beam) throws JMadModelException {
        String sequenceName = LhcUtil.getSequenceName(beam);
        for (RangeDefinition definition : model.getModelDefinition().getRangeDefinitions()) {
            if (definition.getName().compareTo(LhcUtil.DEFAULT_RANGE_NAME) != 0) {
                continue;
            }

            if (definition.getSequenceDefinition().getName().compareTo(sequenceName) == 0) {
                model.setActiveRangeDefinition(definition);
                return;
            }
        }

        throw new IllegalArgumentException("can not set model to beam " + beam + " defined be sequence ["
                + sequenceName + "] and range [" + LhcUtil.DEFAULT_RANGE_NAME + "]");
    }
}

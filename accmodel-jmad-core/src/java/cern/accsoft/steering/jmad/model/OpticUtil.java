// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 * 
 * JMad is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JMad is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JMad.  If not, see <http://www.gnu.org/licenses/>.
 * 
 ******************************************************************************/
// @formatter:on
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

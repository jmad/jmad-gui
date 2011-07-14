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
/**
 * 
 */
package cern.accsoft.steering.jmad.domain.twiss;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditions;
import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditionsImpl;
import cern.accsoft.steering.jmad.domain.twiss.TwissListener;

/**
 * @author kfuchsbe
 */
public class TwissInitialConditionsTest {

    @Test
    public void testCloneTwissInitialConditions() {

        /*
         * first we create an objecto for out purpose
         */
        TwissInitialConditionsImpl twiss = new TwissInitialConditionsImpl();
        twiss.setBetx(1.1);
        twiss.setBety(2.2);
        TwissListener listener = new TwissListener() {
            @Override
            public void changedTwiss(TwissInitialConditions changedTwiss) {
                /* do nothing, just for testing */
            }
        };
        twiss.addListener(listener);
        assertEquals(1, twiss.getListeners().size());

        /*
         * then we clone it and check if everything is ok
         */
        TwissInitialConditionsImpl clonedTwiss = twiss.clone();
        assertFalse("Must be a different object", twiss == clonedTwiss);
        assertEquals("Listeners must not be cloned", 0, clonedTwiss.getListeners().size());

        double betx = clonedTwiss.getBetx();
        assertEquals(1.1, betx, 0.0001);

        double bety = clonedTwiss.getBety();
        assertEquals(2.2, bety, 0.0001);
    }
}

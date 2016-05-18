/*
 * $Id $
 * 
 * $Date$ $Revision$ $Author$
 * 
 * Copyright CERN ${year}, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.util.xml;

import junit.framework.Assert;

import org.junit.Test;

import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditionsImpl;
import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditionsXmlConverter;

/**
 * @author ${user}
 * @version $Revision$, $Date$, $Author$
 */
public class CloneUtilTest {

    @Test
    public void testCloneTwissInitialConditions() {
        TwissInitialConditionsImpl original = new TwissInitialConditionsImpl();
        original.setClosedOrbit(true);
        Assert.assertTrue("closed orbit MUST be set", original.isClosedOrbit());
        original.setCalcChromaticFunctions(true);
        Assert.assertTrue("chroma MUST be set", original.isCalcChromaticFunctions());
        original.setCalcAtCenter(true);
        Assert.assertTrue("center MUST be set", original.isCalcAtCenter());

        TwissInitialConditionsImpl clone = CloneUtil.clone(TwissInitialConditionsImpl.class, original,
                new TwissInitialConditionsXmlConverter());

        Assert.assertTrue("center property MUST be equal", original.isCalcAtCenter() == clone.isCalcAtCenter());
        Assert.assertTrue("chroma property MUST be equal", original.isCalcChromaticFunctions() == clone
                .isCalcChromaticFunctions());
        Assert.assertTrue("closed orbit property MUST be equal", original.isClosedOrbit() == clone.isClosedOrbit());
    }
}

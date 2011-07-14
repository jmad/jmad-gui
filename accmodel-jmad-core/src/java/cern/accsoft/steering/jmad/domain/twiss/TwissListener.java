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
/*
 * $Id: TwissListener.java,v 1.1 2009-01-20 19:43:11 kfuchsbe Exp $
 * 
 * $Date: 2009-01-20 19:43:11 $ $Revision: 1.1 $ $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.domain.twiss;

/**
 * this interface contains methods, which are fired e.g. when values of the twiss change.
 * 
 * @author kfuchsbe
 */
public interface TwissListener {

    /**
     * fired, when values of the twiss changed.
     * 
     * @param twiss the twiss-object whose values changed.
     */
    public void changedTwiss(TwissInitialConditions twiss);
}

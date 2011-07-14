// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, Kajetan Fuchsberger. All rights reserved.
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
 * $Id: NameFilter.java,v 1.2 2008-08-25 13:52:12 kfuchsbe Exp $
 * 
 * $Date: 2008-08-25 13:52:12 $ $Revision: 1.2 $ $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.domain.machine.filter;

import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;

/**
 * simple interface to define e filter (e.g. for elements)
 * 
 * @author kfuchsbe
 */
public interface NameFilter {

    /**
     * @param name the name to check
     * @param plane the plane to check
     * @return <code>true</code> if the name is concerned by the filter in the given plane, <code>false</code>otherwise.
     */
    boolean isConcerned(String name, JMadPlane plane);
}

// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public interface NameFilter {

    /**
     * @param name the name to check
     * @param plane the plane to check
     * @return <code>true</code> if the name is concerned by the filter in the given plane, <code>false</code>otherwise.
     */
    boolean isConcerned(String name, JMadPlane plane);
}

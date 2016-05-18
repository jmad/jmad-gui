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
 * $Id: RangeListener.java,v 1.1 2009-01-15 11:46:26 kfuchsbe Exp $
 * 
 * $Date: 2009-01-15 11:46:26 $ $Revision: 1.1 $ $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.domain.machine;

import java.util.List;

import cern.accsoft.steering.jmad.domain.misalign.MisalignmentConfiguration;

/**
 * this interface defines methods, which are fired, when something in a range changes.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public interface RangeListener {

    /**
     * fired, when a misalignment is added or removed.
     * 
     * @param misalignmentConfiguration the misalignement configuration which was added.
     */
    @Deprecated
    public void addedMisalignments(MisalignmentConfiguration misalignmentConfiguration);

    /**
     * fired when a misalignment is added or removed.
     * 
     * @param misalignmentConfigurations
     */
    public void addedMisalignments(List<MisalignmentConfiguration> misalignmentConfigurations);

}

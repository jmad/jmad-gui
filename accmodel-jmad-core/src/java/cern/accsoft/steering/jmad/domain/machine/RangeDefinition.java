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

package cern.accsoft.steering.jmad.domain.machine;

import java.util.List;

import cern.accsoft.steering.jmad.domain.file.ModelFile;
import cern.accsoft.steering.jmad.domain.file.ModelFileDependant;
import cern.accsoft.steering.jmad.domain.machine.filter.NameFilter;
import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditionsImpl;

public interface RangeDefinition extends ModelFileDependant {

    /**
     * @return the name
     */
    public abstract String getName();

    /**
     * @return the range for madx
     */
    public MadxRange getMadxRange();

    /**
     * If this is non-null, then the sequence is rotated to start at the element of the given name before use.
     * 
     * @return the name of an element or <code>null</code>.
     */
    public String getStartElementName();

    /**
     * @return the twiss
     */
    public abstract TwissInitialConditionsImpl getTwiss();

    /**
     * @return the monitorInvertFilters
     */
    public abstract List<NameFilter> getMonitorInvertFilters();

    /**
     * @return the correctorInvertFilters
     */
    public abstract List<NameFilter> getCorrectorInvertFilters();

    /**
     * @return the postUseFiles
     */
    public abstract List<ModelFile> getPostUseFiles();

    /**
     * @return the aperture-definition of this range
     */
    public abstract ApertureDefinition getApertureDefinition();

    /**
     * @return the sequenceDefinition this range belongs to
     */
    public abstract SequenceDefinition getSequenceDefinition();

}

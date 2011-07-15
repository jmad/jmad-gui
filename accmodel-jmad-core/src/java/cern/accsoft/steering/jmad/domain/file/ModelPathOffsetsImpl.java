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

/**
 * 
 */
package cern.accsoft.steering.jmad.domain.file;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
@XStreamAlias("path-offsets")
public class ModelPathOffsetsImpl implements ModelPathOffsets {

    @XStreamAlias("repository-offset")
    private String repositoryOffset = null;

    @XStreamAlias("resource-offset")
    private String resourceOffset = null;

    @Override
    public String getRepositoryOffset() {
        return this.repositoryOffset;
    }

    @Override
    public String getResourceOffset() {
        return this.resourceOffset;
    }

    public void setResourceOffset(String resourceOffset) {
        this.resourceOffset = resourceOffset;
    }

    public void setRepositoryOffset(String repositoryOffset) {
        this.repositoryOffset = repositoryOffset;
    }

}

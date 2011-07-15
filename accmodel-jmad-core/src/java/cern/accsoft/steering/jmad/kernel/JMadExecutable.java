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

package cern.accsoft.steering.jmad.kernel;

import java.io.File;

import cern.accsoft.steering.jmad.domain.result.ResultType;

public interface JMadExecutable {

    /**
     * returns a string which will be sent to madx in order to accomplish some Task in MadX.
     * 
     * @return the composed string for this executable that can be sent to MadX
     */
    public abstract String compose();

    public abstract ResultType getResultType();

    public abstract void setOutputFile(File outputFile);

    public abstract File getOutputFile();

}

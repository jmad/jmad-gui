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

/**
 * Contains common functions of commands/tasks that can be executed in MadX.
 * 
 * @author kfuchsbe
 */
public abstract class AbstractJMadExecutable implements JMadExecutable {

    /** The output-file, which can be used in the compose method. */
    private File outputFile = null;

    /**
     * The {@link ResultType} which is produced by this command. This value is then used by the kernel, in order to
     * determine which parser to use to determine the type of parser to use for parsing the madx-output.
     * <p>
     * Shall be overridden by subclass, if it provides a result.
     * 
     * @return the result type for this executable
     */
    @Override
    public ResultType getResultType() {
        return ResultType.NO_RESULT;
    }

    /**
     * set the output-file. This should be only done by the {@link JMadKernel} since the kernel is the only one who
     * knows where the output is needed to parse it afterwards.
     * 
     * @param outputFile the new output file to set
     */
    public final void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    /**
     * the actual output-file.
     * 
     * @return the file to which the output shall be written
     */
    public final File getOutputFile() {
        return outputFile;
    }

}

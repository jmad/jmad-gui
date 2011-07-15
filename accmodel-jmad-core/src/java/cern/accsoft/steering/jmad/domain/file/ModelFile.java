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

import cern.accsoft.steering.jmad.modeldefs.io.ModelFileFinder;

/**
 * This interface represents the description of a file used for a model and provides the information where to find it.
 * 
 * @author kfuchsbe
 */
public interface ModelFile {

    /**
     * Where to search the file? In the repository (or if not found there in the repo-copy within the jar) or in the
     * sourcepath
     * 
     * @author kfuchsbe
     */
    public static enum ModelFileLocation {
        REPOSITORY("repdata") {
            @Override
            public String getPathOffset(ModelPathOffsets offsets) {
                return offsets.getRepositoryOffset();
            }
        },
        RESOURCE("resdata") {
            @Override
            public String getPathOffset(ModelPathOffsets offsets) {
                return offsets.getResourceOffset();
            }
        };

        /** The path prefix within the jar/zip/source */
        private String resourcePrefix;

        public abstract String getPathOffset(ModelPathOffsets offsets);

        private ModelFileLocation(String resourcePrefix) {
            this.resourcePrefix = resourcePrefix;
        }

        public String getResourcePrefix() {
            return resourcePrefix;
        }
    }

    /**
     * @return the name used by the {@link ModelFileFinder} to find the file.
     */
    public abstract String getName();

    /**
     * @return the location where to search for the file.
     */
    public abstract ModelFileLocation getLocation();

}

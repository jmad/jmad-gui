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

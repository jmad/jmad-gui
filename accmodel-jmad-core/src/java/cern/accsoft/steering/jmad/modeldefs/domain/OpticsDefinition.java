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
package cern.accsoft.steering.jmad.modeldefs.domain;

import java.util.List;

import cern.accsoft.steering.jmad.domain.file.ModelFile;
import cern.accsoft.steering.jmad.domain.file.ModelFileDependant;

/**
 * This is the interface for definitions of optics.
 * 
 * @author kfuchsbe
 */
public interface OpticsDefinition extends ModelFileDependant {

    /**
     * @return the name of the optics
     */
    public abstract String getName();

    /**
     * @return the {@link ModelFile}s to call for initializing this optics
     */
    public abstract List<ModelFile> getInitFiles();

    /**
     * @return a list of model files that must be called after switching to ptc
     */
    public abstract List<ModelFile> getPostPtcUniverseFiles();

    /**
     * convenience methods to get all the relative pathnames of the required files
     * 
     * @return a list of FileNames for all ModelFiles this Optic consists of
     */
    public abstract String[] getOpticFileNames();

    /**
     * @return <code>true</code> if this optics can be loaded together with another optics. <code>false</code> if it is
     *         a full optics definition which in most cases should override all strengths.
     */
    public abstract boolean isOverlay();
}

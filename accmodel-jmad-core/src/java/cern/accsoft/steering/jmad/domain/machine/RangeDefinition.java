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
	 * If this is non-null, then the sequence is rotated to start at the element
	 * of the given name before use.
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

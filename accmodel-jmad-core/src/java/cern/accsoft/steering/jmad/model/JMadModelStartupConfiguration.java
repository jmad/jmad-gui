// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, Kajetan Fuchsberger. All rights reserved.
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
package cern.accsoft.steering.jmad.model;

import cern.accsoft.steering.jmad.domain.machine.RangeDefinition;
import cern.accsoft.steering.jmad.modeldefs.domain.OpticsDefinition;

public class JMadModelStartupConfiguration {

	private boolean loadDefaultOptics = true;
	private boolean loadDefaultRange = true;
	private OpticsDefinition initialOpticsDefinition = null;
	private RangeDefinition initialRangeDefinition = null;

	/**
	 * @return true, if the default optics shall be loaded on startup
	 */
	public boolean isLoadDefaultOptics() {
		return this.loadDefaultOptics;
	}

	/**
	 * @return true, if the default range shall be loaded on startup
	 */
	public boolean isLoadDefaultRange() {
		return this.loadDefaultRange;
	}

	/**
	 * If {@link #isLoadDefaultRange()} returns false and this returns non null
	 * then the range-definition returned by this method is used to start the
	 * model. If this is null, then no range is loaded (which is not advisable
	 * since it results in an unusable model.).
	 * 
	 * @return a valid range definition or null
	 */
	public RangeDefinition getInitialRangeDefinition() {
		return this.initialRangeDefinition;
	}

	/**
	 * If {@link #isLoadDefaultOptics()} returns false then the
	 * {@link OpticsDefinition} returned by this method is loaded when
	 * initializing the model. If this is null then no optics is loaded (which
	 * is not advisable, since it results in an unusable model)
	 * 
	 * @return a valid optics definition
	 */
	public OpticsDefinition getInitialOpticsDefinition() {
		return this.initialOpticsDefinition;
	}

	/*
	 * setters
	 */

	public void setLoadDefaultOptics(boolean loadDefaultOptics) {
		this.loadDefaultOptics = loadDefaultOptics;
	}

	public void setLoadDefaultRange(boolean loadDefaultRange) {
		this.loadDefaultRange = loadDefaultRange;
	}

	public void setInitialOpticsDefinition(
			OpticsDefinition initialOpticsDefinition) {
		this.initialOpticsDefinition = initialOpticsDefinition;
	}

	public void setInitialRangeDefinition(RangeDefinition initialRangeDefinition) {
		this.initialRangeDefinition = initialRangeDefinition;
	}
}

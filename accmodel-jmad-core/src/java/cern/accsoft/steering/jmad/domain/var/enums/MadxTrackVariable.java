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
package cern.accsoft.steering.jmad.domain.var.enums;

import cern.accsoft.steering.jmad.domain.var.TrackVariable;
import cern.accsoft.steering.jmad.domain.var.VariableUtil;
import cern.accsoft.steering.jmad.util.MadxVarType;

/**
 * 
 * @author xbuffat
 *
 */

public enum MadxTrackVariable implements TrackVariable {
	NUMBER("number","1"),
	TURN("turn","1"),
	X("x","m"),
	PX("px","1"),
	Y("y","m"),
	PY("py","1"),
	T("t","m"),
	PT("pt","1"),
	S("s","m"),
	E("e","1"),
	
	UNKNOWN("unknown","1");

	
	private String name;
	private String unit;
	private MadxVarType type = MadxVarType.DOUBLE;
	
	private MadxTrackVariable(String name, String unit) {
		this.name = name;
		this.unit = unit;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getUnit() {
		return this.unit;
	}

	@Override
	public Class<?> getValueClass() {
		return this.getVarType().getValueClass();
	}

	@Override
	public String getMadxName() {
		return this.getName();
	}

	@Override
	public MadxVarType getVarType() {
		return this.type;
	}

	public static MadxTrackVariable getVariableFromName(String name) {
		return VariableUtil.findFromMadxName(MadxTrackVariable.class, name , MadxTrackVariable.UNKNOWN);
	}

}

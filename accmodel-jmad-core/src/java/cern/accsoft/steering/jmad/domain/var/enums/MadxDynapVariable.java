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

import cern.accsoft.steering.jmad.domain.var.DynapVariable;
import cern.accsoft.steering.jmad.domain.var.VariableUtil;
import cern.accsoft.steering.jmad.util.MadxVarType;

/**
 * 
 * @author xbuffat
 *
 */

public enum MadxDynapVariable implements DynapVariable {

	//standard dynap
	DYNAPFRAC("dynapfrac","1"),
	DKTRTURNS("dktrturns","1"),
	XEND("xend","m"),
	PXEND("pxend","1"),
	YEND("yend","m"),
	PYEND("pyend","1"),
	TEND("tend","m"),
	WXMIN("wxmin","1"),
	WXMAX("wxmax","1"),
	WYMIN("wymin","1"),
	WYMAX("wymax","1"),
	WXYMIN("wxymin","1"),
	WXYMAX("wxymax","1"),
	SMEAR("smear","1"),
	YAPUNOV("yapunov","1"),
	
	//dynaptune
	X("x","m"),
	Y("y","m"),
	TUNX("tunx","1"),
	TUNY("tuny","1"),
	DTUNE("dtune","1"),
	
	UNKNOWN("unknown","1");

	private String name;
	private String unit;
	private MadxVarType type = MadxVarType.DOUBLE;
	
	private MadxDynapVariable(String name,String unit) {
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
		return this.type.getValueClass();
	}
	
	public static MadxDynapVariable getVariableFromName(String name) {
		return VariableUtil.findFromMadxName(MadxDynapVariable.class, name, MadxDynapVariable.UNKNOWN);
		
	}

	@Override
	public String getMadxName() {
		return this.getName();
	}

	@Override
	public MadxVarType getVarType() {
		return this.type;
	}

}

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

package cern.accsoft.steering.jmad.domain.var.enums;

import cern.accsoft.steering.jmad.domain.var.TrackVariable;
import cern.accsoft.steering.jmad.domain.var.VariableUtil;
import cern.accsoft.steering.jmad.util.MadxVarType;

/**
 * @author xbuffat
 */

public enum MadxTrackVariable implements TrackVariable {
    NUMBER("number", "1"), TURN("turn", "1"), X("x", "m"), PX("px", "1"), Y("y", "m"), PY("py", "1"), T("t", "m"), PT(
            "pt", "1"), S("s", "m"), E("e", "1"),

    UNKNOWN("unknown", "1");

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
        return VariableUtil.findFromMadxName(MadxTrackVariable.class, name, MadxTrackVariable.UNKNOWN);
    }

}

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
package cern.accsoft.steering.jmad.kernel.cmd.param;

import cern.accsoft.steering.jmad.domain.types.MadxValue;

public class GenericParameter<T> extends AbstractParameter {
    private String name;
    private T value;

    private boolean useValueQuotes = false;

    public GenericParameter(String name) {
        this(name, null, false);
    }

    public GenericParameter(String name, T value) {
        this(name, value, false);
    }

    public GenericParameter(String name, T value, boolean useValueQuotes) {
        this.name = name;
        this.value = value;
        this.useValueQuotes = useValueQuotes;
    }

    public boolean isSet() {
        if ((value != null) && (value.getClass().equals(Boolean.class))) {
            return (Boolean) value;
        } else {
            return (value != null);
        }
    }

    @Override
    public String compose() {
        String valueString;

        if (value.getClass().equals(Boolean.class)) {
            if ((Boolean) value) {
                return name;
            } else {
                return "";
            }
        } else if (value instanceof MadxValue) {
            return name + "=" + ((MadxValue) value).getMadxString();
        } else {
            if (useValueQuotes) {
                valueString = "\"" + value.toString() + "\"";
            } else {
                valueString = value.toString();
            }
            return name + "=" + valueString;
        }
    }

}

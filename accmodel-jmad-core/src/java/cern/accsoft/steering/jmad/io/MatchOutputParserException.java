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
package cern.accsoft.steering.jmad.io;

import cern.accsoft.steering.jmad.JMadException;

public class MatchOutputParserException extends JMadException {
    private static final long serialVersionUID = 3607954290650559188L;

    public MatchOutputParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public MatchOutputParserException(Throwable cause) {
        super(cause);
    }

    public MatchOutputParserException(String message) {
        super(message);
    }

}

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

package cern.accsoft.steering.jmad.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.domain.knob.strength.SimpleStrength;
import cern.accsoft.steering.jmad.domain.knob.strength.Strength;
import cern.accsoft.steering.jmad.domain.result.StrengthResult;
import cern.accsoft.steering.jmad.domain.var.custom.CustomVariable;
import cern.accsoft.steering.jmad.domain.var.custom.CustomVariableImpl;

public class StrengthFileParserNew {
    private static final Logger LOGGER = Logger.getLogger(StrengthFileParserNew.class);

    /** The strength-file to parse */
    private final File file;

    /**
     * The initial values for a Sequence that will be read from the Strength file
     */
    private final List<Strength> strengths = new ArrayList<Strength>();

    /**
     * The variables, which are retrieved from the Strengthfile. (Actually these are name-value pairs, where the right
     * side of the equal sign could not be parsed to a double value.
     */
    private final List<CustomVariable> variables = new ArrayList<CustomVariable>();

    public StrengthFileParserNew(File file) {
        this.file = file;
    }

    public void parse() throws StrengthFileParserException {

        List<String> statements;
        try {
            statements = MadStatementParser.parse(file);
        } catch (IOException e) {
            throw new StrengthFileParserException("Error while parsing MadX-Strength file '" + file.getAbsolutePath()
                    + "'.", e);
        }

        strengths.clear();

        for (String statement : statements) {

            /* first we want to extract possible endline - comments */
            String[] parts = statement.split("(!|//)");
            if (parts.length < 1) {
                throw new StrengthFileParserException("Unable to interpret line '" + statement + "'");
            }
            String comment = null;
            if (parts.length > 1) {
                comment = parts[1].trim(); // if there are additional comment
                // signs, they will be ignored.
            }

            if (parts[0].toLowerCase().startsWith("return")) {
                break; // return should be last line in file.
            }

            /* next we split at the equal sign in order to get name and value */
            String[] tokens = parts[0].trim().split("=");
            if (tokens.length != 2) {
                LOGGER.warn("Unable to interpret line '" + statement + "' (maybe no '=' or too many of them?)");
                continue; // -> skip
            }

            String name = null;
            String value = null;
            boolean lateAssigned = false;

            if (tokens[0].endsWith(":")) {
                lateAssigned = true;
                name = tokens[0].substring(0, tokens[0].length() - 1).trim();
            } else {
                name = tokens[0].trim();
            }

            if (!tokens[1].endsWith(";")) {
                throw new StrengthFileParserException("Line does not seem to end with an ';' : '" + statement + "'");
            }

            value = tokens[1].substring(0, tokens[1].length() - 1).trim();

            Double doubleValue = null;
            try {
                doubleValue = Double.parseDouble(value);
            } catch (NumberFormatException e) {
                LOGGER.debug("Unable to convert String '" + value + "' to double. -> is treated as variable.");
            }

            if (doubleValue == null) {
                CustomVariable variable = new CustomVariableImpl(name, value, comment, lateAssigned);
                this.variables.add(variable);
            } else {
                Strength strength = new SimpleStrength(name, doubleValue, comment);
                this.strengths.add(strength);
            }
        }
    }

    public List<Strength> getStrengths() {
        return strengths;
    }

    public StrengthResult getResult() {
        return new StrengthResult(strengths);
    }

    public List<CustomVariable> getVariables() {
        return this.variables;
    }
}

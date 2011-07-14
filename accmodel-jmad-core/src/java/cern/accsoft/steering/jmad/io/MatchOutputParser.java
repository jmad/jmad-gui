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
package cern.accsoft.steering.jmad.io;

import java.io.File;
import java.util.List;

import cern.accsoft.steering.jmad.domain.result.match.MatchResultImpl;
import cern.accsoft.steering.jmad.domain.result.match.output.MadxVaryResultImpl;
import cern.accsoft.steering.jmad.domain.result.match.output.MatchConstraintResultGlobal;
import cern.accsoft.steering.jmad.domain.result.match.output.MatchConstraintResultLocal;
import cern.accsoft.steering.jmad.kernel.task.RunMatch;
import cern.accsoft.steering.util.io.TextFileParser;
import cern.accsoft.steering.util.io.TextFileParserException;
import cern.accsoft.steering.util.io.impl.TextFileParserImpl;

/**
 * This class provides an interface to read MadX Matching-Output. The read data is returned in a Result - Object.
 * 
 * @author muellerg
 */
public class MatchOutputParser {

    public static enum MatchingOutputTag {
        // madx internal tag for the final Penalty function value //
        // updated after each Matching Command
        FINAL_PENALTY("TAR"), //

        LOCAL_CONSTRAINTS("Local Constraints:"), //
        GLOBAL_CONSTAINTS("Global Constraints:"), //

        VARY_PARAMETERS("Vary Parameter Results:"), //

        // Should never Occure...
        NONE("not set");

        private String name;

        private MatchingOutputTag(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public static MatchingOutputTag getTagForLine(String line) {
            for (MatchingOutputTag tag : values()) {
                if (line.equals(tag.toString())) {
                    return tag;
                }
            }

            return null;
        }
    }

    /* The file from where to read */
    private File outPut = null;

    /* The result - object, where all read Data will be stored. */
    private MatchResultImpl result = null;

    /**
     * @param file the file to parse.
     */
    public MatchOutputParser(File file) {
        super();
        this.outPut = file;
    }

    /**
     * Parses the file and stores the data in internal Variables.
     * 
     * @throws MatchOutputParserException if the parsing fails
     */
    public void parse() throws MatchOutputParserException {

        TextFileParser parser = new TextFileParserImpl();

        List<String> lines;
        try {
            lines = parser.parse(this.outPut);
        } catch (TextFileParserException e) {
            throw new MatchOutputParserException("Error while parsing MadX-Matching Result file '"
                    + this.outPut.getAbsolutePath() + "'.", e);
        }

        if (!lines.get(0).equals(MatchingOutputTag.FINAL_PENALTY.toString())) {
            throw new MatchOutputParserException("Missing Final Penalty Function Value "
                    + "at the beginning of Matching Output");
        }

        double actValue = Double.NaN;

        try {
            actValue = getValue(lines.get(1)) / RunMatch.FINAL_PENALTY_FACTOR;
            this.result = new MatchResultImpl(actValue);
        } catch (Exception ex) {
            throw new MatchOutputParserException("Error retrieving Final Penalty Function Value "
                    + "from Matching Output", ex);
        }

        MatchingOutputTag actTag = MatchingOutputTag.NONE;
        MatchingOutputTag newTag = null;
        String actVarName = null;

        for (String line : lines.subList(2, lines.size())) {

            newTag = MatchingOutputTag.getTagForLine(line);
            if (newTag != null) {
                actTag = newTag;
                continue;
            }

            actValue = getValue(line);
            actVarName = line.split("=")[0].trim();

            switch (actTag) {
            case LOCAL_CONSTRAINTS:
                this.result.addConstrainParameterResult(new MatchConstraintResultLocal(actVarName, actValue));
                break;
            case GLOBAL_CONSTAINTS:
                this.result.addConstrainParameterResult(new MatchConstraintResultGlobal(actVarName, actValue));
                break;
            case VARY_PARAMETERS:
                this.result.addVaryParameterResult(new MadxVaryResultImpl(actVarName, actValue));
                break;
            default:
                throw new MatchOutputParserException("Error while parsing Matching Output "
                        + "--> parse line without defined Tag");
            }
        }
    }

    private static double getValue(String line) throws MatchOutputParserException {

        String[] tokens = line.split("=");
        if (tokens.length < 2) {
            throw new MatchOutputParserException("Could not interpret Matching Output (Value) Line --> " + line);
        }

        String actValueString = tokens[1].replace(";", "").trim();
        double actValue = Double.NaN;

        try {
            actValue = Double.parseDouble(actValueString);
        } catch (Exception ex) {
            throw new MatchOutputParserException("Could not convert Value [" + actValueString + "] to double.", ex);
        }

        return actValue;
    }

    public MatchResultImpl getResult() {
        return result;
    }
}

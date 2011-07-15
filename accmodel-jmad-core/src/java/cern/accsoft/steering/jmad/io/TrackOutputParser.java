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
import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.result.track.TrackResult;
import cern.accsoft.steering.jmad.domain.result.track.TrackResultImpl;
import cern.accsoft.steering.jmad.domain.var.enums.MadxTrackVariable;
import cern.accsoft.steering.util.io.TextFileParser;
import cern.accsoft.steering.util.io.TextFileParserException;
import cern.accsoft.steering.util.io.impl.TextFileParserImpl;

/**
 * This class provide methods to parse a track output.
 * 
 * @author xbuffat
 */

public class TrackOutputParser {

    private TrackResult trackResult;
    private File file;

    public TrackOutputParser(File file) {
        this.trackResult = new TrackResultImpl();
        this.file = file;
    }

    public void parse() throws TrackParserException {
        TextFileParser parser = new TextFileParserImpl();

        List<String> lines;
        try {
            lines = parser.parse(file);
        } catch (TextFileParserException e) {
            throw new TrackParserException("Error while parsing MadX - Output file '" + file.getAbsolutePath() + "'", e);
        }

        this.trackResult.clear();

        List<MadxTrackVariable> keys = null;
        int numberIndex = 0;
        for (int i = 0; i < lines.size(); ++i) {
            String line = lines.get(i);
            if (!line.contains("@") && !line.contains("#segment")) {
                List<String> tokens = null;
                try {
                    tokens = TfsFileParser.splitString(line);
                } catch (TfsFileParserException e) {
                    throw new TrackParserException("Error while spliting line", e);
                }
                if (tokens.get(0).equalsIgnoreCase("*")) {
                    keys = this.generateKeys(tokens.subList(1, tokens.size()));
                    numberIndex = keys.indexOf(MadxTrackVariable.NUMBER);
                } else if (keys != null && !tokens.get(0).equalsIgnoreCase("$")) {
                    Integer particle = Integer.parseInt(tokens.get(numberIndex));
                    for (int j = 0; j < tokens.size(); ++j) {
                        if (j != numberIndex) {
                            this.trackResult.add(keys.get(j), particle, Double.parseDouble(tokens.get(j)));
                        }
                    }
                }
            }
        }
    }

    private List<MadxTrackVariable> generateKeys(List<String> subList) {
        List<MadxTrackVariable> retVal = new ArrayList<MadxTrackVariable>();
        for (int i = 0; i < subList.size(); ++i) {
            retVal.add(MadxTrackVariable.getVariableFromName(subList.get(i)));
        }
        return retVal;
    }

    public TrackResult getResult() {
        return this.trackResult;
    }
}

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
import java.util.List;

import cern.accsoft.steering.jmad.domain.result.track.DynapResult;
import cern.accsoft.steering.jmad.domain.result.track.DynapResultImpl;
import cern.accsoft.steering.jmad.domain.var.enums.MadxDynapVariable;
import cern.accsoft.steering.util.io.TextFileParser;
import cern.accsoft.steering.util.io.TextFileParserException;
import cern.accsoft.steering.util.io.impl.TextFileParserImpl;

/**
 * This class provide methods to parse a dynap output. Information in the summary part are discarded.
 * 
 * @author xbuffat
 */
public class DynapOutputParser {

    private DynapResult dynapResult;
    private File file;

    public DynapOutputParser(File file) {
        this.dynapResult = new DynapResultImpl();
        this.file = file;
    }

    public void parse() throws DynapParserException {
        TextFileParser parser = new TextFileParserImpl();

        List<String> lines;
        try {
            lines = parser.parse(file);
        } catch (TextFileParserException e) {
            throw new DynapParserException("Error while parsing MadX - Output file '" + file.getAbsolutePath() + "'", e);
        }

        this.dynapResult.clear();

        List<String> keys = null;
        for (int i = 0; i < lines.size(); ++i) {
            String line = lines.get(i);
            if (!line.contains("@")) {
                List<String> tokens = null;
                try {
                    tokens = TfsFileParser.splitString(line);
                } catch (TfsFileParserException e) {
                    throw new DynapParserException("Error while spliting line", e);
                }
                if (tokens.get(0).equalsIgnoreCase("*")) {
                    keys = tokens.subList(1, tokens.size());
                } else if (keys != null && !tokens.get(0).equalsIgnoreCase("$")) {
                    for (int j = 0; j < tokens.size(); ++j) {
                    	try{
                    		this.dynapResult.add(MadxDynapVariable.getVariableFromName(keys.get(j)),
                    				Double.parseDouble(tokens.get(j)));
                    	}catch (NumberFormatException exception) {
                    		throw new DynapParserException("Error while parsing dynap output : "+exception.getMessage(), exception);
                    	}
                    }
                }
            }
        }
    }

    public DynapResult getResult() {
        return this.dynapResult;
    }

}

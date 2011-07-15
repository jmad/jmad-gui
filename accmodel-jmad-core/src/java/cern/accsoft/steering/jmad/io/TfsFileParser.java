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

import cern.accsoft.steering.jmad.domain.result.tfs.TfsResultException;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsResultImpl;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsSummaryImpl;
import cern.accsoft.steering.jmad.util.MadxVarType;
import cern.accsoft.steering.jmad.util.StringUtil;
import cern.accsoft.steering.util.io.TextFileParser;
import cern.accsoft.steering.util.io.TextFileParserException;
import cern.accsoft.steering.util.io.impl.TextFileParserImpl;

/**
 * This class provides an interface to read MadX-output files. The read data is returned in a Result - Object.
 * 
 * @author kfuchsbe
 */
public class TfsFileParser {

    /** The index in the splitted line for the summary value */
    private static final int FIELD_IDX_SUMMARY_VALUE = 3;

    /** The file from where to read */
    private final File file;

    /** The result - object, where all read Data will be stored. */
    private final TfsResultImpl result = new TfsResultImpl();

    /**
     * @param file the file to parse.
     */
    public TfsFileParser(File file) {
        super();
        this.file = file;
    }

    /**
     * Parses the file and stores the data in internal Variables.
     * 
     * @throws TfsFileParserException if the parsing fails
     */
    public void parse() throws TfsFileParserException {

        TextFileParser parser = new TextFileParserImpl();

        List<String> lines;
        try {
            lines = parser.parse(file);
        } catch (TextFileParserException e) {
            throw new TfsFileParserException("Error while parsing MadX - Output file '" + file.getAbsolutePath() + "'",
                    e);
        }

        result.clear();
        TfsSummaryImpl summary = new TfsSummaryImpl();
        result.setTfsSummary(summary);

        for (int i = 0; i < lines.size(); i++) {

            String line = lines.get(i);
            String[] tokens = splitString(line).toArray(new String[] {});

            if ((tokens.length > 2) && (tokens[0].equalsIgnoreCase("@"))) {
                MadxVarType type = MadxVarType.getVarType(tokens[2].trim());
                summary.addValue(tokens[1], tokens[FIELD_IDX_SUMMARY_VALUE].replace("\"", ""), type);
            } else if (tokens.length > 0) {
                if (tokens[0].equalsIgnoreCase("*")) { // keys
                    for (int j = 1; j < tokens.length; j++) {
                        String keyName = tokens[j];
                        result.createColumn(keyName);
                    }
                } else if (tokens[0].equalsIgnoreCase("$")) { // types
                    checkTokenNumber(tokens.length - 1, line);
                    int keyCount = 1;
                    for (String key : result.getKeys()) {
                        result.setVarType(key, MadxVarType.getVarType(tokens[keyCount]));
                        keyCount++;
                    }
                } else { // values
                    checkTokenNumber(tokens.length, line);
                    ArrayList<String> values = new ArrayList<String>();
                    for (int j = 0; j < tokens.length; j++) {
                        values.add(tokens[j].replace("\"", ""));
                    }
                    result.addRow(values);
                }
            }
        }

        try {
            result.convert();
            result.verify();
            summary.convert();
        } catch (TfsResultException e) {
            throw new TfsFileParserException("Conversion or Verification of result (file='" + file.getAbsolutePath()
                    + "') failed!", e);
        }
    }

    /**
     * checks, if the the number corresponds to the initialized number of fields and throws an exception, if that is not
     * the case.
     * 
     * @param number the number of tokens to compare to the column count.
     * @param line the actual parsed line, to compose a meaningful error message.
     * @throws TfsFileParserException if the number does not correspond to the columnCounts in the actual result.
     */
    private void checkTokenNumber(int number, String line) throws TfsFileParserException {
        if (result.getColumnCount() != number) {
            throw new TfsFileParserException("Line '" + line + "' seems to contain " + number
                    + " fields, but there are only " + result.getColumnCount() + " keys. Unable to handle this.");
        }
    }

    /**
     * Static function to split a line (almost) correctly. This is somehow complicated, since quoted values must stay
     * together even if they contain spaces! XXX Problems may still arise, if quoted string e.g. has space as last
     * character!
     * 
     * @param string the String to split
     * @return the fields
     * @throws TfsFileParserException if no meaningful splitting is possible
     */
    static List<String> splitString(String string) throws TfsFileParserException { // NOPMD by kaifox on 10/6/10 8:25 PM
        /* PMD (package visibility for testing) */
        ArrayList<String> fields = new ArrayList<String>();

        String regexpSplitChars = "(^|\\s+)";
        String regexpQuotes = "\\\"";

        // first split on quotes after space
        String[] tokens = string.split(regexpSplitChars + regexpQuotes);

        for (int i = 0; i < tokens.length; i++) {
            // if token still contains quotes, than it should also have been
            // started by one...
            String stringPastQuote = "";
            if (tokens[i].contains("\"")) {
                // we split at the quote.
                String[] newTokens = tokens[i].split(regexpQuotes);
                if (newTokens.length > 2) {
                    throw new TfsFileParserException("Was not able to split string '" + string + "' correctly!");
                }
                fields.add(newTokens[0]); // this shall stay together since
                // they were the ones under quotes.
                // always add, could e.g. be ""
                if (newTokens.length == 2) {
                    stringPastQuote = newTokens[1];
                }
            } else { // if it contains no quote, we just split further
                stringPastQuote = tokens[i];
            }

            String[] newTokens = stringPastQuote.split("[ \t\r]+");
            for (int j = 0; j < newTokens.length; j++) {
                String field = newTokens[j];
                if (!StringUtil.isWhitespacesOnly(field)) { // only add if really some content.
                    fields.add(field);
                }
            }
        }
        return fields;
    }

    public TfsResultImpl getResult() {
        return result;
    }
}

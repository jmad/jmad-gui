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

package cern.accsoft.steering.jmad.gui.manage.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.domain.var.custom.CustomVariable;
import cern.accsoft.steering.jmad.domain.var.custom.StrengthVarSet;
import cern.accsoft.steering.jmad.domain.var.custom.StrengthVarSetImpl;
import cern.accsoft.steering.jmad.gui.manage.StrengthVarManager;
import cern.accsoft.steering.jmad.gui.manage.StrengthVarManagerListener;
import cern.accsoft.steering.jmad.io.StrengthFileParser;
import cern.accsoft.steering.jmad.io.StrengthFileParserException;
import cern.accsoft.steering.jmad.io.StrengthFileParserNew;

/**
 * This is the default implementation of a {@link StrengthVarManager}
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class StrengthVarManagerImpl implements StrengthVarManager {

    /** The logger for this class */
    private final static Logger logger = Logger.getLogger(StrengthVarManagerImpl.class);

    /** the variables are kept in a strengthVarSet */
    private StrengthVarSet strengthVarSet = new StrengthVarSetImpl();

    /** The listeners */
    private List<StrengthVarManagerListener> listeners = new ArrayList<StrengthVarManagerListener>();

    @Override
    public StrengthVarSet getStrengthVarSet() {
        return this.strengthVarSet;
    }

    /**
     * perform the file parsing.
     */
    @Override
    public void load(File file) {
        if (file == null) {
            logger.error("File is null! Nothing to parse.");
            return;
        }

        StrengthFileParserNew parser = new StrengthFileParserNew(file);
        try {
            parser.parse();
        } catch (StrengthFileParserException e) {
            logger.error("Could not parse file '" + file.getAbsolutePath() + "'.", e);
            return;
        }
        this.strengthVarSet.addAllStrengths(parser.getStrengths());
        this.strengthVarSet.addAllVariables(parser.getVariables());
        fireChangedVariables();
    }

    /**
     * notify the listeners that the data has changed.
     */
    private void fireChangedVariables() {
        for (StrengthVarManagerListener listener : this.listeners) {
            listener.changedVariables(new ArrayList<CustomVariable>(this.strengthVarSet.getVariables()));
        }
    }

    @Override
    public void addListener(StrengthVarManagerListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void removeListener(StrengthVarManagerListener listener) {
        this.listeners.remove(listener);
    }

}

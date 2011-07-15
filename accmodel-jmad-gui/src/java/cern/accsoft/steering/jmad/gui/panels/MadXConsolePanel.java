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

package cern.accsoft.steering.jmad.gui.panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import bsh.util.JConsole;
import cern.accsoft.steering.jmad.kernel.JMadKernelListener;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.manage.JMadModelManager;
import cern.accsoft.steering.jmad.model.manage.JMadModelManagerAdapter;
import cern.accsoft.steering.jmad.util.StreamConnector;

/**
 * This class provides a panel with a console, where it is possible to interact directly with madx. NOTE: If you
 * manipulate values in madx directly, they are in general not consistent with the java-representation of the model! So
 * use this possibility with care!
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class MadXConsolePanel extends JPanel {
    private static final long serialVersionUID = -227501450207198640L;

    private JConsole console;

    private StreamConnector outputConnector = null;
    private StreamConnector inputConnector = null;

    /**
     * init-method used by spring
     */
    public void init() {
        initComponenets();
    }

    /**
     * create all components
     */
    private void initComponenets() {
        setLayout(new BorderLayout());
        this.console = new JConsole();
        add(this.console, BorderLayout.CENTER);
    }

    /**
     * this methods registers a listener in the modelmanager in order to be notices, when the model changes.
     * 
     * @param manager the manager to set
     */
    public void setModelManager(JMadModelManager manager) {
        manager.addListener(new JMadModelManagerAdapter() {

            @Override
            public void changedActiveModel(JMadModel newModel) {
                newModel.getKernel().addListener(new JMadKernelListener() {

                    @Override
                    public void startedKernel(Process newProcess) {
                        setProcess(newProcess);
                    }

                    @Override
                    public void stoppedKernel() {
                        unsetProcess();
                    }
                });
            }
        });
    }

    /**
     * creates the connections between the processes of the kernel and the console
     * 
     * @param process
     */
    private void setProcess(Process process) {
        this.outputConnector = new StreamConnector(process.getInputStream(), console.getOut());
        this.outputConnector.start();

        this.inputConnector = new StreamConnector(console.getInputStream(), process.getOutputStream());
        this.inputConnector.start();
    }

    /**
     * removes the connection between the console and the kernel
     */
    private void unsetProcess() {
        closeConnector(this.inputConnector);
        this.inputConnector = null;

        closeConnector(this.outputConnector);
        this.outputConnector = null;
    }

    /**
     * stops one connection
     * 
     * @param connector the connector to stop
     */
    private void closeConnector(StreamConnector connector) {
        if (connector != null) {
            connector.interrupt();
        }
    }
}

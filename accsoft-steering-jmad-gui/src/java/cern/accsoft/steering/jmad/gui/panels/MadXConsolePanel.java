/*
 * $Id: MadXConsolePanel.java,v 1.2 2009-03-16 16:36:33 kfuchsbe Exp $
 * 
 * $Date: 2009-03-16 16:36:33 $ 
 * $Revision: 1.2 $ 
 * $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
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
 * This class provides a panel with a console, where it is possible to interact
 * directly with madx. NOTE: If you manipulate values in madx directly, they are
 * in general not consistent with the java-representation of the model! So use
 * this possibility with care!
 * 
 * @author kfuchsbe
 * 
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
	 * this methods registers a listener in the modelmanager in order to be
	 * notices, when the model changes.
	 * 
	 * @param manager
	 *            the manager to set
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
	 * creates the connections between the processes of the kernel and the
	 * console
	 * 
	 * @param process
	 */
	private void setProcess(Process process) {
		this.outputConnector = new StreamConnector(process.getInputStream(),
				console.getOut());
		this.outputConnector.start();

		this.inputConnector = new StreamConnector(console.getInputStream(),
				process.getOutputStream());
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
	 * @param connector
	 *            the connector to stop
	 */
	private void closeConnector(StreamConnector connector) {
		if (connector != null) {
			connector.interrupt();
		}
	}
}

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
package cern.accsoft.steering.jmad.gui.panels.elements;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import cern.accsoft.steering.jmad.domain.elem.Element;
import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.domain.optics.EditableOpticPointImpl;
import cern.accsoft.steering.jmad.domain.optics.OpticPoint;
import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;
import cern.accsoft.steering.jmad.gui.dv.ChartFactory;
import cern.accsoft.steering.jmad.gui.manage.ElementSelectionManager;
import cern.accsoft.steering.jmad.gui.manage.ElementSelectionManagerListener;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.manage.JMadModelManager;
import cern.jdve.Chart;
import cern.jdve.data.DefaultDataSource;

/**
 * Shows several graphs which display the closed orbit in phase and real space
 * at one or more elements.
 * 
 * @author kaifox
 * 
 */
public class PhaseSpacePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * The manager from which we get the information which elements are
	 * currently selected.
	 */
	private ElementSelectionManager elementSelectionManager;

	/**
	 * The factory which creates the default charts for jmad
	 */
	private ChartFactory chartFactory;

	/** The model manager to retrieve the optics values */
	private JMadModelManager modelManager;

	/** The datasets which contain the phase space datas */
	private List<PhaseSpaceDataSet> dataSets = new ArrayList<PhaseSpaceDataSet>();

	/**
	 * init method called by spring
	 */
	public void init() {
		initComponents();
	}

	/**
	 * creates all the gui components
	 */
	private void initComponents() {
		setLayout(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1;
		constraints.weighty = 1;

		Chart chart = createChart(JMadPlane.H);
		add(chart, constraints);

		constraints.gridx++;
		chart = createChart(JMadPlane.V);
		add(chart, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weighty = 0;

		final JCheckBox chkNormalized = new JCheckBox("Normalized");
		chkNormalized.setSelected(dataSets.get(0).isNormalized());
		chkNormalized.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean normalized = chkNormalized.isSelected();
				for (PhaseSpaceDataSet dataSet : dataSets) {
					dataSet.setNormalized(normalized);
				}
			}
		});

		add(chkNormalized, constraints);
	}

	private Chart createChart(JMadPlane plane) {
		PhaseSpaceDataSet dataSet = new PhaseSpaceDataSet("Phase Space "
				+ plane.toString(), plane);
		this.dataSets.add(dataSet);
		DefaultDataSource dataSource = new DefaultDataSource(dataSet);
		Chart chart = new Chart();
		getChartFactory().configurePolylineChart(dataSource, chart);
		return chart;
	}

	public void setElementSelectionManager(
			ElementSelectionManager elementSelectionManager) {
		this.elementSelectionManager = elementSelectionManager;
		this.elementSelectionManager
				.addListener(new ElementSelectionManagerListener() {

					@Override
					public void changedSelectedElements(
							List<Element> selectedElements,
							Element lastSelectedElement) {
						updateOpticsPoints(selectedElements);
					}
				});
	}

	/**
	 * updates the optics points for all datasets
	 * 
	 * @param selectedElements
	 */
	private void updateOpticsPoints(List<Element> selectedElements) {
		List<OpticPoint> opticsPoints = getElementOptics(selectedElements);
		for (PhaseSpaceDataSet dataSet : this.dataSets) {
			dataSet.setOpticsPoints(opticsPoints);
		}
	}

	/**
	 * retrieves the optics-point for each element from the model and handles
	 * exceptions if they occur.
	 * 
	 * @param elements
	 *            the elements for which to get the optics point
	 * @return the {@link EditableOpticPointImpl}
	 */
	private List<OpticPoint> getElementOptics(List<Element> elements) {
		List<OpticPoint> opticsPoints = new ArrayList<OpticPoint>();
		JMadModel model = getModelManager().getActiveModel();
		for (Element element : elements) {
			OpticPoint opticPoint = null;
			try {
				opticPoint = model.getOptics().getPoint(element);
			} catch (JMadModelException e) {
				; /* just do not add the optics point */
			}
			if (opticPoint != null) {
				opticsPoints.add(opticPoint);
			}
		}
		return opticsPoints;
	}

	public void setChartFactory(ChartFactory chartFactory) {
		this.chartFactory = chartFactory;
	}

	private ChartFactory getChartFactory() {
		return chartFactory;
	}

	public void setModelManager(JMadModelManager modelManager) {
		this.modelManager = modelManager;
	}

	private JMadModelManager getModelManager() {
		return modelManager;
	}
}

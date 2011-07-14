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
package cern.accsoft.steering.util.gui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * This class is a simple panel, which just contains a label and an textfield,
 * which reacts on pressing the return key and then applies the entered
 * regex-filter.
 * 
 * @author kfuchsbe
 * 
 */
public class TableFilterPanel extends JPanel {
	private static final long serialVersionUID = -1378930333245172769L;

	/**
	 * the table on which this filter will act.
	 */
	private JTable table;

	/**
	 * the constructor. enforces to provide a table, which to attach to.
	 * 
	 * @param table
	 *            the table on which we act.
	 */
	public TableFilterPanel(JTable table) {
		this.table = table;
		initComponents();
	}

	/**
	 * creates all components
	 */
	private void initComponents() {
		setLayout(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.fill = GridBagConstraints.BOTH;
		add(new JLabel("Filter:"), constraints);

		table.setAutoCreateRowSorter(true);
		final TableRowSorter<? extends TableModel> elementsSorter = (TableRowSorter<? extends TableModel>) this.table
				.getRowSorter();
		final JTextField filterText = new JTextField();
		filterText.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				String text = filterText.getText();
				if (text.length() == 0) {
					elementsSorter.setRowFilter(null);
				} else {
					elementsSorter.setRowFilter(RowFilter.regexFilter(text));
				}
			}
		});

		constraints.weightx = 1;
		constraints.gridx++;
		add(filterText, constraints);
	}

}

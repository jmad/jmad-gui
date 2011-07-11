/*
 * $Id: TableFilterPanel.java,v 1.1 2008-12-19 13:55:29 kfuchsbe Exp $
 * 
 * $Date: 2008-12-19 13:55:29 $ 
 * $Revision: 1.1 $ 
 * $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
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

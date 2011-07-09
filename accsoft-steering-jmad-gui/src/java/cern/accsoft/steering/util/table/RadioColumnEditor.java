package cern.accsoft.steering.util.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellEditor;

public class RadioColumnEditor extends AbstractCellEditor implements
		TableCellEditor {
	private static final long serialVersionUID = 1L;
	private JRadioButton radioButton;

	public RadioColumnEditor() {
		super();
		radioButton = new JRadioButton();
		radioButton.setBackground(Color.WHITE);
		radioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				fireEditingStopped();
			}
		});
	}

	public Component getTableCellEditorComponent(JTable table, Object obj,
			boolean isSelected, int row, int col) {
		radioButton.setHorizontalAlignment(SwingUtilities.CENTER);
		Boolean lValueAsBoolean = (Boolean) obj;
		radioButton.setSelected(lValueAsBoolean.booleanValue());
		return radioButton;
	}

	public Object getCellEditorValue() {
		return new Boolean(radioButton.isSelected());
	}
}
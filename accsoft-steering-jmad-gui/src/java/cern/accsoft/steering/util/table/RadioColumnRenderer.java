package cern.accsoft.steering.util.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

public class RadioColumnRenderer extends JRadioButton implements
		TableCellRenderer {
	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (value == null) {
			this.setSelected(false);
		}
		Boolean ValueAsBoolean = (Boolean) value;
		this.setBackground(Color.WHITE);
		this.setSelected(ValueAsBoolean.booleanValue());
		this.setHorizontalAlignment(SwingConstants.CENTER);
		return this;
	}
}
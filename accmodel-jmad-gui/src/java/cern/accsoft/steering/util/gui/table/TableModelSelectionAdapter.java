package cern.accsoft.steering.util.gui.table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * This class is the implementation of a listener to remember, which rows in the
 * table are selected and convert the indizes correctly to the model.
 * 
 * @author kfuchsbe
 * 
 */
public class TableModelSelectionAdapter implements ListSelectionListener {

	/* the table we listen to */
	private JTable table = null;

	/* all selected indizes in terms of the model */
	private ArrayList<Integer> selectedRowIndizes = new ArrayList<Integer>();

	/* the actually selected row. null if none selected */
	private Integer selectedRowIndex = null;

	/**
	 * the constructor, which sets the table, which we listen to.
	 * 
	 * @param table
	 */
	public TableModelSelectionAdapter(JTable table) {
		this.table = table;
		this.table.getSelectionModel().addListSelectionListener(this);
	}

	@Override
	public final void valueChanged(ListSelectionEvent event) {
		if (event.getSource() == table.getSelectionModel()) {

			/* store selected indizes */
			int[] rowIndizes = table.getSelectedRows();
			this.selectedRowIndizes.clear();
			for (int i = 0; i < rowIndizes.length; i++) {
				this.selectedRowIndizes.add(table
						.convertRowIndexToModel(rowIndizes[i]));
			}

			int index = table.getSelectedRow();
			if (index >= 0) {
				this.selectedRowIndex = table.convertRowIndexToModel(index);
			} else {
				this.selectedRowIndex = null;
			}
			selectionChanged(this.selectedRowIndex, this.selectedRowIndizes);
		}
	}

	/**
	 * this method may be overridden to react on a selcetion-change in a
	 * subclass
	 * 
	 * @param selectedRow
	 *            the actually selected row in the table-model
	 * @param selectedRows
	 *            all selected rows in the table-model
	 */
	protected void selectionChanged(Integer selectedRow,
			List<Integer> selectedRows) {
		/* override if wanted */
	}

	/**
	 * @return the indizes of all selected Rows in terms of the TableModel
	 */
	public List<Integer> getSelectedRowIndizes() {
		return this.selectedRowIndizes;
	}

	/**
	 * @return the index of the actually selected row in terms of the TableModel
	 */
	public Integer getSelectedRowIndex() {
		return this.selectedRowIndex;
	}
}
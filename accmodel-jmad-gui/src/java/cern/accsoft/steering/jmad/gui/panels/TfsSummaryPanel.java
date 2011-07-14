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
package cern.accsoft.steering.jmad.gui.panels;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import cern.accsoft.steering.jmad.domain.result.tfs.TfsResult;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsSummary;
import cern.accsoft.steering.util.gui.panels.TableFilterPanel;

/**
 * This panel displays the values of a tfs result
 * 
 * @author kaifox
 */
public class TfsSummaryPanel extends AbstractTfsDataSetManagerResultPanel {
    private static final long serialVersionUID = 1L;

    /** the table model which provides the data */
    private TfsSummaryTableModel tableModel = new TfsSummaryTableModel();

    public void init() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        JTable table = new JTable(this.tableModel);

        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(new TableFilterPanel(table), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        validate();
    }

    /**
     * The table model for the tfsSummary
     * 
     * @author kaifox
     */
    static class TfsSummaryTableModel extends AbstractTableModel {
        private static final long serialVersionUID = 1L;

        /** The actually displayed summary */
        private TfsSummary tfsSummary;

        /** we keep the strings so that they are in a fixed order */
        private List<String> keys = new ArrayList<String>();

        private final static int COL_IDX_NAME = 0;
        private final static int COL_IDX_VALUE = 1;
        private final static int COL_COUNT = 2;

        @Override
        public int getColumnCount() {
            return COL_COUNT;
        }

        @Override
        public int getRowCount() {
            if (tfsSummary != null) {
                return this.keys.size();
            }
            return 0;
        }

        @Override
        public String getColumnName(int col) {
            if (col == COL_IDX_NAME) {
                return "name";
            } else if (col == COL_IDX_VALUE) {
                return "value";
            }
            return super.getColumnName(col);
        }

        @Override
        public Object getValueAt(int row, int col) {
            String key = this.keys.get(row);
            if (col == COL_IDX_NAME) {
                return key;
            } else if (col == COL_IDX_VALUE) {
                return tfsSummary.getStringValue(key);
            }
            return null;
        }

        public void setTfsSummary(TfsSummary tfsSummary) {
            this.tfsSummary = tfsSummary;
            this.keys = new ArrayList<String>(tfsSummary.getKeys());
            fireTableDataChanged();
        }

    }

    @Override
    protected void update(TfsResult tfsResult) {
        this.tableModel.setTfsSummary(tfsResult.getSummary());
    }
}

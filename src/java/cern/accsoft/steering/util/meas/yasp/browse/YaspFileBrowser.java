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

package cern.accsoft.steering.util.meas.yasp.browse;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import cern.accsoft.steering.util.gui.menu.MousePopupListener;
import cern.accsoft.steering.util.gui.menu.TablePopupMenu;
import cern.accsoft.steering.util.gui.panels.TableFilterPanel;
import cern.accsoft.steering.util.gui.table.SelectionSetTableModel;
import cern.accsoft.steering.util.gui.table.TableModelSelectionAdapter;

/**
 * A simple dialog to browse through yasp files in a directory and preview the contents.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class YaspFileBrowser extends JDialog {
    private static final long serialVersionUID = 8936666262913943289L;

    /** The logger for the class */
    private final static Logger logger = Logger.getLogger(YaspFileBrowser.class);

    /** the table model for the files */
    private FileTableModel tableModel = new FileTableModel();

    /** the panel containing the dataviewer */
    private DataViewerPanel dvPanel = new DataViewerPanel();

    /** the current directory to browse */
    private File currentDir = null;

    public YaspFileBrowser(File currentDir) {
        if ((currentDir != null) && (currentDir.exists()) && (currentDir.isDirectory())) {
            this.currentDir = currentDir;
        }
        initComponents();
        parseDirectory();
    }

    public YaspFileBrowser() {
        this(null);
    }

    private void initComponents() {
        setPreferredSize(new Dimension(1200, 800));

        JPanel tablePanel = new JPanel(new BorderLayout());
        add(tablePanel, BorderLayout.WEST);

        JTable table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true);
        table.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.getSelectionModel().addListSelectionListener(new FileSelectionAdapter(table));

        new MousePopupListener(table, new TablePopupMenu(tableModel));
        tableModel.setTableModelSelectionAdapter(new TableModelSelectionAdapter(table));

        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        tablePanel.add(new TableFilterPanel(table), BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        JButton btn = new JButton("change dir");
        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                changeDirectory();
            }
        });
        tablePanel.add(btn, BorderLayout.SOUTH);

        /*
         * the panel for displaying the content
         */
        add(dvPanel, BorderLayout.CENTER);

    }

    private void changeDirectory() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(YaspFilters.TRAJECTORY_FILE_FILTER);
        fileChooser.setAcceptAllFileFilterUsed(true);
        if (this.currentDir != null) {
            fileChooser.setCurrentDirectory(this.currentDir);
        }

        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            this.currentDir = file.getAbsoluteFile().getParentFile();
            parseDirectory();
        }
    }

    private void parseDirectory() {
        if (this.currentDir == null) {
            return;
        }

        logger.info("parsing files in dir to '" + this.currentDir.toString() + "'");

        List<File> files = java.util.Arrays.asList(this.currentDir.listFiles(YaspFilters.TRAJECTORY_FILENAME_FILTER));
        tableModel.setFiles(files);
    }

    /**
     * This class is the implementation of a listener to change the displayed Data in the {@link DataViewerPanel}.
     * 
     * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
     */
    private class FileSelectionAdapter implements ListSelectionListener {
        private JTable table = null;

        public FileSelectionAdapter(JTable table) {
            this.table = table;
        }

        @Override
        public void valueChanged(ListSelectionEvent event) {
            if (event.getSource() == table.getSelectionModel()) {
                int index = table.getSelectedRow();
                if (index >= 0) {
                    dvPanel.setYaspFile(tableModel.getFile(table.convertRowIndexToModel(index)));
                }
            }
        }

    }

    private class FileTableModel extends SelectionSetTableModel {
        private static final long serialVersionUID = 66955782630097206L;

        /** The files in the list */
        private List<File> files = new ArrayList<File>();

        private final static int COL_NUM = 1;
        private final static int COL_IDX_NAME = 0;

        @Override
        public int getColumnCount() {
            return COL_NUM;
        }

        @Override
        public int getRowCount() {
            return this.files.size();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (columnIndex == COL_IDX_NAME) {
                return this.files.get(rowIndex).getName();
            }
            return null;
        }

        private void setFiles(List<File> files) {
            this.files = files;
            fireTableDataChanged();
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == COL_IDX_NAME) {
                return String.class;
            }
            return super.getColumnClass(columnIndex);
        }

        @Override
        public String getColumnName(int column) {
            if (column == COL_IDX_NAME) {
                return "name";
            }
            return super.getColumnName(column);
        }

        private File getFile(int index) {
            return this.files.get(index);
        }

    }

    /**
     * the main method.
     * 
     * @param args
     */
    public static void main(String[] args) {
        /* configure the log4j - system */
        BasicConfigurator.configure();

        File dir = null;
        if (args.length > 0) {
            dir = new File(args[0].trim());
        }

        JDialog dialog;
        if (dir != null) {
            dialog = new YaspFileBrowser(dir);
        } else {
            dialog = new YaspFileBrowser();
        }

        dialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        dialog.pack();
        dialog.setVisible(true);
    }

}

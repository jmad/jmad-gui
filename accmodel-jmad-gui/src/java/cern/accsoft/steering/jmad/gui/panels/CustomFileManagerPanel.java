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
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.gui.manage.CustomFileManager;
import cern.accsoft.steering.jmad.gui.manage.CustomFileManagerListener;
import cern.accsoft.steering.jmad.gui.manage.JMadGuiPreferences;
import cern.accsoft.steering.jmad.gui.manage.StrengthVarManager;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.manage.JMadModelManager;
import cern.accsoft.steering.util.gui.panels.TableFilterPanel;

/**
 * This panel allows to parse a variable-file and add these variables to the twiss results.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class CustomFileManagerPanel extends JPanel {
    private static final long serialVersionUID = 1427999923260387841L;

    /**
     * the logger for the class
     */
    private final static Logger logger = Logger.getLogger(CustomFileManagerPanel.class);

    /** the size of the table */
    private final static Dimension PREFERRED_TABLE_SIZE = new Dimension(200, 200);

    /** the tableModel for the variables */
    private CustomFilesTableModel tableModel = new CustomFilesTableModel();

    /**
     * the custom file manager, which keeps track of the files of interest.
     */
    private CustomFileManager customFileManager;

    /**
     * An instance of a preference class, which provides us with the default path to open files.
     */
    private JMadGuiPreferences jmadGuiPreferences;

    /**
     * the class which keeps track of the actually selected model
     */
    private JMadModelManager modelManager;

    /**
     * The manager, which keeps track of all available variables and strengthes from the custom files.
     */
    private StrengthVarManager strengthVarManager;

    /**
     * if this is set, then the file which is run is also parsed by the {@link StrengthVarManager} in order to be able
     * to display the variables and plot them.
     */
    private boolean parseWhenRunning = true;

    /**
     * the file-filter for alignment data
     */
    private FileFilter madxFileFileFilter = new FileFilter() {
        @Override
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            } else {
                String lowername = f.getName().toLowerCase();
                if (lowername.endsWith(".str")) {
                    return true;
                } else if (lowername.endsWith(".madx")) {
                    return true;
                } else if (lowername.endsWith(".seq")) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        @Override
        public String getDescription() {
            return "MADX - files";
        }
    };

    /**
     * simple constructors
     */
    public CustomFileManagerPanel() {
        initComponents();
    }

    /**
     * initializes the components.
     */
    private void initComponents() {
        setLayout(new BorderLayout());

        final JTable table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true);
        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getSource() == table.getSelectionModel()) {
                    int index = table.getSelectedRow();
                    if (index >= 0) {
                        tableModel.setCurrentElement(table.convertRowIndexToModel(index));
                    }
                }

            }
        });

        add(new TableFilterPanel(table), BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(PREFERRED_TABLE_SIZE);
        add(scrollPane, BorderLayout.CENTER);

        add(createButtonsPanel(), BorderLayout.SOUTH);

        validate();
    }

    private JPanel createButtonsPanel() {
        JPanel buttonPanel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;

        JButton btn = new JButton(createLoadFileAction());
        buttonPanel.add(btn, constraints);

        constraints.gridx++;
        btn = new JButton(createEditAction());
        buttonPanel.add(btn, constraints);

        constraints.gridx++;
        btn = new JButton(createRunAction());
        buttonPanel.add(btn, constraints);

        constraints.gridx++;
        final JCheckBox chk = new JCheckBox("parse when run");
        chk.setSelected(this.parseWhenRunning);
        chk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parseWhenRunning = chk.isSelected();
            }
        });
        buttonPanel.add(chk, constraints);

        return buttonPanel;
    }

    /**
     * creates the action for the button to store the actual data as reference.
     * 
     * @return the action
     */
    private Action createEditAction() {
        Action action = new AbstractAction("edit") {
            private static final long serialVersionUID = 1569348877859639865L;

            @Override
            public void actionPerformed(ActionEvent evt) {
                File selectedFile = tableModel.getSelecFile();
                if (selectedFile == null) {
                    logger.warn("No file selected. Nothing to do.");
                    return;
                }

                String editorCommand = getEditorCommand();
                if (editorCommand == null) {
                    logger.warn("No editor configured! Cannot open the file to edit.");
                    return;
                }
                String[] cmdArray = new String[] { editorCommand, selectedFile.getAbsolutePath() };
                try {
                    Runtime.getRuntime().exec(cmdArray);
                } catch (IOException e) {
                    logger.error("error executing command-array: " + cmdArray.toString(), e);
                }

            }
        };

        action.putValue(AbstractAction.SHORT_DESCRIPTION, "Opens the file in editor.");
        return action;
    }

    /**
     * creates the action for the button to store the actual data as reference.
     * 
     * @return the action
     */
    private Action createRunAction() {
        Action action = new AbstractAction("run") {
            private static final long serialVersionUID = -320576052275084112L;

            @Override
            public void actionPerformed(ActionEvent evt) {
                File selectedFile = tableModel.getSelecFile();
                if (selectedFile == null) {
                    logger.warn("No file selected. Nothing to do.");
                    return;
                }

                JMadModel model = getActiveModel();
                if (model == null) {
                    logger.warn("No active model. Cannot run file.");
                    return;
                }

                model.call(selectedFile);

                if (parseWhenRunning) {
                    if (strengthVarManager == null) {
                        logger.warn("No StrengthVarManager set.");
                        return;
                    }
                    strengthVarManager.load(selectedFile);
                }
            }
        };

        action.putValue(AbstractAction.SHORT_DESCRIPTION, "runs the selected file within the current model.");
        return action;
    }

    /**
     * opens a file selection dialog and adds the selected file to the list.
     */
    private void addFile() {
        if (getCustomFileManager() == null) {
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(madxFileFileFilter);
        fileChooser.setAcceptAllFileFilterUsed(true);
        fileChooser.setFileFilter(madxFileFileFilter);

        String workingDir = getWorkingDir();
        if (workingDir != null) {
            fileChooser.setCurrentDirectory(new File(workingDir));
        }

        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            getCustomFileManager().add(file);
        }
    }

    /**
     * creates the action for creating a new Data-View
     * 
     * @return the action
     */
    private Action createLoadFileAction() {
        Action action = new AbstractAction("add file") {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent evt) {
                addFile();
            }
        };
        action.putValue(AbstractAction.SHORT_DESCRIPTION, "adds a new file to the list");
        return action;
    }

    /**
     * table model for the available variables.
     * 
     * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
     */
    private class CustomFilesTableModel extends AbstractTableModel {
        private static final long serialVersionUID = -4386551081279607172L;

        private final static int COLUMN_COUNT = 1;

        private final static int COL_NAME = 0;

        /** the files to display */
        private List<File> files = new ArrayList<File>();

        /** the actually selected file */
        private File selectedFile = null;

        private synchronized void setFiles(List<File> files) {
            this.files = files;
            fireTableDataChanged();
        }

        @Override
        public int getColumnCount() {
            return COLUMN_COUNT;
        }

        @Override
        public int getRowCount() {
            return this.files.size();
        }

        @Override
        public Object getValueAt(int row, int col) {
            File file = this.files.get(row);
            switch (col) {
            case COL_NAME:
                return file.getAbsoluteFile();
            default:
                return null;
            }
        }

        @Override
        public Class<?> getColumnClass(int col) {
            switch (col) {
            case COL_NAME:
                return String.class;
            default:
                return null;
            }
        }

        @Override
        public String getColumnName(int col) {
            switch (col) {
            case COL_NAME:
                return "filename";
            default:
                return null;
            }
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            throw new UnsupportedOperationException("Not imlemented");
        }

        private void setCurrentElement(int index) {
            if (getCustomFileManager() == null) {
                return;
            }
            if ((index >= 0) && (index < this.files.size())) {
                this.selectedFile = files.get(index);
            }
        }

        private File getSelecFile() {
            return this.selectedFile;
        }

    }

    public void setCustomFileManager(CustomFileManager customFileManager) {
        this.customFileManager = customFileManager;
        this.customFileManager.addListener(new CustomFileManagerListener() {

            @Override
            public void changedFiles() {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        CustomFileManagerPanel.this.tableModel.setFiles(CustomFileManagerPanel.this.customFileManager
                                .getFiles());
                    }
                });
            }
        });
    }

    public CustomFileManager getCustomFileManager() {
        return customFileManager;
    }

    public void setJmadGuiPreferences(JMadGuiPreferences jmadGuiPreferences) {
        this.jmadGuiPreferences = jmadGuiPreferences;
    }

    private String getWorkingDir() {
        if (jmadGuiPreferences == null) {
            return null;
        }
        return jmadGuiPreferences.getWorkingDir();
    }

    private String getEditorCommand() {
        if (jmadGuiPreferences == null) {
            return null;
        }
        return jmadGuiPreferences.getEditorCommand();
    }

    public void setModelManager(JMadModelManager modelManager) {
        this.modelManager = modelManager;
    }

    public JMadModel getActiveModel() {
        if (modelManager == null) {
            return null;
        }
        return modelManager.getActiveModel();
    }

    public void setStrengthVarManager(StrengthVarManager strengthVarManager) {
        this.strengthVarManager = strengthVarManager;
    }

}

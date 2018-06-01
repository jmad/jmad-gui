/**
 * Copyright (c) 2015 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package cern.accsoft.steering.jmad.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import cern.accsoft.gui.frame.Task;
import cern.accsoft.steering.jmad.domain.elem.Element;
import cern.accsoft.steering.jmad.domain.elem.MadxElementType;
import cern.accsoft.steering.jmad.gui.panels.var.ElementCellRenderer;
import cern.accsoft.steering.jmad.gui.panels.var.SequenceElementsTableModel;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.manage.JMadModelManager;
import cern.accsoft.steering.jmad.service.JMadModelSeqElementOnNameComparator;
import cern.accsoft.steering.jmad.service.SequenceElementFilter;

public class ComparisonPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private SequenceElementsTableModel sequenceElementesModel1 = new SequenceElementsTableModel();
    private SequenceElementsTableModel sequenceElementesModel2 = new SequenceElementsTableModel();
    private ElementCellRenderer elementCellRenderer;
    private JComboBox<MadxElementType> filterSelection;
    private JButton loadElements;

    private JMadModelManager jMadModelManager;
    private JMadModel model1;
    private JMadModel model2;

    private JLabel labelModel2;

    private JLabel labelModel1;

    public void init() {
        Objects.requireNonNull(jMadModelManager, "jMadModelManager");
        buildGui();
    }

    public void compareTwoModels(JMadModel model1Set, JMadModel model2Set) {
        this.model1 = model1Set;
        this.model2 = model2Set;
        labelModel1.setText(model1.getName());
        labelModel2.setText(model2.getName());
        loadElements.setEnabled(true);
        fireNewDataArrived();
    }

    private void buildGui() {
        elementCellRenderer = new ElementCellRenderer();

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Sequence comparison"));
        add(createButtonsPanel(), BorderLayout.NORTH);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JPanel panelModel1 = new JPanel();
        JTable table = new JTable(sequenceElementesModel1);
        table.setDefaultRenderer(Element.class, elementCellRenderer);
        panelModel1.setLayout(new BorderLayout());
        labelModel1 = new JLabel("MODEL1 NAME");
        labelModel1.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        panelModel1.add(labelModel1, BorderLayout.NORTH);
        panelModel1.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(panelModel1);

        JPanel panelModel2 = new JPanel();
        JTable table2 = new JTable(sequenceElementesModel2);
        table2.setDefaultRenderer(Element.class, elementCellRenderer);
        panelModel2.setLayout(new BorderLayout());
        labelModel2 = new JLabel("MODEL2 NAME");
        labelModel2.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        panelModel2.add(labelModel2, BorderLayout.NORTH);
        panelModel2.add(new JScrollPane(table2), BorderLayout.CENTER);
        panel.add(panelModel2);

        add(panel, BorderLayout.CENTER);
    }

    private Component createButtonsPanel() {
        JPanel jPanel = new JPanel();
        filterSelection = new JComboBox<MadxElementType>(MadxElementType.values());

        jPanel.add(filterSelection);

        loadElements = new JButton("Show selected elements...");
        loadElements.setEnabled(false);
        loadElements.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireNewDataArrived();
            }

            /**
             * 
             */

        });
        jPanel.add(loadElements);
        return jPanel;
    }

    private void fireNewDataArrived() {
        new LoadElementsTask(new MutableSequnceFilter((MadxElementType) filterSelection.getSelectedItem())).start();
    }

    private class LoadElementsTask extends Task<Boolean> {

        private SequenceElementFilter filter;

        public LoadElementsTask(SequenceElementFilter filter) {
            this.filter = filter;
        }

        @Override
        protected Boolean construct() {
            elementCellRenderer.setElements(jMadModelManager.getCommonSequenceElements(model1, model2, filter));
            sequenceElementesModel1.updateElements(JMadModelSeqElementOnNameComparator.getFilteredElements(model1
                    .getActiveRange().getElements(), filter));
            sequenceElementesModel1.setModelName(model1.getName());
            sequenceElementesModel2.updateElements(JMadModelSeqElementOnNameComparator.getFilteredElements(model2
                    .getActiveRange().getElements(), filter));
            sequenceElementesModel2.setModelName(model2.getName());
            return null;
        }

    }

    public void setjMadModelManager(JMadModelManager jMadModelManager) {
        this.jMadModelManager = jMadModelManager;
    }

    private class MutableSequnceFilter implements SequenceElementFilter {

        private MadxElementType elementType;

        public MutableSequnceFilter(MadxElementType elementType) {
            this.elementType = elementType;
        }

        @Override
        public boolean accepts(Element element) {

            if (MadxElementType.KICKER.equals(elementType)) {
                if (element.getMadxElementType().equals(MadxElementType.HKICKER)
                        || element.getMadxElementType().equals(MadxElementType.VKICKER)) {
                    return true;
                }
            }

            if (element.getMadxElementType().equals(elementType)) {
                return true;
            }
            return false;
        }
    }

}

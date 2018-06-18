/**
 * Copyright (c) 2015 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package cern.accsoft.steering.jmad.gui.panels.var;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import cern.accsoft.steering.jmad.domain.elem.Element;
import com.google.common.collect.ImmutableList;

public class SequenceElementsTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    private List<ElementPosition> data = ImmutableList.of();
    private String modelName;

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ElementPosition rowElement = data.get(rowIndex);
        if (columnIndex == 0) {
            return rowElement.element;
        }
        if (columnIndex == 1) {
            return rowElement.position;
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        if (column == 0) {
            return "Element";
        }
        if (column == 1) {
            return "Position";
        }
        return super.getColumnName(column);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
            return Element.class;
        }
        return Double.class;
    }

    public void updateElements(List<Element> elements) {
        ImmutableList.Builder<ElementPosition> builder = ImmutableList.builder();
        for (Element element : elements) {
            builder.add(new ElementPosition(element, element.getPosition().getValue()));
        }
        this.data = builder.build();
        fireTableDataChanged();
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    private static class ElementPosition {
        private final Element element;
        private final double position;

        public ElementPosition(Element element, double position) {
            this.element = element;
            this.position = position;
        }
    }
}

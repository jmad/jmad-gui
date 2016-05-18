/**
 * Copyright (c) 2015 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package cern.accsoft.steering.jmad.gui.panels.var;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import cern.accsoft.commons.util.value.Pair;
import cern.accsoft.steering.jmad.domain.elem.Element;

public class SequenceElementsTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    private List<Pair<Element, Double>> data = new ArrayList<Pair<Element, Double>>();
    private String modelName;
    private Pair<String, String> headers = new Pair<String, String>("None", "None");

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

        Pair<Element, Double> rowElement = data.get(rowIndex);
        if (columnIndex == 0) {
            return rowElement.getFirst();
        }
        if (columnIndex == 1) {
            return rowElement.getSecond();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        if (column == 0) {
            return headers.getFirst();
        }
        if (column == 1) {
            return headers.getSecond();
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

    public void setHeaders(Pair<String, String> headers) {
        this.headers = headers;
    }

    public void updateElements(List<Element> elements) {
        this.data = new ArrayList<Pair<Element, Double>>();
        for (Element element : elements) {
            data.add(new Pair<Element, Double>(element, element.getPosition().getValue()));
        }
        fireTableDataChanged();
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

}

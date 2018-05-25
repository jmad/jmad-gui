/**
 * Copyright (c) 2015 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package cern.accsoft.steering.jmad.gui.panels.var;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import cern.accsoft.steering.jmad.domain.elem.Element;

public class ElementCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;
    private List<Element> elements = new ArrayList<Element>();

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {

        JLabel toReturnSuper = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                column);
        JLabel toReturn = new JLabel();
        Element element = (Element) value;
        String text = element.getName() + " / " + element.getMadxElementType();
        toReturn.setText(text);
        if (!isSelected) {
            if (!isInCommonElementList(element)) {
                toReturn.setBackground(Color.pink);
            } else {
                toReturn.setBackground(toReturnSuper.getBackground());
            }
        } else {
            if (!isInCommonElementList(element)) {
                toReturn.setBackground(Color.red);
            } else {
                toReturn.setBackground(toReturnSuper.getBackground());
            }
        }
        toReturn.setOpaque(true);
        return toReturn;
    }

    private boolean isInCommonElementList(Element element) {
        for (Element one : elements) {
            if (one.getName().equals(element.getName())) {
                return true;
            }
        }
        return false;
    }

    public void setElements(List<Element> elements) {
        this.elements = new ArrayList<Element>(elements);
    }
}

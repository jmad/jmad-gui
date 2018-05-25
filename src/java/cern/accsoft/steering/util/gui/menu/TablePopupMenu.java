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

package cern.accsoft.steering.util.gui.menu;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import cern.accsoft.steering.util.gui.AskUtils;

/**
 * This class is a basic popupmenu used in tables, to enable setting a value to multiple rows and checking/unchecking
 * multiple rows.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class TablePopupMenu extends JPopupMenu {
    private static final long serialVersionUID = -3519304791315763524L;

    /* the tableModel, which should implement one ore the other interface ;-) */
    private Checkable checkable = null;
    private ValueSetable valueSetable = null;

    /* alternatively we can have a class, that provides some additional actions */
    private ActionProvider actionProvider = null;

    /*
     * the menu items
     */
    private JMenuItem itemSetValue = null;
    private JMenuItem itemCheck = null;
    private JMenuItem itemUncheck = null;

    /**
     * the constructor, which sets one or both of the member-variables.
     * 
     * @param object
     */
    public TablePopupMenu(Object object) {
        if (object instanceof Checkable) {
            this.checkable = (Checkable) object;
        }
        if (object instanceof ValueSetable) {
            this.valueSetable = (ValueSetable) object;
        }
        if (object instanceof ActionProvider) {
            this.actionProvider = (ActionProvider) object;
        }
        initComponents();
    }

    /**
     * creates all menu - items
     */
    private void initComponents() {

        if (valueSetable != null) {
            itemSetValue = new JMenuItem("set " + valueSetable.getValueName());
            itemSetValue.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (valueSetable != null) {
                        Double value = AskUtils.askDouble(
                                "Set " + valueSetable.getValueName() + " for selected rows: ", 0.0);
                        if (value != null) {
                            valueSetable.setValueAllSelected(value);
                        }
                    }
                }
            });
            add(itemSetValue);
            addSeparator();
        }

        if (checkable != null) {
            itemCheck = new JMenuItem("check");
            itemCheck.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (checkable != null) {
                        checkable.checkAllSelected();
                    }
                }
            });
            add(itemCheck);

            itemUncheck = new JMenuItem("uncheck");
            itemUncheck.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    if (checkable != null) {
                        checkable.uncheckAllSelected();
                    }
                }
            });
            add(itemUncheck);

            addSeparator();
        }

        if (actionProvider != null) {
            for (Action action : actionProvider.getActions()) {
                JMenuItem item = new JMenuItem(action);
                add(item);
            }
        }
    }

    @Override
    public void show(Component invoker, int x, int y) {
        if (valueSetable != null) {
            itemSetValue.setEnabled(valueSetable.isValueSetEnabled());
        }
        if (checkable != null) {
            itemCheck.setEnabled(checkable.isCheckUncheckEnabled());
            itemUncheck.setEnabled(checkable.isCheckUncheckEnabled());
        }
        super.show(invoker, x, y);
    }

}

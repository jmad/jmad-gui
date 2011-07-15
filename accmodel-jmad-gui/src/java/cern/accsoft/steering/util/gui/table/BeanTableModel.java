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

package cern.accsoft.steering.util.gui.table;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

/**
 * this class is a simple table model, which enables the display of an object, that provides java-bean like properties
 * in a table. the table then has as many rows as the bean has properties and 2 columns: one for the name of the
 * property and one for the value.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class BeanTableModel extends AbstractTableModel {
    private static final long serialVersionUID = -7282333473638238414L;

    /** the logger for the class */
    private final static Logger logger = Logger.getLogger(BeanTableModel.class);

    /** the amount of Columns */
    private final static int COL_COUNT = 3;

    /* the indizes for the columns */
    private final static int COL_INDEX_SELECTED = 0;
    private final static int COL_INDEX_NAME = 1;
    private final static int COL_INDEX_VALUE = 2;

    /* the prefixes we use to identify a getter- and setter- methods */
    private final static String GETTER_PREFIX_DEFAULT = "get";
    private final static String GETTER_PREFIX_BOOLEAN = "is";
    private final static String SETTER_PREFIX = "set";

    /**
     * the classes, which are allowed for the value-column. One has to take care, that they are compatible, since table
     * only supports one class for the column. To tell the table, which renderer to use, we use the first entry in this
     * array. By default we support Double and double.
     */
    private Class<?>[] valueClasses = new Class<?>[] { Double.class, double.class };

    /** the explicit bean-class to use */
    private Class<?> beanClass = null;

    /** the actually displayed bean */
    private Object bean;

    /** the names of the getters corresponding to the properties */
    private List<Method> getters = new ArrayList<Method>();

    /** determines, if the values are editable */
    private boolean editable = false;

    /** an edit-handler, which enables to check/uncheck certain properties */
    private BeanTableEditHandler editHandler = null;

    public BeanTableModel(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    /**
     * the according getter-prefix for the configured type.
     * 
     * @return the prefix for getters.
     */
    private String getGetterPrefix() {
        if (Boolean.class.equals(valueClasses[0])) {
            return GETTER_PREFIX_BOOLEAN;
        } else {
            return GETTER_PREFIX_DEFAULT;
        }
    }

    @Override
    public int getColumnCount() {
        return COL_COUNT;
    }

    @Override
    public int getRowCount() {
        return this.getters.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        if (col == COL_INDEX_SELECTED) {
            if (getEditHandler() != null) {
                return getEditHandler().getCheckValue(this.bean, getPropertyName(row));
            } else {
                return false;
            }
        } else if (col == COL_INDEX_NAME) {
            return getPropertyName(row);
        } else if (col == COL_INDEX_VALUE) {
            return getPropertyValue(row);
        } else {
            logger.warn("unknown column number '" + col + "'");
            return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int col) {
        switch (col) {
        case COL_INDEX_SELECTED:
            return Boolean.class;
        case COL_INDEX_NAME:
            return String.class;
        case COL_INDEX_VALUE:
            return getValueClasses()[0];
        default:
            return null;
        }
    }

    @Override
    public String getColumnName(int col) {
        switch (col) {
        case COL_INDEX_SELECTED:
            if (getEditHandler() != null) {
                return getEditHandler().getCheckableColumnHeader();
            } else {
                return "select";
            }
        case COL_INDEX_NAME:
            return "property name";
        case COL_INDEX_VALUE:
            return "value";
        default:
            return null;
        }
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        switch (col) {
        case COL_INDEX_SELECTED:
            if (getEditHandler() != null) {
                getEditHandler().setCheckValue(getBean(), getPropertyName(row), (Boolean) value);
            }
            break;
        case COL_INDEX_VALUE:
            setPropertyValue(value, row);
            break;
        default:
            super.setValueAt(value, row, col);
            break;
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        if (COL_INDEX_SELECTED == col) {
            return ((getEditHandler() != null) && (getEditHandler().isEditable()));
        } else if (COL_INDEX_VALUE == col) {
            return isEditable();
        } else {
            return false;
        }
    }

    /**
     * sets the given value to the property of the given index
     * 
     * @param value the value to set
     * @param index the index of the property to which to set the value
     */
    private void setPropertyValue(Object value, int index) {
        if (getBean() == null) {
            return;
        }

        Method setter = getSetter(index);
        if (setter == null) {
            logger.error("no setter method for property '" + getPropertyName(index) + "' could be found.");
            return;
        }

        try {
            setter.invoke(getBean(), value);
        } catch (IllegalArgumentException e) {
            logger.error("Error while invoking setter '" + setter.getName() + "' of bean '" + getBean().toString(), e);
        } catch (IllegalAccessException e) {
            logger.error("Error while invoking setter '" + setter.getName() + "' of bean '" + getBean().toString(), e);
        } catch (InvocationTargetException e) {
            logger.error("Error while invoking setter '" + setter.getName() + "' of bean '" + getBean().toString(), e);
        }
    }

    /**
     * searches in the bean for a getter of property with given index
     * 
     * @param index the index for which to find the getter
     * @return the getter method
     */
    private Method getSetter(int index) {
        String getterName = this.getters.get(index).getName();

        String setterName = SETTER_PREFIX + getterName.substring(getGetterPrefix().length());
        Method setter = null;
        for (int i = 0; i < valueClasses.length; i++) {
            Class<?> valueClass = this.valueClasses[i];
            try {
                setter = this.bean.getClass().getMethod(setterName, valueClass);
                if (setter != null) {
                    break;
                }
            } catch (SecurityException e) {
                logger.error("Error while searching for method '" + setterName + "'.", e);
            } catch (NoSuchMethodException e) {
                /* do nothing, just search for another one. */
            }
        }
        return setter;
    }

    /**
     * return the name of the property for a given index
     * 
     * @param index the index in the getterNames - list
     * @return the name of the property
     */
    private String getPropertyName(int index) {
        String getterName = this.getters.get(index).getName();

        int startIndex = getGetterPrefix().length();
        String propertyName = getterName.substring(startIndex, startIndex + 1).toLowerCase();
        if (getterName.length() > getGetterPrefix().length() + 1) {
            propertyName += getterName.substring(startIndex + 1);
        }
        return propertyName;
    }

    /**
     * return the value of the property
     * 
     * @param index the index in the getterNames - list
     * @return the value
     */
    private Object getPropertyValue(int index) {
        if (getBean() == null) {
            return null;
        }
        Method method = this.getters.get(index);
        try {
            return method.invoke(getBean());
        } catch (IllegalArgumentException e) {
            logger.error("Error while invoking getter '" + method.getName() + "' of bean '" + getBean().toString(), e);
        } catch (IllegalAccessException e) {
            logger.error("Error while invoking getter '" + method.getName() + "' of bean '" + getBean().toString(), e);
        } catch (InvocationTargetException e) {
            logger.error("Error while invoking getter '" + method.getName() + "' of bean '" + getBean().toString(), e);
        }
        return null;
    }

    /**
     * searches for all getters in the bean. These are methods which begin with the prefix. Additionaly we only keep
     * such, which return the classes which we support.
     */
    private void initGetterNames() {
        this.getters.clear();

        for (Method method : this.beanClass.getMethods()) {
            /*
             * we are only interested in getters, that take no argument.
             */
            if (method.getParameterTypes().length > 0) {
                continue;
            }
            String methodName = method.getName();
            if (methodName.startsWith(getGetterPrefix()) && methodName.length() > getGetterPrefix().length()) {
                for (int i = 0; i < getValueClasses().length; i++) {
                    if (method.getReturnType().equals(getValueClasses()[i])) {
                        this.getters.add(method);
                        break;
                    }
                }
            }
        }
    }

    /**
     * set the bean
     * 
     * @param bean the bean, which shall be displayed.
     */
    public void setBean(Object bean) {
        this.bean = bean;
        initGetterNames();
        fireTableDataChanged();
    }

    /**
     * @return the actual displayed bean
     */
    private Object getBean() {
        return this.bean;
    }

    /**
     * @param editable the editable to set
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * @return the editable
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * @param valueClasses the valueClasses to set
     */
    public void setValueClasses(Class<?>[] valueClasses) {
        this.valueClasses = valueClasses;
    }

    /**
     * @return the valueClasses
     */
    public Class<?>[] getValueClasses() {
        return valueClasses;
    }

    /**
     * @param editHandler the editHandler to set
     */
    public void setEditHandler(BeanTableEditHandler editHandler) {
        this.editHandler = editHandler;
    }

    /**
     * @return the editHandler
     */
    public BeanTableEditHandler getEditHandler() {
        return editHandler;
    }

}

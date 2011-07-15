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

package cern.accsoft.steering.jmad.gui.components;

import java.text.NumberFormat;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * This is a renderer for table-cells, that renders doiuble values with more digits after the comma than the standard
 * renderer.
 * 
 * @author kaifox
 */
public class DoubleTableCellRenderer extends DefaultTableCellRenderer {
    private static final long serialVersionUID = -5226757407025454017L;

    /** the number of digits we use for the format */
    private final static int NUMBER_DIGITS = 10;

    /** the format as static variable */
    private final static NumberFormat NUMBER_FORMAT;

    /**
     * initialization block for the number - format
     */
    static {
        NUMBER_FORMAT = NumberFormat.getInstance();
        NUMBER_FORMAT.setMaximumFractionDigits(NUMBER_DIGITS);
    }

    /**
     * constructor, which just configures the alignment
     */
    public DoubleTableCellRenderer() {
        super();
        setHorizontalAlignment(SwingConstants.RIGHT);
    }

    /**
     * the overridden conversion - method.
     */
    @Override
    public void setValue(Object value) {
        setText((value == null) ? "" : NUMBER_FORMAT.format(value));
    }
}
// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
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
package cern.accsoft.steering.util.gui.dv.ds;

import java.awt.Color;

/**
 * This interface contains color - constants for the dataviewer
 * 
 * @author kfuchsbe
 */
public interface ColorConstants {
    /* colors used */
    public final static Color COLOR_RED = new Color(127, 0, 0);
    public final static Color COLOR_GREEN = new Color(0, 127, 0);
    public final static Color COLOR_BLUE = new Color(0, 0, 127);

    /* colors for certain purposes */
    /* a light blue */
    public final static Color COLOR_MEAS_DATA_STROKE = new Color(114, 167, 255);
    // public final static Color COLOR_MEAS_DATA_FILL = COLOR_MEAS_DATA_STROKE;
    public final static Color COLOR_MEAS_DATA_FILL = new Color(218, 232, 255);

    public final static Color COLOR_MEAS_ERROR_FILL = new Color(36, 119, 255);
    public final static Color COLOR_MEAS_ERROR_STROKE = COLOR_MEAS_ERROR_FILL;

    /* a kind of red */
    public final static Color COLOR_MODEL_DATA = new Color(187, 19, 0);

    // public final static Color COLOR_MEAS_DATA = new Color(30, 170, 110);
    // public final static Color COLOR_MEAS_DATA = new Color(80, 130, 190);
    // public final static Color COLOR_MEAS_ERROR = new Color(10, 130, 80);
    // public final static Color COLOR_MODEL_DATA = new Color(75, 80, 160);
    // public final static Color COLOR_MODEL_DATA = new Color(170, 20, 0);

    public final static Color COLOR_INVALID_DATA = new Color(255, 0, 0);

    public final static Color COLOR_X = new Color(20, 130, 190);
    public final static Color COLOR_Y = new Color(190, 20, 60);

    /* light blue */
    public static final Color CHARTBG_BEAM_1 = new Color(204, 220, 255);

    /* ocre */
    public static final Color CHARTBG_BEAM_2 = new Color(255, 240, 200);

}

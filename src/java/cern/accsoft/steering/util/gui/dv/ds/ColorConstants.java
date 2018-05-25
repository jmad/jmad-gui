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

package cern.accsoft.steering.util.gui.dv.ds;

import java.awt.Color;

/**
 * This interface contains color - constants for the dataviewer
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
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

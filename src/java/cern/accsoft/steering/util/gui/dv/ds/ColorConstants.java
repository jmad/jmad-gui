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

import java.awt.*;

/**
 * This interface contains color - constants for the dataviewer
 *
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public interface ColorConstants {
    Color COLOR_MEAS_DATA_STROKE = new Color(114, 167, 255);
    Color COLOR_MEAS_DATA_FILL = new Color(218, 232, 255);

    Color COLOR_MEAS_DATA_TRAJECTORY = new Color(0, 127, 0);

    Color COLOR_MEAS_ERROR_FILL = new Color(36, 119, 255);
    Color COLOR_MEAS_ERROR_STROKE = COLOR_MEAS_ERROR_FILL;

    Color COLOR_MODEL_DATA = new Color(187, 19, 0);

    Color COLOR_INVALID_DATA = new Color(255, 0, 0);
}

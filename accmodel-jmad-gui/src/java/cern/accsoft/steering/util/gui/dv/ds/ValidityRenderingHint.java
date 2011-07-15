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

import cern.jdve.Style;
import cern.jdve.data.DataSet;
import cern.jdve.graphic.RenderingHint;
import cern.jdve.utils.DisplayPoint;

/**
 * This class defines, how the value for a BPM will be rendered.
 * 
 * @author kfuchsbe
 */
public class ValidityRenderingHint implements RenderingHint {

    /* the two different styles we use */
    private final static Style STYLE_INVALID = new Style(ColorConstants.COLOR_INVALID_DATA,
            ColorConstants.COLOR_INVALID_DATA);

    @Override
    public Style getStyle(DisplayPoint point, Style defaultStyle) {
        DataSet dataSet = point.getDataSet();

        /*
         * check which basic style to use
         */
        Style style = defaultStyle;
        if ((dataSet instanceof ValidityDataSet) && (((ValidityDataSet) dataSet).hasValidityInformation())) {
            if (!((ValidityDataSet) dataSet).getValidity(point.getIndex())) {
                style = STYLE_INVALID;
            }
        }

        return style;
    }
}

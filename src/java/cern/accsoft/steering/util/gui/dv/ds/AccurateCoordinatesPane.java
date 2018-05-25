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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import cern.jdve.interactor.CoordinatesPane;
import cern.jdve.utils.DisplayPoint;

public class AccurateCoordinatesPane extends CoordinatesPane {

    private static final long serialVersionUID = 1L;
    private NumberFormat numberFormat;

    public AccurateCoordinatesPane() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        numberFormat = new DecimalFormat("#.########", symbols);
    }

    protected String computeYLabel(DisplayPoint displayPoint) {
        return numberFormat.format(displayPoint.getY());
    }
}

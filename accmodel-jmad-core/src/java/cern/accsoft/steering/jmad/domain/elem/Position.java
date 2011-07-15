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

package cern.accsoft.steering.jmad.domain.elem;

public class Position {

    private String element;
    private double position;
    private boolean relative;

    public Position(double position) {
        this.position = position;
        this.relative = false;
    }

    public Position(String element, double position) {
        this.setElement(element);
        this.setPosition(position);
        this.setRelative(true);
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getElement() {
        return element;
    }

    public void setPosition(Double position) {
        this.position = position;
    }

    public double getValue() {
        return position;
    }

    public void setRelative(boolean relative) {
        this.relative = relative;
    }

    public boolean isRelative() {
        return relative;
    }

}

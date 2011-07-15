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

/**
 * 
 */
package cern.accsoft.steering.jmad.domain.result.match.output;

/**
 * @author muellerg
 */
public class MadxVaryResultImpl implements MadxVaryResult {

    private final String name;
    private final double finalValue;

    public MadxVaryResultImpl(String name, double finalValue) {

        this.name = name;
        this.finalValue = finalValue;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getFinalValue() {
        return this.finalValue;
    }

    // @Override
    // public double getInitialValue() {
    // return this.initialValue;
    // }
    //
    // @Override
    // public double getLowerLimit() {
    // return this.lowerLimit;
    // }
    //
    // @Override
    // public double getUpperLimit() {
    // return this.upperLimit;
    // }

}

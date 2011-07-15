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
package cern.accsoft.steering.jmad.domain.result.match.methods;

/**
 * this is the general type of a method for matching in madx.
 * 
 * @author muellerg
 */
public interface MatchMethod {

    /**
     * Available MadX supported matching algorithms which define the Type of the {@link MatchMethod}
     */
    public static enum AlgorithmType {
        LMDIF("lmdif"), MIGRAD("migrad"), SIMPLEX("simplex"), JACOBIAN("jacobian"), NONE("none");

        private String name;

        private AlgorithmType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    /**
     * @return the {@link AlgorithmType} of the MatchMethod
     */
    public abstract AlgorithmType getAlgorithmType();

    /**
     * @return the number of calls during the matching
     */
    public abstract int getCalls();

    /**
     * @return The desired tolerance for the minimum .
     */
    public abstract double getTolerance();

}

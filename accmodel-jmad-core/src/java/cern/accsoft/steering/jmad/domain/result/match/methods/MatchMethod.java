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

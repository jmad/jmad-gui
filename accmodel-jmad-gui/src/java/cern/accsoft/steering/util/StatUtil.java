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
package cern.accsoft.steering.util;

import java.text.NumberFormat;

/**
 * simple statistical util-methods
 * 
 * @author kfuchsbe
 */
public class StatUtil {

    /** the formatter for the mean and rms-output */
    private final static NumberFormat formatter = NumberFormat.getInstance();
    static {
        formatter.setMinimumFractionDigits(1);
        formatter.setMaximumFractionDigits(6);
    }

    private StatUtil() {
        /* only static methods */
    }

    /**
     * creates a predefined string, that contains useful statistical information.
     * 
     * @param values the values for which to calc the stats
     * @return the string
     */
    public final static String createMeanRmsString(double values[]) {
        double mean = calcMean(values);
        double rms = calcRms(values);
        double meanrms = calcStandardDeviation(values, mean);

        String outString = "rms=" + formatter.format(rms) + "; mean=" + formatter.format(mean) + ";  stddev="
                + formatter.format(meanrms);
        return outString;
    }

    /**
     * calculates the average of the given values
     * 
     * @param values the values to calc the average from
     * @return the average
     */
    public final static double calcMean(double[] values) {
        double sum = 0.0;
        for (int i = 0; i < values.length; i++) {
            sum += values[i];
        }
        if (values.length > 0) {
            return sum / values.length;
        } else {
            return 0;
        }
    }

    /**
     * calculates the rms of the given array of values
     * 
     * @param values the values to calc the rms from
     * @return the rms
     */
    public final static double calcRms(double values[]) {
        double sum = 0;
        for (int i = 0; i < values.length; i++) {
            sum += Math.pow(values[i], 2);
        }

        if (values.length > 0) {
            /* we divide by N */
            sum /= (values.length);
        }
        return Math.sqrt(sum);
    }

    /**
     * calculates an estimate for the standard deviation with respect to the given mean.
     * 
     * @param values the values for which to calc the diff-rms
     * @param meanValue the value to calc the diff to for each value.
     * @return the diff-rms
     */
    public final static double calcStandardDeviation(double values[], double meanValue) {
        double sum = 0;
        for (int i = 0; i < values.length; i++) {
            sum += Math.pow(values[i] - meanValue, 2);
        }

        if (values.length > 1) {
            /* we divide by (N-1)! */
            sum /= (values.length - 1);
        }
        return Math.sqrt(sum);
    }

}

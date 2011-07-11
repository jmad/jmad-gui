/*
 * $Id: SineFit.java,v 1.3 2009-03-16 16:38:11 kfuchsbe Exp $
 * 
 * $Date: 2009-03-16 16:38:11 $ 
 * $Revision: 1.3 $ 
 * $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.util.fit;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
//import org.freehep.math.minuit.FCNBase;
//import org.freehep.math.minuit.FunctionMinimum;
//import org.freehep.math.minuit.MnMigrad;
//import org.freehep.math.minuit.MnUserParameters;

import cern.jdve.data.DataSet;
import cern.jdve.data.DefaultDataSet;
import cern.jdve.utils.DataRange;
import cern.jdve.utils.DisplayPoint;

/**
 * This is the sine-function used for fitting.
 * 
 * @author kfuchsbe
 * 
 */
//public class SineFit implements FCNBase, DataViewerFit {
public class SineFit implements DataViewerFit {

	/** the logger for the class */
	private final static Logger logger = Logger.getLogger(SineFit.class);

	/*
	 * the name of the used parameters
	 */
	public final static String PARAM_NAME_AMPLITUDE = "amplitude";
	public final static String PARAM_NAME_FREQUENCY = "frequency";
	public final static String PARAM_NAME_PHASE = "phase";

	/*
	 * the indizes of the parameters
	 */
	public final static int PARAM_INDEX_AMPLITUDE = 0;
	public final static int PARAM_INDEX_FREQUENCY = 1;
	public final static int PARAM_INDEX_PHASE = 2;

	/** the display points to which to fit the sine */
	private List<DisplayPoint> displayPoints = new ArrayList<DisplayPoint>();

	/** the last determined sine */
	private SineFunction lastFit;

	/**
	 * the default constructor. Initialize the points to where to fit to.
	 * 
	 * @param displayPoints
	 *            the points to fit the sine
	 */
	public SineFit(List<DisplayPoint> displayPoints) {
		this.displayPoints = displayPoints;
	}

	//@Override
	public double valueOf(double[] params) {
		int nPoints = displayPoints.size();

		SineFunction sine = new SineFunction(params[PARAM_INDEX_AMPLITUDE],
				params[PARAM_INDEX_FREQUENCY], params[PARAM_INDEX_PHASE]);

		double sum = 0.0;
		for (DisplayPoint point : this.displayPoints) {
			sum += Math.pow(point.getY() - sine.valueAt(point.getX()), 2);
		}

		if (nPoints > 1) {
			sum /= (nPoints - 1);
		}

		/* return the chisquare */
		return Math.sqrt(sum);
	}

	/**
	 * the sine function
	 * 
	 * @author kfuchsbe
	 * 
	 */
	private class SineFunction {

		/** the amplitude of the sine function */
		private double amplitude;

		/** the frequency of the sine function */
		private double frequency;

		/** the phase of the sine function */
		private double phase;

		/**
		 * the constructor with the parameters
		 * 
		 * @param amplitude
		 * @param frequency
		 * @param phase
		 */
		private SineFunction(double amplitude, double frequency, double phase) {
			this.amplitude = amplitude;
			this.frequency = frequency;
			this.phase = phase;
		}

		/**
		 * calculates the value at the given point x
		 * 
		 * @param x
		 *            the value for which to calculate the value
		 * @return the value
		 */
		private double valueAt(double x) {
			return (amplitude * Math.sin(2 * Math.PI * (frequency * x + phase)));
		}

		@Override
		public String toString() {
			return "amplitude: " + amplitude + "; frequency: " + frequency
					+ "; phase: " + phase + ";";
		}
	}

	@Override
	public DataSet getResultDataSet(DataRange range, int nPoints) {

		SineFunction sine = getLastFit();
		if (sine == null) {
			logger
					.warn("No fitted function available. Either the fit was not performed, or it did not give a valid result.");
			return new DefaultDataSet("Sine fit", new double[] {},
					new double[] {});
		}
		double step = range.getLength() / (nPoints - 1);
		double[] xValues = new double[nPoints];
		double[] yValues = new double[nPoints];

		for (int i = 0; i < nPoints; i++) {
			double xValue = range.getMin() + (step * i);
			xValues[i] = xValue;
			yValues[i] = sine.valueAt(xValue);
		}

		return new DefaultDataSet("Sine fit", xValues, yValues);
	}

	/**
	 * performs the fit and stores the fitted function for further usage.
	 */
	public void doFit() {
//		MnUserParameters upar = new MnUserParameters();
//		upar.add(PARAM_NAME_AMPLITUDE, 1.0, 0.01);
//		upar.add(PARAM_NAME_FREQUENCY, 1.0, 0.01);
//		upar.add(PARAM_NAME_PHASE, 0.0, 0.01);
//
//		MnMigrad migrad = new MnMigrad(this, upar);
//
//		FunctionMinimum min = migrad.minimize();
//
//		if (min.isValid()) {
//			MnUserParameters parameters = min.userParameters();
//			this.lastFit = new SineFunction(parameters
//					.value(PARAM_NAME_AMPLITUDE), parameters
//					.value(PARAM_NAME_FREQUENCY), parameters
//					.value(PARAM_NAME_PHASE));
//			double chiSquared = valueOf(new double[] {
//					parameters.value(PARAM_NAME_AMPLITUDE),
//					parameters.value(PARAM_NAME_FREQUENCY),
//					parameters.value(PARAM_NAME_PHASE) });
//			logger.info("Successful fit. Result: " + this.lastFit.toString()
//					+ " chisquared: " + chiSquared + ";");
//		} else {
//			logger.warn("Fit was not successful.");
//			this.lastFit = null;
//		}
	}

	/**
	 * @return the lastFit
	 */
	public SineFunction getLastFit() {
		return lastFit;
	}
}

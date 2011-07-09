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

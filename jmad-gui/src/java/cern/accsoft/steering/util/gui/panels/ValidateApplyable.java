package cern.accsoft.steering.util.gui.panels;

public interface ValidateApplyable extends Applyable {

	/**
	 * if this returns false, then the apply() method is not called
	 * 
	 * @return
	 */
	public boolean prepareApply();
}

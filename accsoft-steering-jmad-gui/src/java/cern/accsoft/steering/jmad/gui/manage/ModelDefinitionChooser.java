/**
 * 
 */
package cern.accsoft.steering.jmad.gui.manage;

/**
 * @author kfuchsbe
 * 
 */
public interface ModelDefinitionChooser {

	/**
	 * shows a dialog to select a model.
	 */
	public void showModelChooseDialog();

	/**
	 * shows a dialog to select a range definition.
	 */
	public void showRangeDefinitionChooseDialog();

	/**
	 * shows the dialog to choose an optics definition.
	 */
	public void showOpticsDefinitionChooseDialog();

}

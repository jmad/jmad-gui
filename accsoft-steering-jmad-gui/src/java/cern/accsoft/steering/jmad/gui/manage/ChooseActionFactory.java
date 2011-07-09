/**
 * 
 */
package cern.accsoft.steering.jmad.gui.manage;

import javax.swing.Action;

/**
 * interface, which provides actions to choose model, range and optics.
 * 
 * @author kfuchsbe
 */
public interface ChooseActionFactory {

    /**
     * @return the action to choose a new model.
     */
    public abstract Action getNewModelAction();

    /**
     * @return the action to start choosing a new range for the model.
     */
    public abstract Action getChooseRangeAction();

    /**
     * @return the action to start choosing a new optics for the model.
     */
    public abstract Action getChooseOpticsAction();

    /**
     * @return the action to close the active model
     */
    public abstract Action getCloseActiveModelAction();

    /**
     * @return the action to exit the application
     */
    public abstract Action getExitAction();
}

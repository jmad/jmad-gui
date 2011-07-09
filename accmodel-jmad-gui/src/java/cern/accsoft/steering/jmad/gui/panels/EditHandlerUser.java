package cern.accsoft.steering.jmad.gui.panels;

/**
 * classes derived from this interface can use {@link TablePanelEditHandler}s
 * 
 * @author kaifox
 * 
 */
public interface EditHandlerUser<T extends TablePanelEditHandler> {

	/**
	 * the edit handler can be injected via this method
	 * 
	 * @param editHandler
	 */
	public void setEditHandler(TablePanelEditHandler editHandler);

}

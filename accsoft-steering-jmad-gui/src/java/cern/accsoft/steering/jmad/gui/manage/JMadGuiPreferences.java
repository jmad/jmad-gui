package cern.accsoft.steering.jmad.gui.manage;

public interface JMadGuiPreferences {

	public abstract boolean isEnabledChangeModel();

	public abstract void setEnabledChangeModel(boolean enabledChangeModel);

	public abstract boolean isEnabledChangeRange();

	public abstract void setEnabledChangeRange(boolean enabledChangeRange);

	public abstract boolean isEnabledChangeOptic();

	public abstract void setEnabledChangeOptic(boolean enabledChangeOptic);

	public void setCleanupOnClose(boolean cleanupOnClose);

	public boolean isCleanupOnClose();

	public void setExitOnClose(boolean exitOnClose);

	public boolean isExitOnClose();

	public boolean isMainFrame();

	public void setMainFrame(boolean mainFrame);

	public String getWorkingDir();

	/**
	 * returns a string that can start an external text editor.
	 * 
	 * @return the command String.
	 */
	public String getEditorCommand();

}
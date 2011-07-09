package cern.accsoft.steering.jmad.gui.manage.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.gui.manage.CustomFileManager;
import cern.accsoft.steering.jmad.gui.manage.CustomFileManagerListener;

public class CustomFileManagerImpl implements CustomFileManager {

	/** The list of files which are managed */
	private List<File> files = new ArrayList<File>();

	/** all the listeners to this class */
	private List<CustomFileManagerListener> listeners = new ArrayList<CustomFileManagerListener>();

	@Override
	public void add(File file) {
		this.files.add(file);
		fireChangedFiles();
	}

	@Override
	public void remove(File file) {
		this.files.remove(file);
		fireChangedFiles();
	}

	@Override
	public List<File> getFiles() {
		return this.files;
	}

	/**
	 * notifies all the listeners that files were added or removed.
	 */
	private void fireChangedFiles() {
		for (CustomFileManagerListener listener : listeners) {
			listener.changedFiles();
		}
	}

	@Override
	public void addListener(CustomFileManagerListener listener) {
		this.listeners.add(listener);
	}

	@Override
	public void removeListener(CustomFileManagerListener listener) {
		this.listeners.remove(listener);
	}

}

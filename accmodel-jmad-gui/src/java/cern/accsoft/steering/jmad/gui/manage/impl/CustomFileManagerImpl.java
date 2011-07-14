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

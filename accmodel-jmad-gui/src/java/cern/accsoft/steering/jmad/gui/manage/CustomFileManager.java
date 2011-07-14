// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, Kajetan Fuchsberger. All rights reserved.
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
package cern.accsoft.steering.jmad.gui.manage;

import java.io.File;
import java.util.List;

/**
 * This is the interface of a class which keeps track of custom files, that can
 * be added and executed.
 * 
 * @author kfuchsbe
 * 
 */
public interface CustomFileManager {

	/**
	 * adds a file to the manager
	 * 
	 * @param file
	 *            the file to add
	 */
	public void add(File file);

	/**
	 * removes a file from the manager.
	 * 
	 * @param file
	 *            the file to remove
	 */
	public void remove(File file);

	/**
	 * @return all the currently available files
	 */
	public List<File> getFiles();

	/**
	 * adds a listener to the {@link CustomFileManager}
	 * 
	 * @param listener
	 *            the listener to add
	 */
	public void addListener(CustomFileManagerListener listener);

	/**
	 * removes a listener from the {@link CustomFileManager}
	 * 
	 * @param listener
	 *            the listener to remove
	 */
	public void removeListener(CustomFileManagerListener listener);

}

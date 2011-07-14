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
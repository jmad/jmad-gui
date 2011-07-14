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
package cern.accsoft.steering.util.meas.yasp.browse;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JFileChooser;

/**
 * A file chooser, which contains a preview for yasp files
 * 
 * @author kfuchsbe
 * 
 */
public class YaspFileChooser extends JFileChooser {
	private static final long serialVersionUID = 1L;

	/** the panel containing the dataviewer */
	private DataViewerPanel dvPanel = new DataViewerPanel();

	/**
	 * the listener to the selection change
	 */
	private PropertyChangeListener propertyChangeListener = new PropertyChangeListener() {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			String propertyName = evt.getPropertyName();

			/* Make sure we are responding to the right event. */
			if (propertyName
					.equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)) {
				File selectedFile = (File) evt.getNewValue();

				if (selectedFile == null) {
					/* no file selected */
					return;
				}
//				if (!YaspFilters.TRAJECTORY_FILE_FILTER.accept(selectedFile)) {
//					/* will not be able to preview the file */
//					return;
//				}
				dvPanel.setYaspFile(selectedFile);
			}

		}
	};

	public YaspFileChooser() {
		super.addChoosableFileFilter(YaspFilters.TRAJECTORY_FILE_FILTER);
		super.setAcceptAllFileFilterUsed(true);
		super.setAccessory(this.dvPanel);
		super.addPropertyChangeListener(this.propertyChangeListener);

	}

}

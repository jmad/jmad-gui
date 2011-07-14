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

import java.io.File;
import java.io.FilenameFilter;

import javax.swing.filechooser.FileFilter;

/**
 * @author kfuchsbe
 * 
 */
public class YaspFilters {

	private YaspFilters() {
		/* No instantiation */
	}

	public final static FilenameFilter TRAJECTORY_FILENAME_FILTER = new FilenameFilter() {

		@Override
		public boolean accept(File dir, String name) {
			boolean retval = false;
			String filename = name.toUpperCase();
			if (filename.startsWith("TRAJ") || filename.startsWith("ORBIT")
					|| filename.startsWith("DATASET-")
					|| filename.startsWith("RM.")) {
				retval = true;

			}
			return retval;
		}

	};

	/**
	 * the file-filter for yasp trajectory files
	 */
	public final static FileFilter TRAJECTORY_FILE_FILTER = new FileFilter() {
		@Override
		public boolean accept(File f) {
			if (f.isDirectory()) {
				return true;
			} else {
				return TRAJECTORY_FILENAME_FILTER.accept(f.getParentFile(), f
						.getName());
			}
		}

		@Override
		public String getDescription() {
			return "Yasp data directory";
		}
	};
}

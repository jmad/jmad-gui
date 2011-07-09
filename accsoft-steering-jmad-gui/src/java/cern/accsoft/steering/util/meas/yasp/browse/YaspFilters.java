/**
 * 
 */
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

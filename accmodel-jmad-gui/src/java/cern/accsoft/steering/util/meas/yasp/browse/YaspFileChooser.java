/**
 * 
 */
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

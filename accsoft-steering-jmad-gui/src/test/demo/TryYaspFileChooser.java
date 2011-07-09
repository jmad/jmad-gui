package demo;

import java.io.File;

import javax.swing.JFileChooser;

import cern.accsoft.steering.util.meas.yasp.browse.YaspFileChooser;

public class TryYaspFileChooser {

	/**
	 * use args[0] as the current path
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		JFileChooser fileChooser = new YaspFileChooser();

		if (args.length > 0) {
			fileChooser.setCurrentDirectory(new File(args[0]));
		}
		fileChooser.setMultiSelectionEnabled(true);


		int returnValue = fileChooser.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File[] files = fileChooser.getSelectedFiles();
			System.out.println("Selected files:");
			System.out.println("---------------");
			for (File file : files) {
				System.out.println(file.getAbsolutePath());
			}
		} else {
			System.out.println("File selection aborted.");
		}

	}

}

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

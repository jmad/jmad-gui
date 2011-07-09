package cern.accsoft.steering.util.gui.icons;

import javax.swing.ImageIcon;

public enum Icon {
	
	SINE_FIT("linechart.png");
	
	private final static String PATH_PREFIX = "data/";
	
	private String name = null;
	
	private Icon(String name) {
		this.name = name;
	}

	public ImageIcon getImageIcon() {
		return new ImageIcon(Icon.class.getResource(PATH_PREFIX + name));
	}
}
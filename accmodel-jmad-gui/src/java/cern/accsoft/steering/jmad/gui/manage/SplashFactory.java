/*
 * $Id: SplashFactory.java,v 1.2 2009-03-16 16:38:12 kfuchsbe Exp $
 * 
 * $Date: 2009-03-16 16:38:12 $ 
 * $Revision: 1.2 $ 
 * $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.gui.manage;

import cern.accsoft.gui.beans.spi.SplashScreen;
import cern.accsoft.steering.jmad.gui.icons.Icon;

/**
 * This class manages the Splash-screen.
 * 
 * @author kfuchsbe
 * 
 */
public class SplashFactory {

	private final static SplashScreen splashScreen = new SplashScreen(null,
			Icon.SPLASH.getImageIcon().getImage());

	/**
	 * static block to initialize the splashScreen.
	 */
	static {
		splashScreen.setText("JMad");
		splashScreen.setMessage("... the java MADX integration.");
	}

	/**
	 * @return the {@link SplashScreen}
	 */
	public final static SplashScreen getSplashScreen() {
		return splashScreen;
	}
}

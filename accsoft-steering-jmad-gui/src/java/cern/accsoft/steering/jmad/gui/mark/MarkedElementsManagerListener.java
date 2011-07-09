/*
 * $Id: MarkedElementsManagerListener.java,v 1.1 2009-02-25 18:48:36 kfuchsbe Exp $
 * 
 * $Date: 2009-02-25 18:48:36 $ 
 * $Revision: 1.1 $ 
 * $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.gui.mark;

/**
 * The interface of a listener to the markedElementsManager
 * 
 * @author kfuchsbe
 * 
 */
public interface MarkedElementsManagerListener {

	/**
	 * fired when an elementName was added
	 * 
	 * @param elementName
	 *            the name of the element, that was added
	 */
	public void addedElementName(String elementName);

	/**
	 * fired, when an elementName was removed
	 * 
	 * @param elementName
	 *            the name of the element, that was removed
	 */
	public void removedElementName(String elementName);
}

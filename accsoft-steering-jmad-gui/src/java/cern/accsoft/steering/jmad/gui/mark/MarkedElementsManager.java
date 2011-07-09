/*
 * $Id: MarkedElementsManager.java,v 1.1 2009-02-25 18:48:36 kfuchsbe Exp $
 * 
 * $Date: 2009-02-25 18:48:36 $ 
 * $Revision: 1.1 $ 
 * $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.gui.mark;

import java.util.Collection;

/**
 * A manager for markers in Charts
 * 
 * @author kfuchsbe
 * 
 */
public interface MarkedElementsManager {

	/**
	 * adds an the given elementName to the marked elements
	 * 
	 * @param marker
	 */
	public void addElementName(String elementName);

	/**
	 * removes a elementName from the list of marked elements
	 * 
	 * @param elementName
	 *            the name of the element to remove
	 */
	public void removeElementName(String elementName);

	/**
	 * @param elementName
	 * @return true, if the manager contains the given name, false otherwise
	 */
	public boolean contains(String elementName);

	/**
	 * @return all contained elementNames
	 */
	public Collection<String> getElementNames();

	/**
	 * @param listener
	 *            the listener to add
	 */
	public void addListener(MarkedElementsManagerListener listener);

	/**
	 * @param listener
	 *            the listener to remove
	 */
	public void removeListener(MarkedElementsManagerListener listener);
}

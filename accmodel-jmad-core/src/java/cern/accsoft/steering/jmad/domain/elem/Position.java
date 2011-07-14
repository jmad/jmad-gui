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
package cern.accsoft.steering.jmad.domain.elem;

public class Position {

	private String element;
	private double position;
	private boolean relative;
	
	public Position(double position) {
		this.position = position;
		this.relative = false;
	}
	
	public Position(String element,double position) {
		this.setElement(element);
		this.setPosition(position);
		this.setRelative(true);
	}

	public void setElement(String element) {
		this.element = element;
	}

	public String getElement() {
		return element;
	}

	public void setPosition(Double position) {
		this.position = position;
	}

	public double getValue() {
		return position;
	}

	public void setRelative(boolean relative) {
		this.relative = relative;
	}

	public boolean isRelative() {
		return relative;
	}
	
}

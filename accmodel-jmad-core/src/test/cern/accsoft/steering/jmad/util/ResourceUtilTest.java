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
/**
 * 
 */
package cern.accsoft.steering.jmad.util;

import static junit.framework.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

/**
 * A simple test case for the resource util
 * 
 * @author kfuchsbe
 * 
 */
public class ResourceUtilTest extends LoggedTestCase {

	@Test
	public void testListPackageContent() {
		Package pkg = ResourceUtil.class.getPackage();
		Collection<String> files = ResourceUtil.getResourceNames(pkg.getName());

		assertTrue("Should contain at least one file", files.size() > 0);

		boolean found = false;
		for (String file : files) {
			if ((ResourceUtil.class.getSimpleName() + ".class").equals(file)) {
				found = true;
				break;
			}
		}

		assertTrue("The resource util itself should be found.",
				found);

	}
}

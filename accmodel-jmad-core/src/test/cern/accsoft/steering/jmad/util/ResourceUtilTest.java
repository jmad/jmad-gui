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

// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

        assertTrue("The resource util itself should be found.", found);

    }
}

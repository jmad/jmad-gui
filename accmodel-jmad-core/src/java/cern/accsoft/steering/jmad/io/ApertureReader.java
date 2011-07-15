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

package cern.accsoft.steering.jmad.io;

import java.io.File;

import cern.accsoft.steering.jmad.domain.aperture.Aperture;

/**
 * this is the interface of a class which loads the aperture data for jmad
 * 
 * @author kfuchsbe
 */
public interface ApertureReader {

    /**
     * loads the aperture data from the file
     * 
     * @param file the file from which to load the data
     * @return a newly instance of the Aperture-data
     */
    public Aperture readIndex(File file);

    /**
     * updates the given aperture with the values from the file.
     * 
     * @param file the file from which to read the values
     * @param aperture the aperture sobject to update
     */
    public void readValues(File file, Aperture aperture);

}

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

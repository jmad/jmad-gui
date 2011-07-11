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

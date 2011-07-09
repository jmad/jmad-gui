package cern.accsoft.steering.jmad.bin;

import java.io.IOException;

/**
 * Encapsulates the execution of the (native) MadX binaries.
 * 
 * @author kaifox
 */
public interface MadxBin {

    /**
     * executes MadX
     * 
     * @return the process of the running MadX
     * @throws IOException if the execution fails
     */
    public abstract Process execute() throws IOException;

}
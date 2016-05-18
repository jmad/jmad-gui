/**
 * Copyright (c) 2015 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package cern.accsoft.steering.jmad.kernel;

import cern.accsoft.steering.jmad.JMadException;

public class MadxTerminatedException extends JMadException {
    private static final long serialVersionUID = 1L;

    public MadxTerminatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public MadxTerminatedException(String message) {
        super(message);
    }

    public MadxTerminatedException(Throwable cause) {
        super(cause);
    }

}

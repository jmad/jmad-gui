/**
 * Copyright (c) 2015 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package cern.accsoft.steering.jmad.kernel;

import cern.accsoft.steering.jmad.JMadException;

public class WaitForMadxTimedOutException extends JMadException {
    private static final long serialVersionUID = 1L;

    public WaitForMadxTimedOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public WaitForMadxTimedOutException(String message) {
        super(message);
    }

    public WaitForMadxTimedOutException(Throwable cause) {
        super(cause);
    }

}

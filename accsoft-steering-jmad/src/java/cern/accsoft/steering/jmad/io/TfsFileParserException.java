package cern.accsoft.steering.jmad.io;

import cern.accsoft.steering.jmad.JMadException;

public class TfsFileParserException extends JMadException {

    private static final long serialVersionUID = 1L;

    public TfsFileParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public TfsFileParserException(String message) {
        super(message);
    }

    public TfsFileParserException(Throwable cause) {
        super(cause);
    }

}

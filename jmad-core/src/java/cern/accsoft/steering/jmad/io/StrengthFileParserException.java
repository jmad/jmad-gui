package cern.accsoft.steering.jmad.io;

import cern.accsoft.steering.jmad.JMadException;

public class StrengthFileParserException extends JMadException {

    private static final long serialVersionUID = 1L;

    public StrengthFileParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public StrengthFileParserException(Throwable cause) {
        super(cause);
    }

    public StrengthFileParserException(String message) {
        super(message);
    }

}

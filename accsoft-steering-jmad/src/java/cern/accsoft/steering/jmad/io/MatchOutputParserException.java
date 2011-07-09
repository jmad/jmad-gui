package cern.accsoft.steering.jmad.io;

import cern.accsoft.steering.jmad.JMadException;

public class MatchOutputParserException extends JMadException {
    private static final long serialVersionUID = 3607954290650559188L;

    public MatchOutputParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public MatchOutputParserException(Throwable cause) {
        super(cause);
    }

    public MatchOutputParserException(String message) {
        super(message);
    }

}

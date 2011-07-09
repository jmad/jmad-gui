package cern.accsoft.steering.jmad.domain.ex;

import cern.accsoft.steering.jmad.JMadException;

public class JMadModelException extends JMadException {
    private static final long serialVersionUID = -6755182093280489763L;

    public JMadModelException(String message) {
        super(message);
    }

    public JMadModelException(Throwable cause) {
        super(cause);
    }

    public JMadModelException(String message, Throwable cause) {
        super(message, cause);
    }

}

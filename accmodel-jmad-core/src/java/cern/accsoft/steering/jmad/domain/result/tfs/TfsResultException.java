package cern.accsoft.steering.jmad.domain.result.tfs;

import cern.accsoft.steering.jmad.JMadException;

public class TfsResultException extends JMadException {

    private static final long serialVersionUID = 1L;

    public TfsResultException(String message, Throwable cause) {
        super(message, cause);
    }

    public TfsResultException(String message) {
        super(message);
    }

    public TfsResultException(Throwable cause) {
        super(cause);
    }

}

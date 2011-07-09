package cern.accsoft.steering.jmad;

public class JMadException extends Exception {

    private static final long serialVersionUID = 1L;

    public JMadException(String message, Throwable cause) {
        super(message, cause);
    }

    public JMadException(String message) {
        super(message);
    }

    public JMadException(Throwable cause) {
        super(cause);
    }

}

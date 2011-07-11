package cern.accsoft.steering.jmad.util.xml;

public class PersistenceServiceException extends Exception {
    private static final long serialVersionUID = -8938564259820244738L;

    public PersistenceServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistenceServiceException(String message) {
        super(message);
    }

    public PersistenceServiceException(Throwable cause) {
        super(cause);
    }

}

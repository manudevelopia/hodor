package info.developia.hodor;

public class HodorException extends RuntimeException {
    public HodorException(String message) {
        super(message);
    }

    public HodorException(String message, Throwable cause) {
        super(message, cause);
    }

    public HodorException(Class<? extends Throwable> clazz, String message) {
        super("HodorException: %s %s".formatted(clazz, message));
    }
}

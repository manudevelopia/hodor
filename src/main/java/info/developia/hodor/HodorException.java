package info.developia.hodor;

public class HodorException extends RuntimeException {
    public HodorException(Class<? extends Throwable> clazz, String message) {
        super("HodorException: %s %s".formatted(clazz, message));
    }
}

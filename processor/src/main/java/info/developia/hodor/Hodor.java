package info.developia.hodor;

import java.util.function.Consumer;

public class Hodor {

    private Hodor() {
    }

    public static void holdTheDoor(Consumer<String[]> applicationMain, Consumer<Throwable> lastAction) {
        try {
            applicationMain.accept(null);
        } catch (Throwable t) {
            lastAction.accept(t);
            throw new HodorException(t.getClass(), t.getMessage());
        }
    }
}

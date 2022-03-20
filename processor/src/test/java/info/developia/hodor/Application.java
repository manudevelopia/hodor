package info.developia.hodor;

import java.util.Arrays;

public class Application {
    public static void main(String[] args) {
        System.out.printf("Application args: %s%n\n", Arrays.stream(args).toList());
        Application app = new Application();
        app.start();
    }

    public void start() {
        System.out.println("Doing serious things");
        throw new OutOfMemoryError("Something went wrong");
    }
}

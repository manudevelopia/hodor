package info.developia.annotation.poc;

import info.developia.hodor.annotation.processor.Hodor;
import info.developia.hodor.annotation.processor.HoldTheDoor;

public class Launcher {
    @Hodor
    public static void main(String[] args) {
        Launcher launcher = new Launcher();
        launcher.launch();
    }

    private void launch() {
        throw new RuntimeException("Hodor!");
    }

    @HoldTheDoor
    static void doSomething(Throwable t) {
        System.out.println("SOS!");
    }
}

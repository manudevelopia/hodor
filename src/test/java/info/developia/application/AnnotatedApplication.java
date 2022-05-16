package info.developia.application;

import info.developia.hodor.annotation.Hodor;
import info.developia.hodor.annotation.HoldTheDoor;

public class AnnotatedApplication {
    @Hodor
    public static void main(String[] args) {
        AnnotatedApplication annotatedApplication = new AnnotatedApplication();
        annotatedApplication.launch();
    }

    private void launch() {
        System.out.println("Doing something important!");
        throw new RuntimeException("Something terrible happened");
    }

    @HoldTheDoor
    static void doSomething(Throwable t) {
        System.out.println("Sending a SOS!");
    }
}

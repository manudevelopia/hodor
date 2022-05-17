package info.developia.application;

import info.developia.hodor.Hodor;

public class NonAnnotatedApplication {
    public static void main(String[] args) {
        Hodor.holdTheDoor((a) -> NonAnnotatedApplication.application(args), (t) -> NonAnnotatedApplication.doSomething(t));
    }

    public static void application(String[] args) {
        NonAnnotatedApplication annotatedApplication = new NonAnnotatedApplication();
        annotatedApplication.launch();
    }

    private void launch() {
        System.out.println("Doing something important!");
        throw new RuntimeException("Something terrible happened");
    }

    static void doSomething(Throwable t) {
        System.out.println("Sending a SOS!");
    }
}

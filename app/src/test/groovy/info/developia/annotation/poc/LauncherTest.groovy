package info.developia.annotation.poc

import info.developia.hodor.annotation.processor.Hodor
import info.developia.hodor.annotation.processor.HoldTheDoor
import info.developia.hodor.lib.HodorException
import spock.lang.Specification

class Application {
    @Hodor
    static void main(String[] args) {
        System.out.println("Hello, world!")
        new Application().foo()
    }

    void foo() {
        throw new RuntimeException("Foo")
    }

    @HoldTheDoor
    static boolean doBeforeExit() {
        return true
    }
}

class LauncherTest extends Specification {
    def "someLibraryMethod returns true"() {
        when:
        LauncherHodor.main(null)
        then:
        HodorException e = thrown()
    }
}

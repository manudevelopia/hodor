package info.developia.annotation.poc

import info.developia.hodor.annotation.processor.Hodor
import info.developia.hodor.annotation.processor.HoldTheDoor
import spock.lang.Ignore
import spock.lang.Specification


class Library {
    @Hodor
    static void main(String[] args) {
        System.out.println("Hello, world!")
        new Library().foo()
    }

    void foo() {
        throw new RuntimeException("Foo")
    }

    @HoldTheDoor
    boolean doBeforeExit() {
        return true
    }
}

@Ignore
class LibraryTest extends Specification {
    def "someLibraryMethod returns true"() {
        setup:
        def lib = new Library()
        when:
        def result = Library.main(null)
        then:
        result == true
    }
}

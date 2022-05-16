package info.developia.hodor

import info.developia.application.Application
import spock.lang.Specification

class ApplicationTest extends Specification {
    ByteArrayOutputStream log

    def setup() {
        log = new ByteArrayOutputStream()
        System.setOut(new PrintStream(log))
    }

    def "Application fail should return HodorException"() {
        when:
        Hodor.holdTheDoor(
                { Application.main([] as String[]) },
                { t -> lastAction(t) })
        then:
        HodorException e = thrown()
        e.message == "HodorException: class java.lang.OutOfMemoryError Something went wrong"
    }

    def "Hodor should not block args"() {
        given:
        List<String> args = ['Osha', 'Bran', 'Rickon', 'Meera Reed', 'Jojen Reed']
        when:
        Hodor.holdTheDoor(
                { Application.main(args as String[]) },
                { t -> lastAction(t) })
        then:
        log.toString().contains(args.toString())
        thrown(HodorException)
    }

    def "Hodor should call last action method before HodorException"() {
        when:
        Hodor.holdTheDoor(
                { Application.main([] as String[]) },
                { t -> lastAction(t) })
        then:
        log.toString().endsWith("Never forget to close the door!\n")
        thrown(HodorException)
    }

    def lastAction(t) {
        println("Never forget to close the door!")
    }
}

package info.developia.hodor

import com.sample.app.Application
import spock.lang.Specification

class ApplicationTest extends Specification {
    ByteArrayOutputStream outContent
    boolean isMetricSent
    ArrayList<String> args = ['Osha', 'Bran', 'Rickon', 'Meera Reed', 'Jojen Reed']

    def setup() {
        outContent = new ByteArrayOutputStream()
        System.setOut(new PrintStream(outContent))
        isMetricSent = false
    }

    def "Application fail should return HodorException"() {
        when:
        Hodor.holdTheDoor(
                { Application.main(args as String[]) },
                { t -> lastAction(t) })
        then:
        HodorException e = thrown()
        e.message == "HodorException: class java.lang.OutOfMemoryError Something went wrong"
    }

    def "Hodor should not block args"() {
        when:
        Hodor.holdTheDoor(
                { Application.main(args as String[]) },
                { t -> lastAction(t) })
        then:
        outContent.toString().contains(args.toString())
        thrown(HodorException)
    }

    def "Hodor should call last action method before HodorException"() {
        when:
        Hodor.holdTheDoor(
                { Application.main(args as String[]) },
                { t -> lastAction(t) })
        then:
        isMetricSent
        outContent.toString().endsWith("Never forget to close the door!\n")
        thrown(HodorException)
    }

    def lastAction(t) {
        isMetricSent = true
        println("Never forget to close the door!")
    }
}

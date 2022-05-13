package info.developia.hodor


import spock.lang.Specification

class ApplicationTest extends Specification {
    ByteArrayOutputStream log
    boolean isMetricSent
    List<String> args

    def setup() {
        args = []
        log = new ByteArrayOutputStream()
        System.setOut(new PrintStream(log))
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
        given:
        args = ['Osha', 'Bran', 'Rickon', 'Meera Reed', 'Jojen Reed']
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
                { Application.main(args as String[]) },
                { t -> lastAction(t) })
        then:
        isMetricSent
        log.toString().endsWith("Never forget to close the door!\n")
        thrown(HodorException)
    }

    def lastAction(t) {
        isMetricSent = true
        println("Never forget to close the door!")
    }
}

package info.developia.annotation.poc

import spock.lang.Ignore
import spock.lang.Specification

@Ignore
class LibrarySpec extends Specification {
    def "someLibraryMethod returns true"() {
        given:
        String type = ""
        when:
        LibraryFactory.createLibrary(type)
        then:
        thrown(RuntimeException)
    }
}

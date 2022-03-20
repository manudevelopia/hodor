package info.developia.hodor

import spock.lang.Specification

class AnnotatedApplicationTest extends Specification {
    def "should return a HodorException explainig what was wrong"() {
        when:
        AnnotatedApplicationHodor.main(null)
        then:
        HodorException e = thrown()
        e.getMessage() == 'HodorException: class java.lang.RuntimeException Something terrible happened'
    }
}

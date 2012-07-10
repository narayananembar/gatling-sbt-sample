import com.excilys.ebi.gatling.core.scenario.configuration.Simulation
import com.excilys.ebi.gatling.core.Predef._
import com.excilys.ebi.gatling.http.Predef._

class SampleSimulation() extends Simulation {
    
    def apply = {

      val headers_1 = Map(
        "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
        "Accept-Charset" -> "ISO-8859-1,utf-8;q=0.7,*;q=0.7"
      )

      val scn = scenario("test")
        .exec(
        http("request_1")
          .get("/")
          .headers(headers_1)
          .check(status.is(200))
      )

      val httpConf = httpConfig.baseURL("http://www.google.com")

      Seq(scn.configure users 10 ramp 2 protocolConfig httpConf)
  }
}

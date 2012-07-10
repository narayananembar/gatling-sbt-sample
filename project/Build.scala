import sbt._
import Keys._
import GatlingPlugin._


object MinimalBuild extends Build {
  val SNAPSHOT = "-SNAPSHOT"

  val appName = "gatling-sbt-sample"
  val buildVersion =  "0.0.1-SNAPSHOT"

  /* OFFICIAL GATLING REPO */
  val gatlingReleasesRepo = "Gatling Releases Repo" at "http://repository.excilys.com/content/repositories/releases"
  val gatling3PartyRepo = "Gatling Third-Party Repo" at "http://repository.excilys.com/content/repositories/thirdparty"

  /* GATLING DEPS */
  val gatlingVersionNumber = "1.2.5"
  val gatlingApp = "com.excilys.ebi.gatling" % "gatling-app" % gatlingVersionNumber //withSources
  val gatlingCore = "com.excilys.ebi.gatling" % "gatling-core" % gatlingVersionNumber //withSources
  val gatlingHttp = "com.excilys.ebi.gatling" % "gatling-http" % gatlingVersionNumber //withSources
  val gatlingRecorder = "com.excilys.ebi.gatling" % "gatling-recorder" % gatlingVersionNumber //withSources
  val gatlingCharts = "com.excilys.ebi.gatling" % "gatling-charts" % gatlingVersionNumber //withSources
  val gatlingHighcharts = "com.excilys.ebi.gatling.highcharts" % "gatling-charts-highcharts" % gatlingVersionNumber //withSources


  val libDependencies = Seq(
     gatlingApp,
     gatlingCore,
     gatlingHttp,
     gatlingRecorder,
     gatlingCharts,
     gatlingHighcharts,

    "be.nextlab" %% "gatling-sbt-test-framework" % "0.0.1-SNAPSHOT"
  )


  lazy val allSettings = 
    Project.defaultSettings ++ 
    GatlingPlugin.gatlingSettings ++ 
    Seq(
      version := buildVersion,
      organization := "be.nextlab",

      resolvers ++= Seq(gatlingReleasesRepo, gatling3PartyRepo),
      
      javacOptions += "-Xlint:unchecked",
      
      libraryDependencies ++= libDependencies,
      
      commands ++= Seq(gatlingTakatak)
    )

  lazy val root = Project(id = appName, base = file(".")).configs(GatlingTest).settings(allSettings: _*)

}

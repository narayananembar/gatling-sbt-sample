import sbt._
import Keys._
import GatlingPlugin._


object MinimalBuild extends Build {
  val SNAPSHOT = "-SNAPSHOT"

  val appName = "gatling-sbt-sample"
  val buildVersion =  "0.0.1-SNAPSHOT"


  /* LOCAL MAVEN REPO */
  val localMavenRepo = "Local Maven Repository" at file(Path.userHome.absolutePath+"/.m2/repository").toURI.toURL.toString

  val gatSbtTestVersion = "0.0.1-SNAPSHOT"

  val cloudbees = "https://repository-andy-petrella.forge.cloudbees.com/"
  val cloudbeesRepo = gatSbtTestVersion match {
      case x if x.endsWith(SNAPSHOT) => x.toLowerCase at cloudbees + "snapshot" + "/"
      case x => x.toLowerCase at cloudbees + "release" + "/"
  }

  val libDependencies = Seq(
    "be.nextlab" %% "gatling-sbt-test-framework" % gatSbtTestVersion % "gatling-test"
  )


  lazy val allSettings = 
    Project.defaultSettings ++ 
    GatlingPlugin.gatlingSettings ++ 
    Seq(
      version := buildVersion,
      organization := "be.nextlab",

      resolvers ++= Seq(cloudbeesRepo),
      
      javacOptions += "-Xlint:unchecked",
      
      libraryDependencies ++= libDependencies,
      
      commands ++= Seq(gatlingTakatak)
    )

  lazy val root = Project(id = appName, base = file(".")).configs(GatlingTest).settings(allSettings: _*)
  
}

import sbt._

object Dependencies {
  object versions {
    lazy val scalatestVersion = "3.2.8"
    lazy val libthriftVersion = "0.10.0"
    lazy val finagleVersion = "21.10.0"
    lazy val tapirVersion = "0.18.3"
    lazy val kindProjectorVersion = "0.13.0"
  }

  lazy val scalaTest = "org.scalatest" %% "scalatest" % versions.scalatestVersion
  lazy val libthrift = "org.apache.thrift" % "libthrift" % versions.libthriftVersion
  lazy val scrooge = "com.twitter" %% "scrooge-core" % versions.finagleVersion
  lazy val finagleThrift = "com.twitter" %% "finagle-thrift" % versions.finagleVersion
  lazy val finagleHttp = "com.twitter" %% "finatra-http-server" % versions.finagleVersion
  lazy val finatraThrift = "com.twitter" %% "finatra-thrift" % versions.finagleVersion
  lazy val injectThriftClient = "com.twitter" %% "inject-thrift-client" % versions.finagleVersion
  
  lazy val tapirJsonCirce = "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % versions.tapirVersion
  lazy val tapirOpenapiCirce = "com.softwaremill.sttp.tapir" %% "tapir-openapi-circe-yaml" % versions.tapirVersion
  lazy val tapirOpenapi = "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs" % versions.tapirVersion
  lazy val tapirSwagger = "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-finatra" % versions.tapirVersion
  lazy val tapirFinatra = "com.softwaremill.sttp.tapir" %% "tapir-finatra-server" % versions.tapirVersion
}

import com.typesafe.sbt.packager.docker._
import Dependencies._

val scala2_13 = "2.13.6"

val compileAndTest = "compile->compile;test->test"

lazy val compilerOptions: Seq[String] = Seq(
  "-deprecation",
  "-unchecked",
  "-encoding",
  "UTF-8",
  "-explaintypes",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:existentials",
  "-language:postfixOps",
  "-Ywarn-dead-code",
  "-Xlint",
  "-Xlint:constant",
  "-Xlint:inaccessible",
  "-Xlint:nullary-unit",
  "-Xlint:type-parameter-shadow",
  "-Xlint:_,-byname-implicit",
  "-Ymacro-annotations",
  "-Wdead-code",
  "-Wnumeric-widen",
  "-Wunused:explicits",
  "-Wunused:implicits",
  "-Wunused:imports",
  "-Wunused:locals",
  "-Wunused:patvars",
  "-Wunused:privates",
  "-Wvalue-discard",
  "-Xlint:deprecation",
  "-Xlint:eta-sam",
  "-Xlint:eta-zero",
  "-Xlint:implicit-not-found",
  "-Xlint:infer-any",
  "-Xlint:nonlocal-return",
  "-Xlint:unused",
  "-Xlint:valpattern"
)

lazy val buildSettings = Seq(
  scalaVersion := scala2_13,
  scalacOptions ++= compilerOptions,
  Test / parallelExecution := false
)

lazy val noPublish = Seq(
  publish := {},
  publishLocal := {},
  publishArtifact := false
)

lazy val commonSettings = Seq(
  libraryDependencies ++= Seq(
    scalaTest % Test
  ),
  addCompilerPlugin(
    ("org.typelevel" %% "kind-projector" % versions.kindProjectorVersion).cross(CrossVersion.full)
  )
)

lazy val $name$ =
  project
    .in(file("."))
    .settings(buildSettings)
    .settings(noPublish)
    .settings(moduleName := "$name$")
    .aggregate(thrift, client, server)

lazy val thrift =
  project
    .in(file("modules/thrift"))
    .settings(buildSettings)
    .settings(commonSettings)
    .settings(moduleName := "$name$-thrift")
    .settings(
      libraryDependencies ++= Seq(
        libthrift,
        scrooge,
        finagleThrift,
        finatraThrift
      )
    )

lazy val httpServer =
  project
    .in(file("modules/server/http"))
    .enablePlugins(JavaAppPackaging, DockerPlugin)
    .settings(buildSettings)
    .settings(commonSettings)
    .settings(
        moduleName := "$name$-http-server",
        libraryDependencies ++= Seq(
          tapirJsonCirce,
          tapirOpenapiCirce,
          tapirOpenapi,
          tapirSwagger,
          tapirFinatra,
          injectThriftClient
        )
    )
    .settings(
      dockerBaseImage := "openjdk:11-jre-slim",
      Docker / packageName := "$name$-http-server",
      Docker / version := "latest"
    )
    .dependsOn(thrift)

lazy val thriftServer =
  project
    .in(file("modules/server/thrift"))
    .enablePlugins(JavaAppPackaging, DockerPlugin)
    .settings(buildSettings)
    .settings(commonSettings)
    .settings(
        moduleName := "$name$-thrift-server"
        libraryDependencies ++= Seq(
          tapirJsonCirce,
          tapirOpenapiCirce,
          tapirOpenapi,
          tapirSwagger,
          tapirFinatra
        )
    )
    .settings(
      dockerBaseImage := "openjdk:11-jre-slim",
      Docker / packageName := "$name$-thrift-server",
      Docker / version := "latest"
    )
    .dependsOn(thrift)
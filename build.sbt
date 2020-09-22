

ThisBuild / scalaVersion := "2.13.3"

name := "mygame.indigo_demo"

lazy val mygame =
  project.in(file("."))
    .enablePlugins(ScalaJSPlugin, SbtIndigo)
    .settings( // Normal SBT settings
      name := "mygame",
      version := "0.0.1",
      scalaVersion := "2.13.3",
      organization := "mygame",
      libraryDependencies ++= Seq(
        "com.lihaoyi"    %%% "utest"      % "0.7.4"  % "test",
        "org.scalacheck" %%% "scalacheck" % "1.14.3" % "test",
        "org.akka-js" %%% "akkajsactor" % "2.2.6.5",
      ),
      testFrameworks += new TestFramework("utest.runner.Framework")
    )
    .settings( // Indigo specific settings
      showCursor := true,
      title := "My Game - Made with Indigo",
      gameAssetsDirectory := "assets",
      windowStartWidth := 412,
      windowStartHeight := 896,
      libraryDependencies ++= Seq(
        "io.indigoengine" %%% "indigo-json-circe" % "0.3.0",
        "io.indigoengine" %%% "indigo"            % "0.3.0",
        "io.indigoengine" %%% "indigo-extras"     % "0.3.0"
      )
    )

addCommandAlias("buildGame", ";compile;fastOptJS;indigoBuild")
addCommandAlias("runGame", ";compile;fastOptJS;indigoRun")
addCommandAlias("buildGameFull", ";compile;fullOptJS;indigoBuildFull")
addCommandAlias("runGameFull", ";compile;fullOptJS;indigoRunFull")

resolvers += Resolver.jcenterRepo
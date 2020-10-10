

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
        "org.typelevel" %% "cats-core" % "2.0.0"
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
        "io.indigoengine" %%% "indigo-json-circe" % "0.4.1-SNAPSHOT",
        "io.indigoengine" %%% "indigo"            % "0.4.1-SNAPSHOT",
        "io.indigoengine" %%% "indigo-extras"     % "0.4.1-SNAPSHOT"
      )
    )

addCommandAlias("buildGame", ";compile;fastOptJS;indigoBuild")
addCommandAlias("runGame", ";compile;fastOptJS;indigoRun")
addCommandAlias("buildGameFull", ";compile;fullOptJS;indigoBuildFull")
addCommandAlias("runGameFull", ";compile;fullOptJS;indigoRunFull")
addCommandAlias("buildGameFullCordova", ";compile;fullOptJS;indigoBuildFull;indigoCordovaBuildFull")

resolvers += Resolver.jcenterRepo
import sbt.Keys.libraryDependencies

name := "kilim-experiment"

version := "0.1"

scalaVersion := "2.13.0"

lazy val core = project
  .in(file("core"))
  .settings(
    libraryDependencies += "org.db4j" % "kilim" % "2.0.2",
    libraryDependencies += "com.lihaoyi" %% "requests" % "0.2.0"
  )

lazy val examples = project
  .in(file("examples"))
  .dependsOn(core)


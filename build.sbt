import sbt.Keys.libraryDependencies

name := "kilim-experiment"

version := "0.1"

scalaVersion := "2.13.4"

lazy val `fiber-core` = project

lazy val `fiber-http-framework` = project
  .dependsOn(`fiber-core`)

lazy val examples = project
  .dependsOn(`fiber-http-framework`)
  .settings(
    libraryDependencies += "com.lihaoyi" %% "requests" % "0.2.0"
  )

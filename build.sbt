name := "sbt-mima-autoversion"
organization := "net.rouly"
description := "Automatically version Scala projects based on the lowest possible version according to Semantic Versioning using Migration Manager to determine forwards and backwards compatibility."

licenses += ("The Apache Software License, Version 2.0", url(
  "https://www.apache.org/licenses/LICENSE-2.0.txt"
))

homepage := Some(url("https://github.com/jrouly/sbt-mima-autoversion"))

developers += Developer(
  id = "jrouly",
  name = "Jean Michel Rouly",
  email = "michel@rouly.net",
  url = url("https://github.com/jrouly")
)

scmInfo := Some(
  ScmInfo(
    browseUrl = url("https://github.com/jrouly/sbt-mima-autoversion"),
    connection = "scm:git:git://github.com/jrouly/sbt-mima-autoversion.git",
    devConnection = "scm:git:ssh://git@github.com:jrouly/sbt-mima-autoversion.git"
  )
)

enablePlugins(SbtPlugin)
sbtPlugin := true

sbtVersion := "1.4.9"

publishMavenStyle := false
artifactory := artifactoryCloud("jrouly")

scriptedBufferLog := false
scriptedLaunchOpts ++= Seq("-Xmx1024M", "-server", "-Dplugin.version=" + version.value)

Global / onChangedBuildSource := ReloadOnSourceChanges

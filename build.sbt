name := "sbt-mima-autoversion"
organization := "net.rouly"
description := "Automatically version Scala projects based on the lowest possible version according to Semantic Versioning using Migration Manager to determine forwards and backwards compatibility."

homepage := Some(url("https://github.com/jrouly/sbt-mima-autoversion"))
licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))

publishMavenStyle := false
artifactory := artifactoryCloud("jrouly")

enablePlugins(SbtPlugin)
sbtPlugin := true

// https://github.com/sbt/sbt/issues/5049
crossSbtVersions := Vector("1.1.6")


scriptedBufferLog := false
scriptedLaunchOpts ++= Seq("-Xmx1024M", "-server", "-Dplugin.version=" + version.value)

Global / onChangedBuildSource := ReloadOnSourceChanges

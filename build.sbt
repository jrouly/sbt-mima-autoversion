name := "sbt-mima-autoversion"
organization := "net.rouly"
description := "Automatically version Scala projects based on the lowest possible version according to Semantic Versioning using Migration Manager to determine forwards and backwards compatibility."

scriptedBufferLog := false
scriptedLaunchOpts ++= Seq("-Xmx1024M", "-server", "-Dplugin.version=" + version.value)

Global / onChangedBuildSource := ReloadOnSourceChanges

enablePlugins(SbtPlugin)

crossScalaVersions := Seq("2.12.10")

pluginCrossBuild / sbtVersion := {
  scalaBinaryVersion.value match {
    case "2.12" => "1.1.6" // https://github.com/sbt/sbt/issues/5049
  }
}

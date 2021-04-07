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

// Dependencies
addSbtPlugin("com.typesafe" % "sbt-mima-plugin" % "0.8.1")
addSbtPlugin("com.github.sbt" % "sbt-release" % "1.0.15")
addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "1.0.0")

libraryDependencies += "com.vdurmont" % "semver4j" % "3.1.0"
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.15.3" % "test"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.6" % "test"
libraryDependencies += "org.scalatestplus" %% "scalacheck-1-15" % "3.2.6.0" % "test"

package net.rouly.sbt.autoversion

import com.typesafe.sbt.{GitPlugin, SbtGit}
import com.typesafe.tools.mima.plugin.MimaKeys._
import com.typesafe.tools.mima.plugin.MimaPlugin
import com.vdurmont.semver4j.Semver
import com.vdurmont.semver4j.Semver.SemverType
import net.rouly.sbt.autoversion.model.Tag
import sbt.Keys._
import sbt._
import sbt.plugins.JvmPlugin
import sbtrelease.ReleasePlugin
import sbtrelease.ReleasePlugin.autoImport.releaseVersion
import sbtrelease.Version.Bump

import scala.util.Properties

object MimaAutoVersionPlugin extends AutoPlugin {

  override def requires: Plugins = JvmPlugin && MimaPlugin && ReleasePlugin && GitPlugin

  override def trigger: PluginTrigger = allRequirements

  object autoImport extends Keys
  import autoImport._

  override def projectSettings: Seq[Def.Setting[_]] = Seq(
    mimaAutoVersionLatestTag := findLatestTag.value,
    mimaAutoVersionTagNameCleaner := { _.stripPrefix("v") },
    mimaAutoVersionSuggestedBump := suggestBump.value,
    mimaAutoVersionAttemptBumps := Vector(Bump.Bugfix, Bump.Minor, Bump.Major),
    releaseVersion := MimaAutoVersion.setReleaseVersion(mimaAutoVersionSuggestedBump.value),
    mimaPreviousArtifacts := previousTaggedArtifact.value
  )

  private lazy val findLatestTag = Def.task {
    val gitTags = runGit("tag", "--list").value
    val clean = mimaAutoVersionTagNameCleaner.value
    val versions = gitTags.map(tag => Tag(tag, new Semver(clean(tag), SemverType.LOOSE)))
    versions.sortBy(_.version).lastOption
  }

  // The current project module ID, but at the latest tagged version.
  private lazy val previousTaggedArtifact = Def.task {
    val moduleId = projectID.value
    val latestVersion = mimaAutoVersionLatestTag.value.map(_.version.toString)
    Set(latestVersion.map(moduleId.withRevision)).flatten
  }

  private lazy val suggestBump = Def.task {
    Bump.default // TODO
  }

  private def runGit(args: String*): Def.Initialize[Task[Array[String]]] = Def.task {
    SbtGit.GitKeys.gitRunner
      .value(args: _*)(file("."), Logger.Null)
      .split(Properties.lineSeparator)
      .filter(_.trim.nonEmpty)
  }

}

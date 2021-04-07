package net.rouly.sbt.autoversion

import com.typesafe.sbt.{GitPlugin, SbtGit}
import com.typesafe.tools.mima.plugin.MimaKeys._
import com.typesafe.tools.mima.plugin.MimaPlugin
import com.vdurmont.semver4j.Semver
import com.vdurmont.semver4j.Semver.SemverType
import net.rouly.sbt.autoversion.model.{Tag, bumpOrdering}
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
    mimaAutoVersionDefaultBump := Some(Bump.Bugfix),
    releaseVersion := MimaAutoVersion.setReleaseVersion(mimaAutoVersionSuggestedBump.value),
    mimaPreviousArtifacts := previousTaggedArtifact.value,
    mimaCheckDirection := "both"
  )

  private lazy val findLatestTag = Def.task {
    val gitTags = runGit("tag", "--list").value
    val clean = mimaAutoVersionTagNameCleaner.value
    val versions = gitTags.map(tag => Tag(tag, new Semver(clean(tag), SemverType.LOOSE)))
    versions.sortBy(_.version).lastOption
  }

  // The current project module ID, but at the latest tagged version.
  private lazy val previousTaggedArtifact = Def.task {
    val latestVersion = mimaAutoVersionLatestTag.value.map(_.version.toString)
    // TODO: Using projectID doesn't work with explicit Artifacts.
    val moduleId = latestVersion.map { version => organization.value %% name.value % version }
    Set(moduleId).flatten
  }

  private lazy val suggestBump = Def.task {
    val default = mimaAutoVersionDefaultBump.value
    val bumps: Iterable[Bump] = mimaFindBinaryIssues.value.values.map {
      case (_ :: _, _) => Bump.Major
      case (Nil, _ :: _) => Bump.Minor
      case (Nil, Nil) => Bump.Bugfix
    }

    if (bumps.nonEmpty) bumps.max
    else
      default match {
        case None => sys.error("Unable to automatically determine an appropriate version bump.")
        case Some(bump) => bump
      }
  }

  private def runGit(args: String*): Def.Initialize[Task[Array[String]]] = Def.task {
    SbtGit.GitKeys.gitRunner
      .value(args: _*)(file("."), Logger.Null)
      .split(Properties.lineSeparator)
      .filter(_.trim.nonEmpty)
  }

}

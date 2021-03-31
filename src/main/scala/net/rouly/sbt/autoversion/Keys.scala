package net.rouly.sbt.autoversion

import net.rouly.sbt.autoversion.model.Tag
import sbt._
import sbtrelease.Version.Bump

trait Keys {
  val mimaAutoVersionLatestTag =
    taskKey[Option[Tag]]("Latest semver version from Git tags.")

  val mimaAutoVersionTagNameCleaner =
    settingKey[String => String]("Cleans the git tag to extract only the version.")

  val mimaAutoVersionSuggestedBump =
    taskKey[Bump]("Version bump computed by sbt-mima-autoversion.")

  val mimaAutoVersionAttemptBumps =
    taskKey[Seq[Bump]]("Ordered list of version bumps to attempt.")
}

object Keys extends Keys

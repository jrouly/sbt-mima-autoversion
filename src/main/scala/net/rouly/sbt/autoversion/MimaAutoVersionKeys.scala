package net.rouly.sbt.autoversion

import net.rouly.sbt.autoversion.model.Tag
import sbt._
import sbtrelease.Version.Bump

trait MimaAutoVersionKeys {
  val mimaAutoVersionLatestTag =
    settingKey[Option[Tag]]("Latest semver version from Git tags.")

  val mimaAutoVersionTagNameCleaner =
    settingKey[String => String]("Cleans the git tag to extract only the version.")

  val mimaAutoVersionSuggestedBump =
    taskKey[Bump]("Version bump computed by sbt-mima-autoversion.")

  val mimaAutoVersionDefaultBump =
    settingKey[Option[Bump]](
      "Default version bump if sbt-mima-autoversion is unable to suggest one."
    )
}

object MimaAutoVersionKeys extends MimaAutoVersionKeys

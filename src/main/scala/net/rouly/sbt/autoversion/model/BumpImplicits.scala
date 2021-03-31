package net.rouly.sbt.autoversion.model

import sbtrelease.Version.Bump

trait BumpImplicits {

  implicit val bumpOrdering: Ordering[Bump] = new Ordering[Bump] {
    override def compare(x: Bump, y: Bump): Int = {
      if (x == y) 0
      else {
        (x, y) match {
          case (_, Bump.Major) => -1
          case (Bump.Major, _) => 1
          case (Bump.Bugfix, Bump.Minor) => -1
          case (_, Bump.Bugfix) => 1
          case _ => throw new RuntimeException(s"Unhandled case: ($x,$y)")
        }
      }
    }
  }

}

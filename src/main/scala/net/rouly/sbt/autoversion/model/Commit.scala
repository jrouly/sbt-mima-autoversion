package net.rouly.sbt.autoversion.model

case class Commit(
  sha: String,
  msg: String
)

object Commit {
  def apply(commitLine: String): Commit = {
    def parts: Array[String] = commitLine.split(" ")
    Commit(parts(0), parts.drop(1).mkString(" "))
  }
}

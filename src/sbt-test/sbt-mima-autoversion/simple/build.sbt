import ReleaseTransformations._

isSnapshot := true

// Satisfy the publishArtifacts precondition.
publishTo := Some(Resolver.url("dummy"))

// Only publish artifacts locally for testing.
releasePublishArtifactsAction := publishLocal.value

// Default sbt-release process, minus git push.
releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  publishArtifacts,
  setNextVersion,
  commitNextVersion
  // pushChanges
)

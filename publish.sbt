organizationHomepage := Some(url("https://rouly.net"))

homepage := Some(url("https://github.com/jrouly/sbt-mima-autoversion"))

licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))

scmInfo := Some(
  ScmInfo(
    url("https://github.com/jrouly/sbt-mima-autoversion"),
    "scm:git@github.com:jrouly/sbt-mima-autoversion.git"
  )
)

developers := List(
  Developer(
    id = "jrouly",
    name = "Michel Rouly",
    email = "michel@rouly.net",
    url = url("https://michel.rouly.net")
  )
)

pomIncludeRepository := { _ => false }

pgpSigningKey := Some("0xFA8B833314500A89")

credentials += Credentials(
  realm = "Sonatype Nexus Repository Manager",
  host = "s01.oss.sonatype.org",
  userName = sys.env.getOrElse("SONATYPE_USER", "username"),
  passwd = sys.env.getOrElse("SONATYPE_PASS", "password")
)

publishTo := {
  val nexus = "https://s01.oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

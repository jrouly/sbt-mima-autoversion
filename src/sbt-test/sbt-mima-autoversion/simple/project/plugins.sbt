sys.props.get("plugin.version") match {
  case Some(x) => addSbtPlugin("net.rouly" % "sbt-mima-autoversion" % x)
  case _ => sys.error("The system property 'plugin.version' is not defined.")
}

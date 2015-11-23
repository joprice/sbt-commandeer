
name := "sbt-commandeer"

organization := "com.joprice"

licenses := Seq(("MIT License", url("http://opensource.org/licenses/MIT")))

scalacOptions := Seq("-unchecked", "-deprecation", "-feature", "-encoding", "utf8")

sbtPlugin := true

publishMavenStyle := false

publishArtifact in Test := false

ScriptedPlugin.scriptedSettings

scriptedSettings

scriptedLaunchOpts ++= Seq("-Xmx2G", "-Dplugin.version=" + version.value)

bintrayOrganization := Some("joprice")

bintrayRepository := "maven"

addSbtPlugin("me.lessis" % "bintray-sbt" % "0.3.0")


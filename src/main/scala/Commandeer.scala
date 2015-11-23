
import bintray.BintrayPlugin
import BintrayPlugin._
import bintray.BintrayKeys._
import sbt._
import Keys._
import complete.Parser
import complete.DefaultParsers._

object Commandeer {
  def apply(state: State, args: (String, String)) = {
    val (organization, repository) = (args)
    val extracted = Project.extract(state)
    import extracted._
    val newSettings = session.mergeSettings ++ extracted.structure.allProjectRefs.flatMap { proj =>
      bintraySettings ++ Seq(
        bintrayRepository in proj := repository,
        bintrayOrganization in proj := Some(organization)
      )
    }
    extracted.append(newSettings, state)
  }

}

//TODO: prevent having to rerun when version is modified
/*
 * Start a repl, run "commandeer", then "publish" to publish to bintray.
 *
 * > commandeer joprice maven
 *
 * If any settings are modified in the session, e.g., "set version := "0.1.1", the command needs to be rerun.
 *
 **/
object CommandeerPlugin extends AutoPlugin {

  lazy val commandeerCommand = {
    val name ="commandeer"
    val briefHelp = "Modifies the build to publish to a different bintray repository"
    val parser: Parser[(String, String)] = {
      (token(Space) ~> token(StringBasic, "organization")) ~ (token(Space) ~> token(StringBasic, "repository"))
    }
    val help = s"$briefHelp This is useful when the maintainer of a library forgoes publishing a much-needed fix and you feel the need to take it into your own hands."
    Command(name, (name, briefHelp), help)(_ => parser)(Commandeer.apply)
  }

  override def requires = BintrayPlugin

  override lazy val projectSettings = Seq(commands += commandeerCommand)

}


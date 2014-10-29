import sbt._
import Keys._
import java.io.File
import com.typesafe.sbt.osgi.SbtOsgi._

/**
 * ScalaBuff SBT build file.
 *
 * Useful SBT commands:
 *
 *      run (arguments)             Runs ScalaBuff inside SBT with the specified arguments.
 *      test                        Runs the tests.
 *      package                     Generates the main ScalaBuff compiler .JAR.
 *      update-test-resources       Regenerates the test resources using ScalaBuff.
 *
 *      project compiler            Switches to the compiler project (default).
 *      project runtime             Switches to the runtime project.
 *
 */
object ScalaBuffBuild extends Build {

	lazy val buildSettings = Seq(
		name := "ScalaBuff",
		organization := "net.sandrogrzicic",
		version := "1.3.9.2",
		scalaVersion := "2.11.2",
		logLevel := Level.Info
	)

	override lazy val settings = super.settings ++ buildSettings

	lazy val defaultSettings = Defaults.defaultSettings ++ Seq(
    bintray.Keys.bintrayOrganization in bintray.Keys.bintray := Some("actor"),

    bintray.Keys.repository in bintray.Keys.bintray := "maven",
    
    licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),

		resolvers ++= Seq(
			"Akka Maven Repository" at "http://akka.io/repository",
			"Typesafe Maven Repository" at "http://repo.typesafe.com/typesafe/releases/",
			"Sonatype OSS Repository" at "https://oss.sonatype.org/content/groups/public/"
		),
		
		libraryDependencies ++= Seq(
			"com.google.protobuf" % "protobuf-java" % "2.5.0",
			"org.scala-lang" % "scala-reflect" % scalaVersion.value
		) ++ (CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, scalaMajor)) if scalaMajor >= 10 =>
        Seq("org.scalatest" %% "scalatest" % "2.1.5" % "test") ++ (
          if (scalaMajor >= 11) Seq("org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.1") else Seq()
        )
      case Some((2, scalaMajor)) if scalaMajor == 9 =>
        Seq("org.scalatest" %% "scalatest" % "1.9.2" % "test")
      case _ =>
        Seq()
    }),

		crossScalaVersions ++= Seq("2.10.4", "2.11.2"),
		
		scalacOptions ++= Seq("-encoding", "utf8", "-unchecked", "-deprecation", "-Xlint"),
		scalacOptions ++=
		  (CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, scalaMajor)) if scalaMajor >= 10 =>
          Seq("-Xlog-reflective-calls")
        case _ =>
         Seq()
       }),

		javacOptions ++= Seq("-encoding", "utf8", "-Xlint:unchecked", "-Xlint:deprecation"),

		parallelExecution in GlobalScope := true,

		scalaSource in Compile <<= baseDirectory(_ / "src/main"),
		scalaSource in Test <<= baseDirectory(_ / "src/test"),

		javaSource in Compile <<= baseDirectory(_ / "src/main"),
		javaSource in Test <<= baseDirectory(_ / "src/test"),

		compileOrder := CompileOrder.Mixed,
		
		credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")
	) ++ bintray.Plugin.bintraySettings

	lazy val compilerProject = Project(
		id = "scalabuff-compiler",
		base = file("scalabuff-compiler"),
		dependencies = Seq(runtimeProject % "test->compile"),
		settings = defaultSettings ++ Seq(
			mainClass in (Compile, run) := Some("net.sandrogrzicic.scalabuff.compiler.ScalaBuff"),
			mainClass in (Compile, packageBin) := Some("net.sandrogrzicic.scalabuff.compiler.ScalaBuff"),
			fullRunTask(TaskKey[Unit]("update-test-resources"), Compile, "net.sandrogrzicic.scalabuff.test.UpdateTestResources")
		) // ++ osgiSettings
	)

	lazy val runtimeProject = Project(
		id = "scalabuff-runtime",
		base = file("scalabuff-runtime"),
		settings = defaultSettings
	)

  override val rootProject = Option(compilerProject)

}

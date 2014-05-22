import sbt._
import sbt.Keys._
import Dependencies._


object MemoryClientBuild extends Build {

	lazy val root = Project(id = "memory-client", base = file("."),
		settings = Project.defaultSettings ++ Seq (
			name := "memory-client",
			version := "1.0",
		    scalaVersion := "2.10.3",

			resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/",

			libraryDependencies ++= core ++ tests
	    )
	)
}

object Dependencies {
	val core = Seq(
		"org.scalaz"                %% "scalaz-core"            % "7.0.6",
		"play"                      %% "play"                   % "2.1.0"               // TODO use specific http-client library & not all play + json library
	)

	val tests = Seq(
		"org.scalatest"             %% "scalatest"              % "2.1.0"                % "test",
		"org.mockito"               %  "mockito-all"            % "1.9.5"                % "test",
		"com.github.simplyscala"    %% "simplyscala-server"     % "0.5"                  % "test"
	)
}
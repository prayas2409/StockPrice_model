
import AssemblyPlugin._ // put this at the top of the file,leave the next line blank

assemblySettings

name := "StockMarket"

version := "0.1"

scalaVersion := "2.12.8"

//import org.apache.maven.model.Build

//libraryDependencies += "junit" % "junit" % "4.12" % "test"

//libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.4.3"

// https://mvnrepository.com/artifact/org.apache.spark/spark-sql
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.3"

// https://mvnrepository.com/artifact/org.apache.spark/spark-core
libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.3"

libraryDependencies += "org.apache.hadoop" % "hadoop-aws" % "3.1.2"

libraryDependencies += "org.apache.hadoop" % "hadoop-common" % "3.1.2"

libraryDependencies += "org.apache.hadoop" % "hadoop-mapreduce-client-core"  % "3.1.2"

dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-core" % "2.8.7"

dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.7"

dependencyOverrides += "com.fasterxml.jackson.module" % "jackson-module-scala_2.12" % "2.8.7"

resolvers += Resolver.url("bintray-sbt-plugins", url("http://dl.bintray.com/sbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)

resolvers += Resolver.bintrayIvyRepo("com.eed3si9n", "sbt-plugins")

// https://mvnrepository.com/artifact/org.apache.commons/commons-io
libraryDependencies += "org.apache.commons" % "commons-io" % "1.3.2"

// https://mvnrepository.com/artifact/org.apache.commons/commons-exec
libraryDependencies += "org.apache.commons" % "commons-exec" % "1.3"

lazy val commonSettings = Seq(
  organization := "com.bridgelabz",
  version := "0.1.0-SNAPSHOT"
)

// set the main class for packaging the main jar
mainClass in (Compile, packageBin) := Some("com.stockprice.stockmain")

// set the main class for the main 'sbt run' task
mainClass in (Compile, run) := Some("com.stockprice.stockmain")

//lazy val app = (project in file(".")).
//  settings(commonSettings: _*).
//  settings(
//    name := "fat-jar-test"
//  ).
//  enablePlugins(AssemblyPlugin)

resolvers in Global ++= Seq(
  "Sbt plugins"                   at "https://dl.bintray.com/sbt/sbt-plugin-releases",
  "Maven Central Server"          at "http://repo1.maven.org/maven2",
  "TypeSafe Repository Releases"  at "http://repo.typesafe.com/typesafe/releases/",
  "TypeSafe Repository Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/"
)

lazy val root = (project in file(".")).
  settings(
    name := "stockMarket",
    version := "1.0",
    scalaVersion := "2.12.8",
    Compile / mainClass := Some("com.stockprice.stockmain")
  )
  .settings(commonSettings: _*)
  .enablePlugins(AssemblyPlugin)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
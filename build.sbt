name := "text-summarizer"
version := "0.1"

// Required Scala version for the Spark 2.4.0 version
scalaVersion := "2.11.12"

// Import project dependencies
// groupID % artifactID % revision
libraryDependencies := Seq(

  // Spark dependencies
  "org.apache.spark" %% "spark-sql" % "2.4.0",
  "org.apache.spark" %% "spark-yarn" % "2.4.0",

  // REST API dependencies
  "com.typesafe.akka" %% "akka-http" % "10.1.0-RC1",
  "com.typesafe" % "config" % "1.3.0"
)
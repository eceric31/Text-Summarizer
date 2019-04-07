name := "text-summarizer"
version := "0.1"

// Required Scala version for the Spark 2.4.0 version
scalaVersion := "2.11.12"

mainClass in Compile := Some("com.text_summarizer.TextSummarizer")

// Import project dependencies
// groupID % artifactID % revision
libraryDependencies := Seq(

  // Spark dependencies
  "org.apache.spark" %% "spark-sql" % "2.4.0",
  "org.apache.spark" %% "spark-yarn" % "2.4.0",

  // REST API dependencies
  "com.typesafe.akka" %% "akka-stream" % "2.5.12",
  "com.typesafe.akka" %% "akka-http" % "10.1.0-RC1",
  "com.typesafe" % "config" % "1.3.0",

  // Stanford CoreNLP
  "edu.stanford.nlp" % "stanford-corenlp" % "3.4",
  "edu.stanford.nlp" % "stanford-corenlp" % "3.4" classifier "models",

  // Stanford CoreNLP Spark wrapper
  "databricks" % "spark-corenlp" % "0.3.1-s_2.11",

  // Reader
  "com.github.tototoshi" %% "scala-csv" % "1.3.5"
)
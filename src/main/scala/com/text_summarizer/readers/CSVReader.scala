package com.text_summarizer.readers

import java.io.File
import java.util.UUID

import com.github.tototoshi.csv._
import com.text_summarizer.ApplicationConfiguration
import com.text_summarizer.models.{Article, SummarizerConfiguration}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
  * Reads a csv file into an RDD.
  */
class CSVReader(file: File, configuration: SummarizerConfiguration, session: SparkSession) extends Readable {

  /**
    * Reads the file and creates an RDD of it.
    *
    * @return a [[RDD]] of [[Article]]
    */
  override def read: RDD[Article] = {

    val reader = CSVReader.open(file)

    // Note: Streaming the data would be much better (since huge files might not fit into memory)
    val mappedData = reader.allWithHeaders()
      .filter(_ != null)
      .map(row => {
        Article(
          row.getOrElse(configuration.csvConfiguration.projections("author"), null),
          row.getOrElse(configuration.csvConfiguration.projections("title"), null),
          row.getOrElse(configuration.csvConfiguration.projections("date"), null),
          row.getOrElse(configuration.csvConfiguration.projections("content"), null)
        )
      })
      .filter(_.content != null)
      .toArray

    reader.close

    val result = session.sparkContext.parallelize(mappedData)

    // Persist into HDFS for future use
    persistToHDFS(
      result,
      buildPath(file.getName.split('.')(0))
    )

    result
  }

  private[this] def buildPath(fileName: String): String = {
    ApplicationConfiguration.hdfsConf.get("fs.defaultFS") +
      ApplicationConfiguration.hdfsConf.get("hdfs.articles.location") +
      fileName +
      UUID.randomUUID()
  }
}

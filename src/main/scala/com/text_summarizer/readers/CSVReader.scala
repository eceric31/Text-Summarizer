package com.text_summarizer.readers

import java.io.File

import com.text_summarizer.ApplicationConfiguration
import com.text_summarizer.models.{Article, SummarizerConfiguration}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Dataset, SparkSession}

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

/**
  * Reads a csv file into an RDD.
  */
class CSVReader(file: File, configuration: SummarizerConfiguration, session: SparkSession) extends Readable {

  /**
    * Reads the file and creates a dataset of it.
    *
    * @return a [[Dataset]] of [[Article]]
    */
  override def read: RDD[Article] = {
    val rows = Source.fromFile(file)

    val arrayBuffer = ArrayBuffer[Article]()

    for (row <- rows.getLines.drop(1)) {
      if (row != null) {

        val split = row.split(configuration.csvConfiguration.separator)
        arrayBuffer += Article(
          resolveColumnValue(split, "author"),
          resolveColumnValue(split,"title"),
          resolveColumnValue(split,"date"),
          resolveColumnValue(split,"content")
        )
      }
    }

    val result = session.sparkContext.parallelize(arrayBuffer)

    // Persist into HDFS for future use
    persistToHDFS(result, ApplicationConfiguration.dataLocation + file.getName.split(".").take(1))

    result
  }

  private[this] def resolveColumnValue(
    row: Array[String],
    columnName: String
  ): String = row(configuration.csvConfiguration.projections(columnName))
}

package com.text_summarizer

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import collection.JavaConverters._

object SparkSessionProvider {

  private[this] lazy val sparkConf = {
    val configuration: SparkConf = new SparkConf

    // Copy properties of default spark configuration into spark conf
    ApplicationConfiguration
      .sparkConf
      .entrySet
      .asScala
      .foreach { entry => configuration.set(entry.getKey, entry.getValue.unwrapped.toString) }

    configuration
  }

  val sparkSession: SparkSession = SparkSession.builder.config(sparkConf).getOrCreate

  def getInstance: SparkSession = sparkSession
}

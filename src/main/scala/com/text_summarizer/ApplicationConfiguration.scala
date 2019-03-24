package com.text_summarizer

import java.io.File

import akka.ConfigurationException
import org.apache.commons.lang3.StringUtils
import com.typesafe.config._
import org.apache.hadoop.conf.Configuration

import scala.collection.JavaConverters._

/**
  * The application configuration.
  */
object ApplicationConfiguration {

  private[this] val DEFULT_API_PORT: Int = 8100
  private[this] var configuration: Config = ConfigFactory.load()

  ////////////////////////////////////////////////////
  //GLOBAL CONFIGURATION PROPERTIES
  ////////////////////////////////////////////////////

  def sparkConf: Config = {
    if (configuration.hasPath("spark.configuration")) {
      configuration.getConfig("spark.configuration")
    } else {
      ConfigFactory.empty
    }
  }

  def apiConf: Config = {
    if (configuration.hasPath("text.summarizer.api")) {
      configuration.getConfig("text.summarizer.api")
    } else {
      ConfigFactory.empty
    }
  }

  def hdfsConf: Configuration = {
    if (sparkConf.hasPath("text.summarizer.hdfs.configuration")) {
      val rawHDFSConfiguration = sparkConf.getConfig("text.summarizer.hdfs.configuration")
      val hdfsConfiguration = new Configuration()

      for (entry <- rawHDFSConfiguration.entrySet.asScala) {
        hdfsConfiguration.set(entry.getKey, entry.getValue.unwrapped.toString)
      }

      hdfsConfiguration
    } else {
      throw new ConfigurationException("HDFS configuration is required, but none was provided.")
    }
  }

  ////////////////////////////////////////////////////
  //SPARK CONFIGURATION PROPERTIES
  ////////////////////////////////////////////////////

  def sparkApplicationName: String = {
    if (sparkConf.hasPath("spark.application.name")) {
      sparkConf.getString("spark.application.name")
    } else {
      StringUtils.EMPTY
    }
  }

  def sparkMaster: String = {
    if (sparkConf.hasPath("spark.master")) {
      sparkConf.getString("spark.master")
    } else {
      StringUtils.EMPTY
    }
  }

  def sparkDeployMode: String = {
    if (sparkConf.hasPath("spark.submit.deployMode")) {
      sparkConf.getString("spark.submit.deployMode")
    } else {
      StringUtils.EMPTY
    }
  }

  ////////////////////////////////////////////////////
  //API CONFIGURATION PROPERTIES
  ////////////////////////////////////////////////////

  def apiHost: String = {
    if (apiConf.hasPath("host")) {
      apiConf.getString("host")
    } else {
      StringUtils.EMPTY
    }
  }

  def apiPort: Int = {
    if (apiConf.hasPath("port")) {
      apiConf.getInt("port")
    } else {
      DEFULT_API_PORT
    }
  }

  /**
    * Loads the configuration at startup.
    *
    * Configuration initialization consists of loading an external configuration from a file provided in the startup parameters
    * (in case such is provided). The argument property is named `--conf-file`.
    *
    * @param args application launch parameters
    */
  def init(args: Array[String]): Unit = {
    loadExternalConfiguration(args, "--conf-file")
    applyOverrides(args)
  }

  /**
    * Loads an external configuration file, if one is provided.
    *
    * @param launchProperties the launch properties
    */
  private[this] def loadExternalConfiguration(launchProperties: Array[String], propertyPrefix: String): Unit = {
    val confFileParam = launchProperties.find(_.startsWith(propertyPrefix))

    if (confFileParam.isDefined) {
      configuration = ConfigFactory.parseFile(new File(confFileParam.get.substring(propertyPrefix.length)))
    }
  }

  /**
    * Overrides or adds properties contained in the external configuration file, and not in the application.conf.
    *
    * @param overrides the application launch overrides
    */
  private[this] def applyOverrides(overrides: Array[String]): Unit = {

  }
}

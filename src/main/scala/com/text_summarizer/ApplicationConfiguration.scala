package com.text_summarizer

import org.apache.commons.lang3.StringUtils
import com.typesafe.config._

object ApplicationConfiguration {

  private[this] val DEFULT_API_PORT: Int = 8100
  private[this] val configuration: Config = ConfigFactory.load("application.conf")

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
}

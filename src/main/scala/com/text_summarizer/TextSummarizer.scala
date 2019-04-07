package com.text_summarizer

import com.text_summarizer.controllers.HttpService

object TextSummarizer {

  def main(args: Array[String]): Unit = {

    // Initialize the application configuration
    ApplicationConfiguration.init(args)

    // Start the http listening thread
    HttpService.start()
  }

}

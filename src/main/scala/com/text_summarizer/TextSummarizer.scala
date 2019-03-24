package com.text_summarizer

import com.text_summarizer.models.Article
import com.text_summarizer.pipelines.TextPreprocessor

object TextSummarizer {

  def main(args: Array[String]): Unit = {

    // Initialize the application configuration
    ApplicationConfiguration.init(args)

    val preprocessor = new TextPreprocessor

    preprocessor.preprocess(
      SparkSessionProvider
        .getInstance
        .sparkContext
        .textFile("/Users/edin/Desktop/summarizer-test.csv")
        .map(Article(null, null, null, _))
    )
  }

}

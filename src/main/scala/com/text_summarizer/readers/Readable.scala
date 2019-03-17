package com.text_summarizer.readers

import com.text_summarizer.models.Article
import org.apache.spark.rdd.RDD

/**
  * The Readable trait.
  *
  * Any file reader implementation must extend this trait.
  */
trait Readable {

  /**
    * Reads the file and creates an RDD of it.
    *
    * @return a [[RDD]] of [[Article]]
    */
  def read: RDD[Article]

  protected[this] def persistToHDFS(articles: RDD[Article], path: String): Unit = articles.saveAsTextFile(path)
}

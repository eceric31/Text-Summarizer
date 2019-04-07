package com.text_summarizer.pipelines

import java.util.Properties

import com.text_summarizer.models.Article
import edu.stanford.nlp.ling.CoreAnnotations
import edu.stanford.nlp.pipeline.StanfordCoreNLP
import org.apache.spark.rdd.RDD

import scala.io.Source

/**
  * Used for pre-processing text.
  */
class TextPreprocessor {

  /**
    * Load stop words from a resource file.
    */
  private[this] val stopWords: Set[String] = Source.fromInputStream(
      getClass.getResourceAsStream("/stop_words.csv")
    ).getLines.toSet

  /**
    * Conducts basic NLP preprocessing on the input data.
    *
    * The preprocessing in order is stop words removal, tokenization, stemming, and lemmatization.
    *
    * @param data the data that is to be preprocessed
    * @return preprocessed data
    */
  def preprocess(data: RDD[Article]): Unit = {
    val properties = new Properties
    properties.setProperty("annotators", "tokenize, ssplit, parse, lemma")

    // Create a preprocessor with the given set of annotators
    val preprocessor = new StanfordCoreNLP(properties)

    data
      .map(article => preprocessor.process(article.content))
      .map(sentences => sentences.get(classOf[CoreAnnotations.SentencesAnnotation]))
      .filter(tokens => !stopWords.contains(tokens.toString))
  }
}

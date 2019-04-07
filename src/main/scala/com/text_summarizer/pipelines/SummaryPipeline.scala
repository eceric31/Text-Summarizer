package com.text_summarizer.pipelines

import java.io.File

import akka.http.scaladsl.marshalling.ToResponseMarshallable
import com.text_summarizer.SparkSessionProvider
import com.text_summarizer.models.{Article, SummarizerConfiguration}
import com.text_summarizer.models.api.requests.{FilePathSummaryRequest, FileUploadSummaryRequest, TextSummaryRequest}
import com.text_summarizer.readers.ReaderFactory
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
  * Represents a text summary pipeline, containing everything required for summarizing a text, or a text file.
  */
class SummaryPipeline {

  private[this] val session: SparkSession = SparkSessionProvider.getInstance

  /**
    * Creates a summary from a single text.
    *
    * @param request the [[TextSummaryRequest]]
    * @return the [[ToResponseMarshallable]]
    */
  def summarizeText(request: TextSummaryRequest): ToResponseMarshallable = {
    summarize(
      session.sparkContext.parallelize(
        Seq(
          Article(null, null, null, request.text)
        )
      ),
      request.configuration
    )
  }

  /**
    * Creates a summary from a single file, provided by path.
    *
    * @param request the [[FilePathSummaryRequest]]
    * @return the [[ToResponseMarshallable]]
    */
  def summarizeFilePath(request: FilePathSummaryRequest): ToResponseMarshallable = {
    summarize(
      readFile(new File(request.filePath), request.configuration),
      request.configuration
    )
  }

  /**
    * Creates a summary from a single file, provided via upload.
    *
    * TO DO: Implement file upload
    *
    * @param request the [[FileUploadSummaryRequest]]
    * @return the [[ToResponseMarshallable]]
    */
  def summarizeFileUpload(request: FileUploadSummaryRequest): ToResponseMarshallable = {
    throw new NotImplementedError("File upload is not yet implemented.")
  }

  private[this] def readFile(file: File, summarizerConfiguration: SummarizerConfiguration): RDD[Article] = {
    ReaderFactory(file, session, summarizerConfiguration).getReader.read
  }

  private[this] def summarize(
    data: RDD[Article],
    summarizerConfiguration: SummarizerConfiguration
  ): ToResponseMarshallable = {

    val preprocessor = new TextPreprocessor
    preprocessor.preprocess(data)

//    val preprocessedData = preprocessor
//      .preprocess(data)
//      .cache

    null
  }

}

package com.text_summarizer.controllers

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.text_summarizer.models.api.requests.{FilePathSummaryRequest, FileUploadSummaryRequest, TextSummaryRequest}
import com.text_summarizer.pipelines.SummaryPipeline
import com.text_summarizer.utils.Json4sSupport._

/**
  * Handles summarization requests.
  */
object TextSummarizerController {

  private[this] val summaryPipeline = new SummaryPipeline

  /**
    * Handles the request to summarize a single text.
    *
    * @return the [[Route]]
    */
  def summarizeText: Route = entity(as[TextSummaryRequest]) {
    request => complete(summaryPipeline.summarizeText(request))
  }

  /**
    * Handles the request to summarize a single file or a directory, provided via a path.
    *
    * @return the [[Route]]
    */
  def summarizeFilePath: Route = entity(as[FilePathSummaryRequest]) {
    request => complete(summaryPipeline.summarizeFilePath(request))
  }

  /**
    * Handles the request to summarize a single uploaded file.
    *
    * @return the [[Route]]
    */
  def summarizeFileUpload: Route = entity(as[FileUploadSummaryRequest]) {
    null
  }

}

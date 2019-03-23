package com.text_summarizer.controllers

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object Router {

  def routes: Route = {
    pathPrefix("text-summarizer") {
      pathPrefix("summarize") {
        path("text") {
          post { ctx => TextSummarizerController.summarizeText(ctx) }
        } ~
        path("file-upload") {
          post { ctx => TextSummarizerController.summarizeFileUpload(ctx) }
        } ~
        path("file-path") {
          post { ctx => TextSummarizerController.summarizeFilePath(ctx) }
        }
      }
    }
  }

}

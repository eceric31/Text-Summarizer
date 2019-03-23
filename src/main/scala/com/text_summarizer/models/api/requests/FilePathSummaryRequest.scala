package com.text_summarizer.models.api.requests

import com.text_summarizer.models.SummarizerConfiguration

case class FilePathSummaryRequest(
  filePath: String,
  configuration: SummarizerConfiguration
) extends Serializable

package com.text_summarizer.models.api.requests

import com.text_summarizer.models.SummarizerConfiguration

case class TextSummaryRequest(
  text: String,
  configuration: SummarizerConfiguration
) extends Serializable

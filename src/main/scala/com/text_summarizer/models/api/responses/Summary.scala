package com.text_summarizer.models.api.responses

/**
  * Serves as s response model for summary requests.
  *
  * @param summary the actual summary text
  * @param compressionRate the provided compression rate
  */
final case class Summary(
  summary: String,
  compressionRate: Double
) extends AbstractSummary

package com.text_summarizer.models

import com.text_summarizer.models.configurations.CSVConfiguration

/**
  * Represents the properties which determine how the summarizer will behave.
  *
  * @param fileType         type of file which is being submitted for summarization
  * @param csvConfiguration csv file reader related configuration
  */
case class SummarizerConfiguration(
  fileType: String,
  csvConfiguration: CSVConfiguration
)

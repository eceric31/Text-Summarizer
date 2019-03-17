package com.text_summarizer.models.configurations

/**
  * Used for configuring csv file reading.
  *
  * @param separator csv column separator
  * @param projections a mapping between column names and column indices in the provided file
  */
case class CSVConfiguration(
  separator: String,
  projections: Map[String, Int]
)
package com.text_summarizer.models.configurations

/**
  * Used for configuring csv file reading.
  *
  * @param projections a mapping between internal column names and column names in the provided file
  */
case class CSVConfiguration(
  projections: Map[String, String]
)
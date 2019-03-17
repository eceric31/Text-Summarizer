package com.text_summarizer.readers

import java.io.File

import com.text_summarizer.models.{FileType, SummarizerConfiguration}
import org.apache.commons.lang3.StringUtils
import org.apache.spark.sql.SparkSession

/**
  * Used for instantiating a reader instance.
  */
class ReaderFactory(
  file: File,
  session: SparkSession,
  configuration: SummarizerConfiguration
) {

  def getReader: Readable = {
    validateInput()

    FileType.fromString(configuration.fileType) match {
      case FileType.CSV => new CSVReader(file, configuration, session)
      case _ => throw new IllegalArgumentException("The provided file type is not supported.")
    }
  }

  private[this] def validateInput(): Unit = {
    assert(file != null, "The provided file must not be null.")
    assert(configuration != null, "The provided configuration must not be null.")
    assert(StringUtils.isNotBlank(configuration.fileType), "File type is not provided in the configuration.")
  }
}

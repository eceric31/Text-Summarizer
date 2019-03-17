package com.text_summarizer.models

import com.text_summarizer.models

object FileType extends Enumeration {
  type FileType = Value

  val CSV: models.FileType.Value = Value
  val SGM: models.FileType.Value = Value
  val MTX: models.FileType.Value = Value

  def fromString(fileType: String): FileType = values.find(_.toString == fileType.toUpperCase).orNull
}

package com.text_summarizer.models

/**
  * Represents the basic abstraction used for text summarization.
  *
  * @param author  name of the author
  * @param title   name of the article
  * @param date    date created
  * @param content the actual text of the article
  */
case class Article(
  author: String,
  title: String,
  date: String,
  content: String
)

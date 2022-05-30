package br.com.spring.dynamodb.model.error

import java.time.LocalDateTime

data class AmazonExceptionBody(
    val timeStamp: LocalDateTime = LocalDateTime.now(),
    val status: Int,
    val service: String,
    val error: String,
    val message: String,
    val path: String
)

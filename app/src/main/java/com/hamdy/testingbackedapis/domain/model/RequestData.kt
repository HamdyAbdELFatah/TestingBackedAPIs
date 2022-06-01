package com.hamdy.testingbackedapis.domain.model

import java.io.Serializable

data class RequestData(
    val url: String,
    val requestMethod: String,
    val postBody: String = "",
    val response: String = "",
    val headers: String = "",
    val statusCode: Int = 0,
    val executionTime: Long = 0,
) : Serializable

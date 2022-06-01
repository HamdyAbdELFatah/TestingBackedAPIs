package com.hamdy.testingbackedapis.domain.model

data class RequestData(
    val url: String,
    val requestMethod: String,
    val parameters : MutableMap<String?, String?>,
    val headers : MutableMap<String?, String?>,
    val response : String,
    val statusCode : String,
)

package com.hamdy.testingbackedapis.domain.repository

interface NetworkDataRepository {
    fun getResponseStatus(url: String, method: String)

    fun getResponseHeaders(url: String, method: String)

    fun getResponseBody(url: String, method: String)

}
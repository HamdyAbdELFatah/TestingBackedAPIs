package com.hamdy.testingbackedapis.domain.repository

import com.hamdy.testingbackedapis.domain.model.ParamsData
import java.net.HttpURLConnection

interface NetworkDataRepository {

    fun getResponse(
        url: String,
        method: String,
        listHeaders: MutableList<ParamsData>,
        listParameters: MutableList<ParamsData>
    )

    fun getHeader(connection: HttpURLConnection)
    fun addHeaders(connection: HttpURLConnection, listHeaders: MutableList<ParamsData>)
    fun addParameters(connection: HttpURLConnection, listParameters: MutableList<ParamsData>)

    fun postResponse(url: String, method: String)

}
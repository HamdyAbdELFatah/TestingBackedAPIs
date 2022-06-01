package com.hamdy.testingbackedapis.domain.repository

import androidx.lifecycle.LiveData
import com.hamdy.testingbackedapis.common.Result
import com.hamdy.testingbackedapis.domain.model.ParamsData
import com.hamdy.testingbackedapis.domain.model.RequestData
import java.net.HttpURLConnection

interface NetworkDataRepository {

    fun getResponse(
        requestData: RequestData, headersList: MutableList<ParamsData>,
        parametersList: MutableList<ParamsData>, callback: (Result<RequestData>) -> Unit
    )

    fun getHeader(connection: HttpURLConnection): String
    fun addHeaders(connection: HttpURLConnection, listHeaders: MutableList<ParamsData>)
    fun addParameters(connection: HttpURLConnection, listParameters: MutableList<ParamsData>)

    fun postResponse(
        requestData: RequestData, headersList: MutableList<ParamsData>,
        parametersList: MutableList<ParamsData>, callback: (Result<RequestData>) -> Unit
    )

    fun addBody(
        connection: HttpURLConnection,
        body: String,listParameters: MutableList<ParamsData>
    )

}
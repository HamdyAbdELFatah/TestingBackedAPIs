package com.hamdy.testingbackedapis.data.remote

import com.hamdy.testingbackedapis.domain.model.ParamsData
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

object ParameterStringBuilder {
    @Throws(UnsupportedEncodingException::class)
    fun getParamsString(params: MutableList<ParamsData>): String {
        val result = StringBuilder()
        for (parameter in params) {
            result.append(URLEncoder.encode(parameter.key, "UTF-8"))
            result.append("=")
            result.append(URLEncoder.encode(parameter.value, "UTF-8"))
            result.append("&")
        }
        val resultString = result.toString()
        return if (resultString.isNotEmpty()) resultString.substring(
            0,
            resultString.length - 1
        ) else resultString
    }
}

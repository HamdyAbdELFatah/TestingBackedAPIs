package com.hamdy.testingbackedapis.data.remote

import java.io.UnsupportedEncodingException
import java.net.URLEncoder

object ParameterStringBuilder {
    @Throws(UnsupportedEncodingException::class)
    fun getParamsString(params: MutableMap<String?, String?>): String {
        val result = StringBuilder()
        for ((key, value) in params) {
            result.append(URLEncoder.encode(key, "UTF-8"))
            result.append("=")
            result.append(URLEncoder.encode(value, "UTF-8"))
            result.append("&")
        }
        val resultString = result.toString()
        return if (resultString.isNotEmpty()) resultString.substring(
            0,
            resultString.length - 1
        ) else resultString
    }
}

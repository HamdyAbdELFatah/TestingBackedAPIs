package com.hamdy.testingbackedapis.data.remote

import java.net.HttpURLConnection
import java.net.URL

class HttpClient {

    companion object {
        fun getConnectionInstance(url: String, method: String): HttpURLConnection {
            val urlObject = URL(url)
            val urlConnection: HttpURLConnection = urlObject.openConnection() as HttpURLConnection
            urlConnection.requestMethod = method
            return urlConnection
        }
    }
}
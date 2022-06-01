package com.hamdy.testingbackedapis.data

import android.util.Log
import com.hamdy.testingbackedapis.data.remote.HttpClient.Companion.getConnectionInstance
import com.hamdy.testingbackedapis.domain.repository.NetworkDataRepository
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.util.concurrent.Executor

class NetworkDataRepositoryImpl(
    private val executor: Executor
) : NetworkDataRepository {
    override fun getResponseStatus(url: String, method: String) {
        executor.execute {
            var connection: HttpURLConnection? = null
            try {
                connection = getConnectionInstance(url, method)
                val status = connection.responseCode
                Log.d("LastManStanding", "getResponseStatus: $status")
            } finally {
                connection?.disconnect()
            }
        }
    }

    override fun getResponseHeaders(url: String, method: String) {

    }

    override fun getResponseBody(url: String, method: String) {
        executor.execute {
            var connection: HttpURLConnection? = null
            try {
                connection = getConnectionInstance(url, method)
                val status = connection.responseCode
                var bufferReader: BufferedReader?=null
                val response = StringBuilder()
                var line:String? = ""
                if (status > 299) {
                    bufferReader = BufferedReader(InputStreamReader(connection.errorStream))

                    while (bufferReader.readLine().also { line = it } != null) {
                        response.append(line)
                    }
                } else {
                    bufferReader = BufferedReader(InputStreamReader(connection.inputStream))
                    while (bufferReader.readLine().also { line = it } != null) {
                        response.append(line)
                    }
                }


                bufferReader.close()
                Log.d("LastManStanding", "getResponseStatus: $status")
                Log.d("LastManStanding", "response: $response")
            } finally {
                connection?.disconnect()
            }
        }
    }


}
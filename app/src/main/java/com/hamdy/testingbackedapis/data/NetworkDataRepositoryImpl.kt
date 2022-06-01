package com.hamdy.testingbackedapis.data

import android.util.Log
import com.hamdy.testingbackedapis.data.remote.HttpClient.Companion.getConnectionInstance
import com.hamdy.testingbackedapis.data.remote.ParameterStringBuilder
import com.hamdy.testingbackedapis.domain.model.ParamsData
import com.hamdy.testingbackedapis.domain.repository.NetworkDataRepository
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.util.concurrent.Executor

class NetworkDataRepositoryImpl(
    private val executor: Executor
) : NetworkDataRepository {


    override fun getResponse(
        url: String,
        method: String,
        listHeaders: MutableList<ParamsData>,
        listParameters: MutableList<ParamsData>
    ) {
        executor.execute {
            var connection: HttpURLConnection? = null
            val response = StringBuilder()
            try {
                connection = getConnectionInstance(url, method)
                // add header
                addHeaders(connection, listHeaders)
                val status = connection.responseCode
                val bufferReader: BufferedReader?
                var line: String?
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
                    getHeader(connection)

                }
                bufferReader.close()
                Log.d("LastManStanding", "getResponseStatus: $status")
                Log.d("LastManStanding", "response: $response")
            } catch (e: Exception) {
                Log.d("LastManStanding", "responseError: $response")
                Log.e("LastManStanding", "Error: $e")

            } finally {
                connection?.disconnect()
            }
        }
    }

    override fun postResponse(url: String, method: String) {
        executor.execute {
            var connection: HttpURLConnection? = null
            val response = StringBuilder()
            try {
                connection = getConnectionInstance(url, method)
                val status = connection.responseCode
                val bufferReader: BufferedReader?
                var line: String?
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
                    getHeader(connection)

                }
                bufferReader.close()
            } catch (e: Exception) {
                Log.e("LastManStanding", "Error: $e")
            } finally {
                connection?.disconnect()
            }
        }
    }

    override fun getHeader(connection: HttpURLConnection) {
        var i = 0
        val header: StringBuilder = StringBuilder()
        while (true) {
            val headerName: String? = connection.getHeaderFieldKey(i)
            val headerValue: String? = connection.getHeaderField(i)
            if (headerName.isNullOrBlank() && headerValue.isNullOrBlank()) {
                Log.d("LastManStanding", "header: $header")
                break
            } else {
                header.append("$headerName : $headerValue\n")
            }
            i++
        }
    }

    override fun addHeaders(connection: HttpURLConnection, listHeaders: MutableList<ParamsData>) {
        for (header in listHeaders) {
            if (header.key.isNotEmpty()) {
                connection.apply {
                    setRequestProperty(header.key, header.value)
                }
            }
        }
    }

    override fun addParameters(
        connection: HttpURLConnection,
        listParameters: MutableList<ParamsData>
    ) {
        val out = DataOutputStream(connection.outputStream)
        out.writeBytes(ParameterStringBuilder.getParamsString(listParameters))
        out.flush()
        out.close()

    }
}





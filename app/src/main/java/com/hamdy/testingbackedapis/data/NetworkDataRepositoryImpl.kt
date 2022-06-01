package com.hamdy.testingbackedapis.data

import android.os.Handler
import android.util.Log
import com.hamdy.testingbackedapis.common.Result
import com.hamdy.testingbackedapis.data.remote.HttpClient.Companion.getConnectionInstance
import com.hamdy.testingbackedapis.data.remote.ParameterStringBuilder
import com.hamdy.testingbackedapis.domain.model.ParamsData
import com.hamdy.testingbackedapis.domain.model.RequestData
import com.hamdy.testingbackedapis.domain.repository.NetworkDataRepository
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.util.concurrent.Executor

class NetworkDataRepositoryImpl(
    private val executor: Executor,
    private val resultHandler: Handler
) : NetworkDataRepository {
    private val TAG = "NetworkDataRepositoryIm"

    override fun getResponse(
        requestData: RequestData, headersList: MutableList<ParamsData>,
        parametersList: MutableList<ParamsData>,
        callback: (Result<RequestData>) -> Unit
    ) {
        val response = StringBuilder()
        executor.execute {
            var connection: HttpURLConnection? = null
            val status: Int
            var header = ""
            try {
                val start = System.nanoTime()

                var newUrl = requestData.url.trim()
                val par = ParameterStringBuilder.getParamsString(parametersList)
                if (parametersList.isNotEmpty() && par.isNotEmpty())
                    newUrl += par
                Log.d("NewUrl", "getResponse: $newUrl")
                connection = getConnectionInstance(newUrl, requestData.requestMethod)
                // add header
                addHeaders(connection, headersList)
                //addParameters(connection,listParameters)
                status = connection.responseCode
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
                    header = getHeader(connection)

                }
                bufferReader.close()
                Log.d(TAG, "getResponseStatus: $status")
                Log.d(TAG, "response: $response")
                val end = System.nanoTime()
                resultHandler.post {
                    callback(
                        Result.Success(
                            requestData.copy(
                                response = response.toString(),
                                headers = header,
                                statusCode = status,
                                executionTime = end - start

                            )
                        )
                    )
                }

            } catch (e: Exception) {
                Log.e(TAG, "Error: $e")
                val errorResult = Result.Error(e)
                resultHandler.post { callback(errorResult) }

            } finally {
                connection?.disconnect()
            }
        }
    }

    override fun postResponse(
        requestData: RequestData, headersList: MutableList<ParamsData>,
        parametersList: MutableList<ParamsData>,
        callback: (Result<RequestData>) -> Unit
    ) {
        executor.execute {
            val start = System.nanoTime()
            var connection: HttpURLConnection? = null
            val response = StringBuilder()
            var header = ""

            try {
                connection = getConnectionInstance(requestData.url, requestData.requestMethod)

                addBody(connection,requestData.postBody,parametersList)

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
                    header = getHeader(connection)

                }
                bufferReader.close()
                val end = System.nanoTime()
                resultHandler.post {
                    callback(
                        Result.Success(
                            requestData.copy(
                                response = response.toString(),
                                headers = header,
                                statusCode = status,
                                executionTime = end - start
                            )
                        )
                    )
                }

            } catch (e: Exception) {
                Log.e(TAG, "Error: ${e.localizedMessage}")
                val errorResult = Result.Error(e)
                resultHandler.post { callback(errorResult) }
            } finally {
                connection?.disconnect()
            }
        }
    }

    override fun addBody(connection: HttpURLConnection, body: String,listParameters: MutableList<ParamsData>) {
        //addParameters(connection,listParameters)
        val out = DataOutputStream(connection.outputStream)
        out.writeBytes(ParameterStringBuilder.getParamsString(listParameters))

        connection.outputStream.use { os ->
            val input: ByteArray = body.toByteArray(charset("utf-8"))
            os.write(input, 0, input.size)
        }
        out.flush()
        out.close()
    }
    override fun addParameters(
        connection: HttpURLConnection,
        listParameters: MutableList<ParamsData>
    ) {
        connection.doOutput = true
        val out = DataOutputStream(connection.outputStream)
        out.writeBytes(ParameterStringBuilder.getParamsString(listParameters))
        out.flush()
        out.close()
    }
    override fun getHeader(connection: HttpURLConnection): String {
        var i = 0
        val header: StringBuilder = StringBuilder()
        while (true) {
            val headerName: String? = connection.getHeaderFieldKey(i)
            val headerValue: String? = connection.getHeaderField(i)
            if (headerName.isNullOrBlank() && headerValue.isNullOrBlank()) {
                return header.toString()
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
                    setRequestProperty(header.key.trim(), header.value.trim())
                }
            }
        }
    }


}





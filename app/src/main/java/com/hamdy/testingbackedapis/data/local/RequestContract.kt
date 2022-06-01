package com.hamdy.testingbackedapis.data.local

import android.provider.BaseColumns


class RequestContract {
    object RequestEntry : BaseColumns {
        const val TABLE_NAME = "RequestsTable"
        const val COLUMN_ID = BaseColumns._ID
        const val REQUEST_METHODE = "RequestMethode"
        const val STATUS_CODE = "StatusCode"
        const val EXECUTION_TIME = "ExecutionTime"
        const val RESPONSE = "response"
        const val HEADERS = "headers"
        const val URL = "url"
    }
}
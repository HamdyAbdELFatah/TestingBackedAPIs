package com.hamdy.testingbackedapis.presentation.main_activity

import com.hamdy.testingbackedapis.domain.model.RequestData

data class MainActivityState(
    val isLoading: Boolean = false,
    val products: List<RequestData> = emptyList(),
    val error: String = ""
)


package com.hamdy.testingbackedapis.presentation.result_activity.ui.main

import android.accounts.AccountAuthenticatorResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PageViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()
    val text: LiveData<String> = Transformations.map(_response) {
        it
    }

    fun setIndex(response: String) {
        _response.value = response
    }
}
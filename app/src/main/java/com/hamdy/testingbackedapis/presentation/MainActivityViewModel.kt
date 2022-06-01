package com.hamdy.testingbackedapis.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hamdy.testingbackedapis.data.NetworkDataRepositoryImpl
import com.hamdy.testingbackedapis.domain.model.ParamsData

class MainActivityViewModel(private val networkDataRepositoryImpl: NetworkDataRepositoryImpl) :
    ViewModel() {
    private val _url = MutableLiveData("")
    val url: LiveData<String>
        get() = _url
    private val _headersCount = MutableLiveData(0)
    val headersCount: LiveData<Int>
        get() = _headersCount
    private val _parameterCount = MutableLiveData(0)
    val parameterCount: LiveData<Int>
        get() = _parameterCount
    private val _headersList = MutableLiveData(mutableListOf<ParamsData>())
    val headersList: LiveData<MutableList<ParamsData>>
        get() = _headersList
    private val _parameterList = MutableLiveData(mutableListOf<ParamsData>())
    val parameterList: LiveData<MutableList<ParamsData>>
        get() = _parameterList

    fun getResponseStatus(url: String, method: String) {
        networkDataRepositoryImpl.getResponseBody(url, method)
    }

    fun changeUrl(url: String) {
        _url.value = url
    }

    fun setHeadersList(list: MutableList<ParamsData>) {
        _headersList.value = list
    }

    fun setParametersList(list: MutableList<ParamsData>) {
        _parameterList.value = list
    }

    fun addHeadersItem(item: ParamsData) {
        _headersList.value?.add(item)
        _headersCount.value = _headersCount.value!! + 1
        _headersList.notifyObserver()
    }

    fun addParametersItem(item: ParamsData) {
        _parameterList.value?.add(item)
        _parameterCount.value = _parameterCount.value!! + 1
        _parameterList.notifyObserver()
    }

    fun removeHeadersItemAt(position: Int) {
        _headersList.value?.removeAt(position)
        _headersList.notifyObserver()
    }

    fun removeParametersItemAt(position: Int) {
        _parameterList.value?.removeAt(position)
        _parameterList.notifyObserver()
    }


    fun onHeadersItemChange(position: Int, item: ParamsData) {
        _headersList.value?.set(position, item)
        _headersList.notifyObserver()
    }

    fun onParametersItemChange(position: Int, item: ParamsData) {
        _parameterList.value?.set(position, item)
        _parameterList.notifyObserver()
    }

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

}

class MainActivityViewModelFactory(
    private val networkDataRepositoryImpl: NetworkDataRepositoryImpl
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (MainActivityViewModel(networkDataRepositoryImpl) as T)
}

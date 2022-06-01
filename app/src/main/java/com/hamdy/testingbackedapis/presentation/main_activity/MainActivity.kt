package com.hamdy.testingbackedapis.presentation.main_activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamdy.testingbackedapis.MyApplication
import com.hamdy.testingbackedapis.R
import com.hamdy.testingbackedapis.common.Constants.GET_METHODE
import com.hamdy.testingbackedapis.common.Constants.HEADER_TYPE
import com.hamdy.testingbackedapis.common.Constants.PARAMETERS_TYPE
import com.hamdy.testingbackedapis.common.NetworkStatus.isNetworkAvailable
import com.hamdy.testingbackedapis.data.NetworkDataRepositoryImpl
import com.hamdy.testingbackedapis.databinding.ActivityMainBinding
import com.hamdy.testingbackedapis.domain.model.ParamsData
import com.hamdy.testingbackedapis.domain.model.RequestData
import com.hamdy.testingbackedapis.presentation.main_activity.adapter.ParametersViewHolder
import com.hamdy.testingbackedapis.presentation.main_activity.adapter.ParamsAdapter
import com.hamdy.testingbackedapis.presentation.result_activity.ResultActivity


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener,
    ParametersViewHolder.OnKeyValueItemTouch, View.OnClickListener {
    private lateinit var requestMethods: Array<String>
    private lateinit var headersList: MutableList<ParamsData>
    private lateinit var parameterList: MutableList<ParamsData>
    private lateinit var requestData: RequestData
    private lateinit var binding: ActivityMainBinding
    private var parameterCount = 0
    private var headersCount = 0
    private var url = "https://httpbin.org/get"
    private var requestMethode = GET_METHODE
    private lateinit var headersAdapter: ParamsAdapter
    private lateinit var parametersAdapter: ParamsAdapter
    private lateinit var viewModel: MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val app = application as MyApplication
        viewModel = ViewModelProvider(
            this,
            MainActivityViewModelFactory(
                NetworkDataRepositoryImpl(
                    app.executorService
                )
            )
        )[MainActivityViewModel::class.java]
        val methodSpinner = binding.methodSpinner
        methodSpinner.onItemSelectedListener = this
        requestMethods = resources.getStringArray(R.array.request_method_spinner)
        val headersRecyclerView = binding.headersRecyclerView
        val parametersRecyclerView = binding.parametersRecyclerView
        headersRecyclerView.layoutManager = LinearLayoutManager(this)
        parametersRecyclerView.layoutManager = LinearLayoutManager(this)
        parametersAdapter = ParamsAdapter(this)
        headersAdapter = ParamsAdapter(this)
        headersRecyclerView.adapter = headersAdapter
        parametersRecyclerView.adapter = parametersAdapter

        binding.addHeader.setOnClickListener(this)
        binding.addParameters.setOnClickListener(this)
        binding.requestButton.setOnClickListener(this)
        binding.urlText.addTextChangedListener { viewModel.changeUrl(it.toString()) }
        viewModelObserver()
        binding.urlText.setText(url)

    }

    private fun viewModelObserver() {
        viewModel.url.observe(this) {
            url = it
        }
        viewModel.headersCount.observe(this) {
            headersCount = it
        }
        viewModel.parameterCount.observe(this) {
            parameterCount = it
        }
        viewModel.headersList.observe(this) {
            headersList = it
            headersAdapter.differ.submitList(headersList.toList())
        }
        viewModel.parameterList.observe(this) {
            parameterList = it
            parametersAdapter.differ.submitList(it.toList())
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, index: Int, p3: Long) {
        requestMethode = requestMethods[index]
    }


    override fun onDeleteClicked(position: Int, type: String) {
        if (type == HEADER_TYPE) {
            viewModel.removeHeadersItemAt(position)
        } else {
            viewModel.removeParametersItemAt(position)
        }
    }

    override fun onKeyChange(position: Int, key: String, type: String) {
        if (type == HEADER_TYPE) {
            viewModel.onHeadersItemChange(position, headersList[position].copy(key = key))
        } else {
            viewModel.onParametersItemChange(position, parameterList[position].copy(key = key))

        }
    }

    override fun onValueChange(position: Int, value: String, type: String) {
        if (type == HEADER_TYPE) {
            headersList[position] = headersList[position].copy(value = value)
            viewModel.setHeadersList(headersList)
        } else {
            parameterList[position] = parameterList[position].copy(value = value)
            viewModel.setParametersList(parameterList)
        }
    }

    override fun onClick(view: View?) {
        if (view?.id == R.id.add_header) {
            viewModel.addHeadersItem(ParamsData(headersCount, "", "", HEADER_TYPE))
        } else if (view?.id == R.id.add_parameters) {
            viewModel.addParametersItem(ParamsData(parameterCount, "", "", PARAMETERS_TYPE))
        } else if ((view?.id == R.id.request_button)) {
            if (isNetworkAvailable(this@MainActivity)) {
                if (url.isEmpty()) {
                    binding.urlText.error = "Url can't be empty"
                } else {
//                    for (i in headersList)
//                        Log.d("manprint", "onClick: ${i.id} ${i.key}  ${i.value}")
//                    for (i in parameterList)
//                        Log.d("manprint", "onClick: ${i.id} ${i.key}  ${i.value}")

                    if(requestMethode == GET_METHODE){
                        viewModel.getResponse(url, requestMethode)
                    }else{
                        viewModel.postResponse(url, requestMethode)
                    }
//                    viewModel.getResponseStatus(url, requestMethode)
//                    startActivity(Intent(this@MainActivity, ResultActivity::class.java))
                }
            } else {
                Toast.makeText(this@MainActivity, "No Internet Connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    override fun onNothingSelected(p0: AdapterView<*>?) {}
}
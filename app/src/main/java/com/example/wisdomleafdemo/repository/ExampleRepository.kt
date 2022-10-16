package com.example.wisdomleafdemo.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wisdomleafdemo.retrofit.APIInterface
import com.example.wisdomleafdemo.utils.NetworkResult
import com.example.wisdomleafdemo.utils.SafeApiCall
import com.example.wisdomleafdemo.pojo.ExampleData
import retrofit2.Response
import retrofit2.http.Query
import javax.inject.Inject

class ExampleRepository @Inject constructor(private val APIInterface: APIInterface) {

    private val _examples = MutableLiveData<List<ExampleData>?>()
    val examples: LiveData<List<ExampleData>?>
        get() = _examples

    suspend fun getExamples(page: String, limit: String) {
        val result: NetworkResult<Response<List<ExampleData>?>> = SafeApiCall.safeApiCall {
            APIInterface.getExamples(page, limit)
        }
        when (result) {
            is NetworkResult.Success -> {//Handle success
                if (result.data.isSuccessful && result.data.body() != null) {
                    _examples.postValue(result.data.body())
                }
            }
            is NetworkResult.Error -> { //Handle error
                Log.e("Error", result.message.toString())
                _examples.postValue(null)
            }
        }

    }

}
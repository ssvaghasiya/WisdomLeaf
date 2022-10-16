package com.example.wisdomleafdemo.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wisdomleafdemo.pojo.ExampleData
import com.example.wisdomleafdemo.repository.ExampleRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: ExampleRepository,
) : ViewModel() {

    val exampleLiveData: LiveData<List<ExampleData>?>
        get() = repository.examples

    fun callApi(page: String, limit: String) {
        viewModelScope.launch {
            repository.getExamples(page, limit)
        }
    }

}


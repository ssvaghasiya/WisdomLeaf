package com.example.wisdomleafdemo.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wisdomleafdemo.MyApp
import com.example.wisdomleafdemo.R
import com.example.wisdomleafdemo.databinding.ActivityMainBinding
import com.example.wisdomleafdemo.listener.EventListener
import com.example.wisdomleafdemo.pojo.ExampleData
import com.example.wisdomleafdemo.ui.adapter.CustomAdapter
import com.example.wisdomleafdemo.utils.Utils.isNetworkAvailable
import com.example.wisdomleafdemo.utils.Utils.setProgressDialog
import com.example.wisdomleafdemo.viewmodels.MainViewModel
import com.example.wisdomleafdemo.viewmodels.MainViewModelFactory
import com.google.gson.Gson
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: CustomAdapter
    var exampleList = ArrayList<ExampleData>()
    var dialog: AlertDialog? = null

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        (application as MyApp).applicationComponent.inject(this)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
        dialog = setProgressDialog(this, "Loading..")

        setObserver()
        initView()
    }

    private fun setObserver() {
        mainViewModel.exampleLiveData.observe(this, Observer {
            dialog?.dismiss()
            binding.pullToRefresh.isRefreshing = false
            if (it != null) { //set response data
                exampleList.clear()
                exampleList.addAll(it)
                adapter.notifyDataSetChanged()
            } else {
                showToast(getString(R.string.something_wrong_validation))
            }
        })
    }

    private fun initView() = with(binding) {
        setAdapter()
        pullToRefresh.setOnRefreshListener {
            pullToRefresh.isRefreshing = true
            callApi()
        }
        dialog?.show()
        callApi()
    }

    private fun setAdapter() = with(binding) {
        adapter = CustomAdapter(exampleList, object : EventListener<ExampleData> {
            override fun onItemClick(pos: Int, item: ExampleData, view: View) {
                showAlert(item)
            }
        })
        recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
        recyclerview.adapter = adapter
        recyclerview.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    private fun showAlert(data: ExampleData) {
        AlertDialog.Builder(this)
            .setTitle("Data")
            .setMessage(Gson().toJson(data))
            .setPositiveButton(
                "Ok"
            ) { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }


    private fun callApi() = with(binding) {
        if (isNetworkAvailable(this@MainActivity)) {
            mainViewModel.callApi("2", "20")
        } else {
            pullToRefresh.isRefreshing = false
            showToast(getString(R.string.internet_validation))
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
    }
}
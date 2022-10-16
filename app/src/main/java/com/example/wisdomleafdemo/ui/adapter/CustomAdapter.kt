package com.example.wisdomleafdemo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wisdomleafdemo.R
import com.example.wisdomleafdemo.databinding.LayoutDataBinding
import com.example.wisdomleafdemo.extension.loadImage
import com.example.wisdomleafdemo.listener.EventListener
import com.example.wisdomleafdemo.pojo.ExampleData

class CustomAdapter(
    var data: ArrayList<ExampleData>,
    var mEventListener: EventListener<ExampleData>
) :
    RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = DataBindingUtil.inflate<LayoutDataBinding>(
            inflater,
            R.layout.layout_data, parent, false
        )
        return MyViewHolder(itemBinding)
    }


    override fun getItemCount(): Int {
        return data.size
    }

    private fun getItem(p: Int): ExampleData {
        return data[p]

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) = with(holder.itemBinding) {
        val item = getItem(position)
        holder.itemBinding.exampleData = item
        item.apply {
            imageView.loadImage(downloadUrl)
        }

        root.setOnClickListener {
            mEventListener.onItemClick(position, item, it)
        }
    }

    inner class MyViewHolder(internal var itemBinding: LayoutDataBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

}
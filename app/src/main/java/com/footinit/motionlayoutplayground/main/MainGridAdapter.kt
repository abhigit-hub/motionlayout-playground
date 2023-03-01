package com.footinit.motionlayoutplayground.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.footinit.motionlayoutplayground.R
import com.footinit.motionlayoutplayground.databinding.ItemMainBinding
import com.footinit.motionlayoutplayground.model.CustomModel

class MainGridAdapter(private var callback: Callback?, val list: ArrayList<CustomModel>) :
        RecyclerView.Adapter<MainGridAdapter.MainViewHolder>() {

    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    fun removeCallback() {
        this.callback = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) = holder.onBind(list[position])

    fun updateList(list: List<CustomModel>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    interface Callback {
        fun onItemSelected(id: Int, message: String)
    }

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemMainBinding.bind(itemView)

        fun onBind(model: CustomModel) {
            binding.tvTitle.text = model.title
            binding.tvDescription.text = model.description
            binding.cvItem.setCardBackgroundColor(model.colorCode)

            binding.cvItem.setOnClickListener {
                callback?.onItemSelected(position + 1, model.title + " - " + model.description)
            }
        }
    }
}
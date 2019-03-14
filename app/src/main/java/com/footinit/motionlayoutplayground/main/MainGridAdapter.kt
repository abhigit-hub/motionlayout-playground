package com.footinit.motionlayoutplayground.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.footinit.motionlayoutplayground.R
import com.footinit.motionlayoutplayground.model.CustomModel
import kotlinx.android.synthetic.main.item_main.view.*

class MainGridAdapter(private var callback: Callback?, val list: ArrayList<CustomModel>) :
        RecyclerView.Adapter<MainGridAdapter.MainViewHolder>() {

    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    fun removeCallback() {
        this.callback = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
                LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_main, parent, false))
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
        fun onBind(model: CustomModel) {
            itemView.tvTitle.setText(model.title)
            itemView.tvDescription.setText(model.description)
            itemView.cvItem.setCardBackgroundColor(model.colorCode)

            itemView.cvItem.setOnClickListener {
                callback?.onItemSelected(position + 1, model.title + " - " + model.description)
            }
        }
    }
}
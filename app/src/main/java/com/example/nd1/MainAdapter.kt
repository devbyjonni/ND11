package com.example.nd1


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nd1.databinding.MainItemBinding


class MainAdapter(private val model: List<String>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: MainItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(
            MainItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = model.get(position)

        with(holder.binding) {
            titleTextView.text = task.toString()
        }
    }

    override fun getItemCount(): Int = model.size
}
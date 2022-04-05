package com.example.nd1


import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nd1.data.HabitEntity
import com.example.nd1.databinding.MainItemBinding


class MainAdapter(private val habits: List<HabitEntity>, private val listener: MainAdapterItemListener) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

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
        val habit = habits.get(position)

        with(holder.binding) {
            titleTextView.text = habit.title.toString()
            descriptionTextView.text = habit.description.toString()

            if (habit.completed) {
                titleTextView.setTextColor(Color.parseColor("#48484A"))
                descriptionTextView.setTextColor(Color.parseColor("#48484A"))
                timeTextView.setTextColor(Color.parseColor("#48484A"))
                timerTextView.setTextColor(Color.parseColor("#48484A"))
                holder.binding.defaultCompleteImageView.visibility = View.INVISIBLE
                holder.binding.defaultBorderImageView.visibility = View.INVISIBLE
                holder.binding.completedImageView.visibility = View.VISIBLE
            } else {
                titleTextView.setTextColor(Color.parseColor("#FFFFFF"))
                descriptionTextView.setTextColor(Color.parseColor("#8E8E93"))
                timeTextView.setTextColor(Color.parseColor("#FFFFFF"))
                timerTextView.setTextColor(Color.parseColor("#8E8E93"))
                holder.binding.defaultCompleteImageView.visibility = View.VISIBLE
                holder.binding.defaultBorderImageView.visibility = View.INVISIBLE

                holder.binding.completedImageView.visibility = View.INVISIBLE

                holder.binding.habitButton.setOnClickListener{
                    Log.i("LOGTEST", "editButton")
                    listener.onItemClick(habit.id)
                }

                holder.binding.completedButton.setOnClickListener{
                    Log.i("LOGTEST", "completedButton")
                }
            }
        }


    }

    override fun getItemCount(): Int = habits.size

    interface MainAdapterItemListener {
        fun onItemClick(id: Int)
    }
}
package com.example.nd1


import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nd1.data.HabitEntity
import com.example.nd1.databinding.MainItemBinding


class MainAdapter(private val habits: List<HabitEntity>, private val listener: MainAdapterItemListener) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    lateinit var mainFragment : MainFragment
    val selectedHabits = arrayListOf<HabitEntity>()


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
            titleTextView.text = habit.title
            val completions = habit.totalCompletions.toString()
            completionsTextView.text = "Times: $completions"

            val seconds = habit.timerSeconds
            val hours = seconds / 3600
            val minutes = seconds / 60 % 60

            val strHours: String = "%02d".format(hours)
            val strMinutes: String = "%02d".format(minutes)

            timerTextView.text = "${strHours}:${strMinutes}"

            if (mainFragment.isEditing) {

                holder.binding.defaultCompleteImageView.visibility = View.INVISIBLE
                holder.binding.defaultBorderImageView.visibility = View.INVISIBLE
                holder.binding.completedImageView.visibility = View.INVISIBLE
                holder.binding.completedBorderImageView.visibility = View.INVISIBLE
                holder.binding.completedButton.visibility = View.INVISIBLE
                holder.binding.habitButton.visibility = View.INVISIBLE

                holder.binding.editImageView.visibility = View.VISIBLE
                holder.binding.deleteImageView.visibility = View.VISIBLE

                holder.binding.editBtn.visibility = View.VISIBLE
                holder.binding.deleteBtn.visibility = View.VISIBLE

                titleTextView.setTextColor(Color.parseColor("#FFFFFF"))
                descriptionTextView.setTextColor(Color.parseColor("#8E8E93"))
                timerTextView.setTextColor(Color.parseColor("#FFFFFF"))
                completionsTextView.setTextColor(Color.parseColor("#8E8E93"))

                holder.binding.editBtn.setOnClickListener{
                    listener.didClickEdit(habit.id)
                }

                holder.binding.deleteBtn.setOnClickListener{
                    if (selectedHabits.contains(habit)) {
                        R.drawable.ic_baseline_delete_outline
                        selectedHabits.remove(habit)
                    } else {
                        R.drawable.ic_baseline_delete_outline_selected
                        selectedHabits.add(habit)
                    }
                    listener.onItemSelectionChanged(selectedHabits.size > 0)
                }

                deleteImageView.setImageResource(
                    if (selectedHabits.contains(habit)) {
                        R.drawable.ic_baseline_delete_outline_selected
                    } else {
                        R.drawable.ic_baseline_delete_outline
                    }
                )
            } else {

                holder.binding.editImageView.visibility = View.INVISIBLE
                holder.binding.deleteImageView.visibility = View.INVISIBLE
                holder.binding.editBtn.visibility = View.INVISIBLE
                holder.binding.deleteBtn.visibility = View.INVISIBLE

                holder.binding.completedButton.visibility = View.VISIBLE
                holder.binding.habitButton.visibility = View.VISIBLE

                if (habit.isCompleted) {
                    titleTextView.setTextColor(Color.parseColor("#48484A"))
                    descriptionTextView.setTextColor(Color.parseColor("#48484A"))
                    timerTextView.setTextColor(Color.parseColor("#48484A"))
                    completionsTextView.setTextColor(Color.parseColor("#48484A"))
                    holder.binding.defaultCompleteImageView.visibility = View.INVISIBLE
                    holder.binding.defaultBorderImageView.visibility = View.INVISIBLE
                    holder.binding.completedImageView.visibility = View.VISIBLE
                } else {
                    titleTextView.setTextColor(Color.parseColor("#FFFFFF"))
                    descriptionTextView.setTextColor(Color.parseColor("#8E8E93"))
                    timerTextView.setTextColor(Color.parseColor("#FFFFFF"))
                    completionsTextView.setTextColor(Color.parseColor("#8E8E93"))
                    holder.binding.defaultCompleteImageView.visibility = View.VISIBLE
                    holder.binding.defaultBorderImageView.visibility = View.INVISIBLE

                    holder.binding.completedImageView.visibility = View.INVISIBLE


                }

                holder.binding.habitButton.setOnClickListener{
                    listener.didClickHabitBtn(habit.id)
                }

                holder.binding.completedButton.setOnClickListener{
                    listener.didClickCompletionBtn(habit)
                }

                holder.binding.defaultCompleteImageView.visibility = View.VISIBLE
                holder.binding.defaultBorderImageView.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int = habits.size

    interface MainAdapterItemListener {
        fun didClickHabitBtn(id: Int)
        fun didClickEdit(id: Int)
        fun didClickCompletionBtn(habit: HabitEntity)
        fun onItemSelectionChanged(shouldDeleteItems: Boolean)
    }
}
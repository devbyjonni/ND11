package com.example.nd1


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nd1.data.HabitEntity
import com.example.nd1.databinding.FragmentMainBinding
import java.text.SimpleDateFormat
import java.util.*


class MainFragment : Fragment(), MainAdapter.MainAdapterItemListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var mainAdapter: MainAdapter
    var isEditing = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.floatingActionButton.setOnClickListener {
            isEditing = false
            binding.editButtonMainFrag.text = getString(R.string.editBynText)
            binding.editButtonMainFrag.visibility = View.VISIBLE
            val action =
                MainFragmentDirections.actionMainFragmentToAddEditFragment(habitId = NEW_HABIT_ID)
            findNavController().navigate(action)
        }

        viewModel.habitList?.observe(viewLifecycleOwner, Observer { habit ->
            mainAdapter = MainAdapter(habit.sortedBy { it.isCompleted }, this@MainFragment)

            //Fix this later
            mainAdapter.mainFragment = this

            binding.habitsRecycleView.layoutManager = LinearLayoutManager(activity)
            binding.habitsRecycleView.adapter = mainAdapter
        })

        binding.editButtonMainFrag.setOnClickListener {
            if (isEditing) {
                isEditing = false
                binding.editButtonMainFrag.text = getString(R.string.editBynText)
            } else {
                isEditing = true
                binding.editButtonMainFrag.text = getString(R.string.doneBtnText)
            }
            mainAdapter.notifyDataSetChanged()
        }

        binding.trashImageView.setOnClickListener {
            viewModel.deleteHabits(mainAdapter.selectedHabits)
            Handler(Looper.getMainLooper()).postDelayed({
                mainAdapter.selectedHabits.clear()
            }, 100)

            isEditing = false
            binding.editButtonMainFrag.text = getString(R.string.editBynText)
            binding.editButtonMainFrag.visibility = View.VISIBLE
        }

        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateInstance()
        val formattedTodayDate = formatter.format(date)
        binding.dateTextView.text = formattedTodayDate

        return binding.root
    }

    override fun onPause() {
        super.onPause()

        Log.i("LOGTEST", "onPause")
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllSettings()
        Log.i("LOGTEST", "start")
    }

    override fun didClickHabitBtn(id: Int) {
        val action = MainFragmentDirections.actionMainFragmentToTimerFragment(habitId = id)
        findNavController().navigate(action)
    }

    override fun didClickEdit(id: Int) {
        isEditing = false
        binding.editButtonMainFrag.text = getString(R.string.editBynText)
        val action = MainFragmentDirections.actionMainFragmentToAddEditFragment(habitId = id)
        findNavController().navigate(action)
    }

    override fun didClickCompletionBtn(habit: HabitEntity) {
        habit.isCompleted = habit.isCompleted != true

        if (habit.isCompleted) habit.totalCompletions += 1 else habit.totalCompletions -= 1
        viewModel.currentHabit.value = habit

        viewModel.updateHabit()

        Handler(Looper.getMainLooper()).postDelayed({
            mainAdapter.notifyDataSetChanged()
        }, 100)
    }

    override fun onItemSelectionChanged(shouldDeleteItems: Boolean) {
        if (shouldDeleteItems) {
            binding.editButtonMainFrag.visibility = View.INVISIBLE
        } else {
            binding.editButtonMainFrag.visibility = View.VISIBLE
        }

        mainAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
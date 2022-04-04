package com.example.nd1

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.nd1.databinding.FragmentMainBinding


class MainFragment : Fragment(), MainAdapter.MainAdapterItemListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SharedMainViewModel
    private lateinit var mainAdapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(SharedMainViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingActionButton.setOnClickListener{
            val habit = Habit(0,"Test","Test",false)
            val action = MainFragmentDirections.actionMainFragmentToAddEditFragment(testHabit = habit)
            findNavController().navigate(action)
        }

        viewModel.model.observe(viewLifecycleOwner) {
            viewModel.model.value?.let { habits ->
                mainAdapter = MainAdapter(habits, this@MainFragment)
                binding.habitsRecycleView.layoutManager = LinearLayoutManager(activity)
                binding.habitsRecycleView.adapter = mainAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(id: Int) {
        Log.i("LOGTEST", "LOGTEST $id" )
        //requireActivity().supportFragmentManager.beginTransaction().add(R.id.fragContainer, TimerFragment()).addToBackStack(null).commit()
       // findNavController().navigate(R.id.action_mainFragment_to_timerFragment)
        val action = MainFragmentDirections.actionMainFragmentToTimerFragment(habitId = id)
        findNavController().navigate(action)
    }
}
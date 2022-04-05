package com.example.nd1

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.nd1.databinding.FragmentMainBinding


class MainFragment : Fragment(), MainAdapter.MainAdapterItemListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var mainAdapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.floatingActionButton.setOnClickListener{
            val action = MainFragmentDirections.actionMainFragmentToAddEditFragment(habitId = NEW_HABIT_ID)
            findNavController().navigate(action)
        }

        viewModel.habitList?.observe(viewLifecycleOwner, Observer {
            Log.i("noteLogging", it.toString())
            mainAdapter = MainAdapter(it, this@MainFragment)
            binding.habitsRecycleView.layoutManager = LinearLayoutManager(activity)
            binding.habitsRecycleView.adapter = mainAdapter
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // addSampleData()
    }

    private fun addSampleData(): Boolean {
        viewModel.addSampleData()
        return true
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
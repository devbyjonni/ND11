package com.example.nd1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.nd1.databinding.FragmentMainBinding


class MainFragment : Fragment() {

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
            viewModel.updateData()
        }

        viewModel.model.observe(viewLifecycleOwner) {
            viewModel.model.value?.let { value ->
                mainAdapter = MainAdapter(value)
                binding.tasksRecycleView.layoutManager = LinearLayoutManager(activity)
                binding.tasksRecycleView.adapter = mainAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
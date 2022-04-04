package com.example.nd1

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.nd1.databinding.TimerFragmentBinding

class TimerFragment : Fragment() {

    private lateinit var viewModel: TimerViewModel
    private val args: TimerFragmentArgs by navArgs()
    private lateinit var binding: TimerFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TimerFragmentBinding.inflate(inflater, container, false)
        binding.timerDescriptionTextView.setText("You selected habit: ${args.habitId}")

        binding.timerCancelButton.setOnClickListener {
            saveAndReturn()
        }

        binding.timerSaveButton.setOnClickListener {
            saveAndReturn()
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                   saveAndReturn()
                }
            }
        )
        return binding.root
    }

    private fun saveAndReturn() : Boolean {
        Log.i("LOGTEST", "saveAndReturn")
        findNavController().navigateUp()
        return true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TimerViewModel::class.java)
    }
}
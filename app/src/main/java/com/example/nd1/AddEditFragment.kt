package com.example.nd1

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SeekBar
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.nd1.databinding.AddEditFragmentBinding
import java.text.SimpleDateFormat
import java.util.*

class AddEditFragment : Fragment() {

    private lateinit var viewModel: AddEditViewModel
    private val args: AddEditFragmentArgs by navArgs()
    private var _binding: AddEditFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(this).get(AddEditViewModel::class.java)
        _binding = AddEditFragmentBinding.inflate(inflater, container, false)

        binding.addEditCancelButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.addEditSaveButton.setOnClickListener {
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

        viewModel.currentHabit.observe(viewLifecycleOwner, Observer {
            val savedString = savedInstanceState?.getString(HABIT_TEXT_KEY)
            val cursorPosition = savedInstanceState?.getInt(CURSOR_POSITION_KEY) ?: 0
            binding.nameTextView.setText(savedString ?: it.title)
            val minutes = it.timerSeconds / 60
            minutesToString(minutes)
            binding.nameTextView.setSelection(cursorPosition)
        })
        viewModel.getHabitById(args.habitId)



        binding.addEditeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                // write custom code for progress is changed
                binding.minutesTextView.setText(seek.progress.toString())
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
                binding.minutesTextView.setText(seek.progress.toString())
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped

                binding.minutesTextView.setText(seek.progress.toString())
            }
        })
        minutesToString(viewModel.currentHabit.value?.timerSeconds ?: 0)
        return binding.root
    }

    private fun minutesToString(minutes: Long) {
        binding.addEditeekBar.progress = minutes.toInt()
        binding.minutesTextView.setText(minutes.toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    if (args.habitId == NEW_HABIT_ID) {
        binding.nameTextView.requestFocus()
    }

        val inputMethodManager: InputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(binding.nameTextView, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun saveAndReturn() : Boolean {
        viewModel.currentHabit.value?.title = binding.nameTextView.text.toString()

        if (binding.minutesTextView.text.isNotEmpty()) {
            val time = binding.minutesTextView.text.toString().toLong()
            val toSeconds = time * 60
            viewModel.currentHabit.value?.timerSeconds = toSeconds
        }

        val date = Date()
        val formatter = SimpleDateFormat.getDateInstance()
        val formattedDate = formatter.format(date)

        viewModel.currentHabit.value?.date = date
        viewModel.currentHabit.value?.dateStr = formattedDate
        viewModel.updateHabit()

        findNavController().navigateUp()

        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
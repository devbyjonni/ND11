package com.example.nd1

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.nd1.databinding.AddEditFragmentBinding

class AddEditFragment : Fragment() {

   // private lateinit var viewModel: AddEditViewModel
    private val args: AddEditFragmentArgs by navArgs()
    private var _binding: AddEditFragmentBinding? = null
    private val binding get() = _binding!!

    private var viewModel: MainViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddEditFragmentBinding.inflate(inflater, container, false)
        //binding.addEditTextView.setText("You selected habit: ${args.taskId}")

        binding.addEditCancelButton.setOnClickListener {
            saveAndReturn()
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(requireActivity())[MainViewModel::class.java]
        }
    }

    private fun saveAndReturn() : Boolean {
        Log.i("LOGTEST", "saveAndReturn")
        findNavController().navigateUp()
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.nd1

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.nd1.databinding.TimerFragmentBinding
import android.media.MediaPlayer


class TimerFragment : Fragment() {

    var mMediaPlayer: MediaPlayer? = null

    private val args: TimerFragmentArgs by navArgs()
    private lateinit var viewModel: TimerViewModel
    private var _binding: TimerFragmentBinding? = null
    private val binding get() = _binding!!

    private var timer: CountDownTimer? = null
    private var secondsRemaining: Long = 0
    private var timerLengthSeconds: Long = 0
    private var timerState = TimerState.Stopped

    enum class TimerState {
        Stopped, Paused, Running
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TimerFragmentBinding.inflate(inflater, container, false)

        binding.timerCancelButton.setOnClickListener {
            saveAndReturn()
        }

        binding.startBtn.setOnClickListener {
            startTimer()
        }

        binding.stopBtn.setOnClickListener {
            stopTimer()
        }

        binding.pauseBtn.setOnClickListener {
            pauseTimer()
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    saveAndReturn()
                }
            }
        )

        viewModel = ViewModelProvider(this)[TimerViewModel::class.java]

        viewModel.currentHabit.observe(viewLifecycleOwner, Observer {
            binding.habitTitletextView.text = it.title
            binding.timerTotalCompletionsTextView.text = it.totalCompletions.toString()

            timerLengthSeconds = it.timerSeconds
            secondsRemaining = it.timerSeconds


            if (it.isCompleted) {
                binding.timerCompletedImageView.visibility = View.VISIBLE
            } else {
                binding.timerCompletedImageView.visibility = View.INVISIBLE
            }

            updateCountdownUI()
        })

        updateButtons()

        viewModel.getHabitById(args.habitId)

        return binding.root
    }

    private fun initTimer() {

        stopTimer()
        updateCountdownUI()
    }

    private fun updateCountdownUI() {
        val hours = secondsRemaining / 3600
        val minutes = secondsRemaining / 60 % 60
        val seconds = secondsRemaining % 60

        val strHours: String = "%02d".format(hours)
        val strMinutes: String = "%02d".format(minutes)
        val strSeconds: String = "%02d".format(seconds)

        binding.timerTimeTextView.text = "$strHours:$strMinutes:$strSeconds"
    }

    private fun updateButtons() {
        when (timerState) {
            TimerState.Running -> {
                binding.startBtn.visibility = View.INVISIBLE
                binding.stopBtn.visibility = View.VISIBLE
                binding.pauseBtn.visibility = View.VISIBLE
                binding.timerCancelButton.text = getString(R.string.cancel)
            }
            TimerState.Stopped -> {
                binding.startBtn.visibility = View.VISIBLE
                binding.stopBtn.visibility = View.VISIBLE
                binding.pauseBtn.visibility = View.INVISIBLE
                binding.timerCancelButton.text = getString(R.string.done)

            }
            TimerState.Paused -> {
                binding.startBtn.visibility = View.VISIBLE
                binding.stopBtn.visibility = View.VISIBLE
                binding.pauseBtn.visibility = View.INVISIBLE
                binding.timerCancelButton.text = getString(R.string.cancel)
            }
        }
        //Log.i(TAG, timerState.toString())
    }

    private fun startTimer() {
        timerState = TimerState.Running
        updateButtons()

        timer = object : CountDownTimer(secondsRemaining * 1000, 1000) {
            override fun onFinish() = onTimerFinished()

            override fun onTick(millisUntilFinished: Long) {
                secondsRemaining = millisUntilFinished / 1000
                updateCountdownUI()
                //Log.i(TAG, "TIC")
            }
        }.start()
    }

    private fun pauseTimer() {
        timerState = TimerState.Paused
        timer?.cancel()
        updateButtons()
    }

    private fun stopTimer() {

        timerState = TimerState.Stopped
        timer?.cancel()
        secondsRemaining = timerLengthSeconds

        updateButtons()
        updateCountdownUI()

        stopSound()
    }

    private fun onTimerFinished() {
        timerState = TimerState.Stopped
        timer?.cancel()
        updateButtons()

        viewModel.currentHabit.value!!.isCompleted = true
        viewModel.currentHabit.value!!.totalCompletions += 1
        viewModel.updateHabit()

        playSound()
    }

    private fun saveAndReturn(): Boolean {
        //Log.i(TAG, "saveAndReturn")
        stopTimer()
        findNavController().navigateUp()

        return true
    }

    private fun playSound() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(requireContext(), R.raw.alerm_bell)
            mMediaPlayer!!.isLooping = false
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()
    }

    private fun pauseSound() {
        if (mMediaPlayer?.isPlaying == true) mMediaPlayer?.pause()
    }

    private fun stopSound() {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.stop()
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

    // 4. Destroys the MediaPlayer instance when the app is closed
    override fun onStop() {
        super.onStop()
        if (mMediaPlayer != null) {
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
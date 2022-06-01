package com.yseko.customtimers

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.yseko.customtimers.databinding.FragmentTimerBinding


class TimerFragment : Fragment() {
    private var _binding: FragmentTimerBinding? = null
    private val binding get() = _binding!!
    private val navigationArgs: TimerFragmentArgs by navArgs()

    private val viewModel: ActiveTimerViewModel by viewModels()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_timer, container, false)
        viewModel.setTime(navigationArgs.hours, navigationArgs.minutes, navigationArgs.seconds,navigationArgs.task)
        viewModel.setupView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.activeTimerViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.taskTimer.text = navigationArgs.task
        binding.playBtn.setOnClickListener{
            if(viewModel.pause.value == false) {
                viewModel.pauseTimer()
                it.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24)
            }else{
                viewModel.resumeTimer()
                it.setBackgroundResource(R.drawable.ic_baseline_pause_24)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelTimer()
    }



}
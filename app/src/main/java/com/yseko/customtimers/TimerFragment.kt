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


//    private var timer: CountDownTimer? = null
//    private var progress = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_timer, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarTimer)
        viewModel.setTime(navigationArgs.hours, navigationArgs.minutes, navigationArgs.seconds,navigationArgs.task)
        viewModel.setupView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarTimer.setNavigationOnClickListener{
            requireActivity().onBackPressed()
        }
        binding.activeTimerViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    override fun onDestroy() {
        super.onDestroy()
//        if(timer!= null){
//            timer?.cancel()
//            progress = 0
//        }
        viewModel.cancelTimer()
    }

//    private fun setupView(){
//        if(timer!=null){
//            timer?.cancel()
//            progress = 0
//        }
//        setProgressBar()
//    }
//
//    private fun setProgressBar(){
//        binding.progressBar.progress = progress
//        timer = object: CountDownTimer(10000, 1000){
//            override fun onTick(millisUntilFinished: Long) {
//                progress++
//                binding.progressBar.progress = 10 - progress
//                binding.tvTimer.text = (10 - progress).toString()
//            }
//
//            override fun onFinish() {
//                Toast.makeText(
//                    requireContext(),
//                    "Timer end",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//
//        }.start()
//    }


}
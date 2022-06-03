package com.yseko.customtimers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yseko.customtimers.databinding.FragmentAddTimerBinding


class AddTimerFragment : Fragment() {

    private var _binding: FragmentAddTimerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TimerViewModel by activityViewModels{
        TimerViewModelFactory(
            (activity?.application as TimerApplication).database.timerDao()
        )
    }

    private val navigationArgs: AddTimerFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.hoursPicker.maxValue = 23
        binding.hoursPicker.minValue = 0
        binding.minutesPicker.maxValue = 59
        binding.minutesPicker.minValue = 0
        binding.secondsPicker.maxValue = 59
        binding.secondsPicker.minValue = 0


        if(navigationArgs.id != -1){
            binding.taskInput.setText(navigationArgs.task)
            binding.hoursPicker.value = navigationArgs.hours
            binding.minutesPicker.value = navigationArgs.minutes
            binding.secondsPicker.value = navigationArgs.seconds
            binding.createTimerBtn.text = "Edit"
            binding.createTimerBtn.setOnClickListener{
                viewModel.editTimer(navigationArgs.id, binding.hoursPicker.value, binding.minutesPicker.value, binding.secondsPicker.value, binding.taskInput.text.toString())
                val action = AddTimerFragmentDirections.actionAddTimerFragmentToListFragment()
                this.findNavController().navigate(action)
            }
        }
        else{
            binding.createTimerBtn.setOnClickListener{
                viewModel.addTimer(binding.hoursPicker.value, binding.minutesPicker.value, binding.secondsPicker.value, binding.taskInput.text.toString())
                val action = AddTimerFragmentDirections.actionAddTimerFragmentToListFragment()
                this.findNavController().navigate(action)
            }
        }
    }



}
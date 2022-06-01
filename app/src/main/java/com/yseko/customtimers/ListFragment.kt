package com.yseko.customtimers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.yseko.customtimers.databinding.FragmentListBinding


class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TimerViewModel by activityViewModels{
        TimerViewModelFactory(
            (activity?.application as TimerApplication).database.timerDao()
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.goToTimer.setOnClickListener{
//            val action = ListFragmentDirections.actionListFragmentToTimerFragment()
//            this.findNavController().navigate(action)
//        }
        val adapter = TimerAdapter{
            val action = ListFragmentDirections.actionListFragmentToTimerFragment(it.task, it.hours, it.minutes, it.seconds)
            this.findNavController().navigate(action)
        }


        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.adapter = adapter

        viewModel.allTimers.observe(this.viewLifecycleOwner){
            it.let{
                adapter.submitList(it)
            }
        }

        binding.addTimer.setOnClickListener{
//            viewModel.addTimer(0, 0, 30, "thirtysec")
            val action = ListFragmentDirections.actionListFragmentToAddTimerFragment()
            this.findNavController().navigate(action)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
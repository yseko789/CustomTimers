package com.yseko.customtimers

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val action = ListFragmentDirections.actionListFragmentToAddTimerFragment(action = "Create New Timer")
        this.findNavController().navigate(action)
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = TimerAdapter(
            {
                val action = ListFragmentDirections.actionListFragmentToTimerFragment(it.task, it.hours, it.minutes, it.seconds)
                this.findNavController().navigate(action)
            },
            {
                viewModel.removeTimer(it.id)
                println(it.id)
            },
            {
                val action = ListFragmentDirections.actionListFragmentToAddTimerFragment("Edit Timer", it.id, it.task, it.hours, it.minutes, it.seconds)
                this.findNavController().navigate(action)
            }
        )


        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.adapter = adapter

        viewModel.allTimers.observe(this.viewLifecycleOwner){
            it.let{
                adapter.submitList(it)
            }
        }

//        binding.addTimer.setOnClickListener{
//            val action = ListFragmentDirections.actionListFragmentToAddTimerFragment()
//            this.findNavController().navigate(action)
//        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
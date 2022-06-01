package com.yseko.customtimers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.yseko.customtimers.databinding.FragmentStartTimerBinding


class StartTimerFragment : Fragment() {
    private var _binding: FragmentStartTimerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.flStart.setOnClickListener{
//            val action = StartTimerFragmentDirections.actionStartTimerFragmentToTimerFragment(hours = 1, minutes = 0, seconds = 0, task = "Exercise")
//            this.findNavController().navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
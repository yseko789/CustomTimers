package com.yseko.customtimers

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yseko.customtimers.data.Timer
import com.yseko.customtimers.databinding.TimerListTimerBinding

class TimerAdapter(private val onTimerClicked: (Timer)->Unit, private val onDeleteClicked: (Timer)->Unit, private val onEditClicked: (Timer)->Unit): ListAdapter<Timer, TimerAdapter.TimerViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerViewHolder {
        return TimerViewHolder(
            TimerListTimerBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: TimerViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onTimerClicked(current)
        }
        holder.bind(current)
        holder.binding.timerOptions.setOnClickListener{
            println("clicked")
            val popupMenu: PopupMenu = PopupMenu(holder.itemView.context, it)
            popupMenu.inflate(R.menu.timer_menu)
            popupMenu.show()

            popupMenu.setOnMenuItemClickListener { menuItem->
                when(menuItem.itemId){
                    R.id.edit ->{
                        onEditClicked(current)
                    }
                    R.id.delete->{
                        onDeleteClicked(current)
                    }
                }
                true
            }
        }

    }

    class TimerViewHolder(var binding: TimerListTimerBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(timer: Timer){
            val time = String.format("%02d:%02d:%02d", timer.hours, timer.minutes, timer.seconds)
            binding.timerTime.text = time
            binding.timerTask.text = timer.task

        }
    }

    companion object{
        private val DiffCallback = object: DiffUtil.ItemCallback<Timer>(){
            override fun areItemsTheSame(oldItem: Timer, newItem: Timer): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Timer, newItem: Timer): Boolean {
                return (oldItem.hours == newItem.hours && oldItem.minutes==newItem.minutes && oldItem.seconds == newItem.seconds && oldItem.task == newItem.task)
            }

        }
    }
}
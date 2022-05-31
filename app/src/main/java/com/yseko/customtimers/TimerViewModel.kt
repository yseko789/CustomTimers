package com.yseko.customtimers

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yseko.customtimers.data.TimerDao
import com.yseko.customtimers.data.Timer
import kotlinx.coroutines.launch

class TimerViewModel(private val timerDao: TimerDao): ViewModel() {

    val allTimers: LiveData<List<Timer>> = timerDao.getAllTimers().asLiveData()

    fun addTimer(hours: Int, minutes: Int, seconds: Int, task: String){
        val timer = Timer(hours = hours, minutes = minutes, seconds = seconds, task=task)
        insertTimer(timer)
    }

    private fun insertTimer(timer: Timer){
        viewModelScope.launch {
            timerDao.insert(timer)
        }
    }

    fun getTimer(id: Int): LiveData<Timer>{
        return timerDao.getTimer(id).asLiveData()
    }

    fun removeTimer(id: Int){
        val timer = getTimer(id).value
        if (timer != null) {
            deleteTimer(timer)
        }
    }

    private fun deleteTimer(timer: Timer){
        viewModelScope.launch {
            timerDao.delete(timer)
        }
    }

    fun editTimer(id: Int){
        val timer = getTimer(id).value
        if(timer!=null){
            updateTimer(timer)
        }
    }

    private fun updateTimer(timer: Timer){
        viewModelScope.launch {
            timerDao.update(timer)
        }
    }

}
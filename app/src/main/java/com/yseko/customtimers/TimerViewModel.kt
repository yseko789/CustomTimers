package com.yseko.customtimers

import androidx.lifecycle.*
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
        viewModelScope.launch {
            timerDao.deleteById(id)
        }
    }



    fun editTimer(id: Int, hours: Int, minutes: Int, seconds: Int, task: String){
        viewModelScope.launch{
            timerDao.update(id, hours, minutes, seconds, task)
        }
    }



}

class TimerViewModelFactory(private val timerDao: TimerDao): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TimerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TimerViewModel(timerDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
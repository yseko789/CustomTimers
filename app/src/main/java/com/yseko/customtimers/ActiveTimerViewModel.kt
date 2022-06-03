package com.yseko.customtimers

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActiveTimerViewModel :ViewModel() {

    private val _hours = MutableLiveData<Int>()
    private val _minutes = MutableLiveData<Int>()
    private val _seconds = MutableLiveData<Int>()
    private var _task: String = ""
    private val _milliSeconds = MutableLiveData<Int>()
    private val _time = MutableLiveData<String>()

    val hours: LiveData<Int> get() = _hours
    val minutes: LiveData<Int> get() = _minutes
    val seconds: LiveData<Int> get() = _seconds
    val task: String get() = _task
    val milliSeconds = _milliSeconds
    val time: LiveData<String> get() = _time

    var millisecondsOriginal: Int = 0
    val pause = MutableLiveData(true)

    private var hoursOriginal: Int = 0
    private var minutesOriginal: Int=0
    private  var secondsOriginal: Int=0


    private var timer: CountDownTimer? = null


    //timer has to be rebuilt everytime!!//
    //
    //
    //


    fun buildTimer(){
        timer = object: CountDownTimer(milliSeconds.value!!.toLong(), 1000){
            override fun onTick(millisUntilFinished: Long) {
                    if (seconds.value == 0) {
                        _seconds.value = 59
                        if (minutes.value == 0) {
                            _minutes.value = 59
                            _hours.value = _hours.value?.minus(1)
                        } else {
                            _minutes.value = _minutes.value?.minus(1)
                        }
                    } else {
                        _seconds.value = _seconds.value?.minus(1)
                    }
                    _time.value =
                        String.format("%02d:%02d:%02d", hours.value, minutes.value, seconds.value)
                    _milliSeconds.value = _milliSeconds.value?.minus(1000)
            }
            override fun onFinish() {
                pauseTimer()
                _hours.value = hoursOriginal
                _minutes.value = minutesOriginal
                _seconds.value = secondsOriginal
                _milliSeconds.value = millisecondsOriginal
                _time.value= String.format("%02d:%02d:%02d", _hours.value, _minutes.value, _seconds.value)

            }
        }
    }

    fun setOriginalTime(hours: Int, minutes: Int, seconds: Int, task: String){
        hoursOriginal = hours
        minutesOriginal = minutes
        secondsOriginal = seconds
        millisecondsOriginal = hours * 3600000 + minutes * 60000 + seconds * 1000
        _task = task
    }

    fun setProgressTime(){
        _hours.value = hoursOriginal
        _minutes.value = minutesOriginal
        _seconds.value = secondsOriginal
        _milliSeconds.value = millisecondsOriginal
        _time.value = String.format("%02d:%02d:%02d", _hours.value, _minutes.value, _seconds.value)
    }

    fun cancelTimer(){
        if(timer!=null){
            timer?.cancel()
            _hours.value = 0
            _minutes.value = 0
            _seconds.value =0
            _milliSeconds.value = 0
            _task = ""
        }
    }

//    fun startTimer(){
//        buildTimer()
//        timer?.start()
//    }

    fun pauseTimer(){
        pause.value = true
        timer?.cancel()
    }

    fun resumeTimer(){
        pause.value = false
        buildTimer()
        timer?.start()

    }


    fun resetTimer(){
        cancelTimer()
        setProgressTime()
        buildTimer()
        pauseTimer()
    }




}
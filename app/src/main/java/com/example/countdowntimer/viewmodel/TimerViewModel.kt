package com.example.countdowntimer.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimerViewModel : ViewModel() {

    companion object {
        const val DONE = 0L
        const val ONE_SECOND = 1000L

        // Time of the session
        const val START_TIME = (ONE_SECOND * 60) * 2
    }

    private lateinit var timer: CountDownTimer

    private var _timerIsRunning = MutableLiveData<Boolean>()
    val timerIsRunning: LiveData<Boolean>
        get() = _timerIsRunning

    // The time left
    private var _timeLeft = MutableLiveData<Long>()
    val timeLeft: LiveData<Long>
        get() = _timeLeft

    // The text of countdown timer
    private var _countdownText = MutableLiveData<String>()
    val countdownText: LiveData<String>
        get() = _countdownText

    // The text of start/pause button
    private var _startPauseButtonText = MutableLiveData<String>()
    val startPauseButtonText: LiveData<String>
        get() = _startPauseButtonText

    init {
        _startPauseButtonText.value = "start"
        _timeLeft.value = START_TIME
        _timerIsRunning.value = false

        updateCountDownText()
    }

    fun startOrPause() {
        if (_timerIsRunning.value!!) {
            pauseTimer()
        } else {
            startTimer()
        }
    }

    private fun startTimer() {
        _timerIsRunning.value = true
        _startPauseButtonText.value = "pause"
        timer = object : CountDownTimer(timeLeft.value!!, ONE_SECOND) {
            override fun onTick(timeUntilFinish: Long) {
                if (timerIsRunning.value!!) {
                    _timeLeft.value = timeUntilFinish
                    updateCountDownText()
                }
            }

            override fun onFinish() {
                _timeLeft.value = DONE
                _timerIsRunning.value = false
            }
        }.start()
    }

    private fun pauseTimer() {
        timer.cancel()
        _timerIsRunning.value = false
        _startPauseButtonText.value = "start"
    }

    fun resetTimer() {
        _timeLeft.value = START_TIME
        timer.cancel()
        _startPauseButtonText.value = "start"
        _timerIsRunning.value = false
        updateCountDownText()
    }

    fun updateCountDownText() {
        val minutes = timeLeft.value!! / 1000 / 60
        val seconds = timeLeft.value!! / 1000 % 60

        val timeLeftFormatted = "${minutes.toString().padStart(2, '0')}:" +
                seconds.toString().padStart(2, '0')

        _countdownText.value = timeLeftFormatted
    }
}
package com.example.countdowntimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.countdowntimer.databinding.ActivityMainBinding
import com.example.countdowntimer.viewmodel.TimerViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: TimerViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding  = DataBindingUtil.setContentView(
            this, R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(TimerViewModel::class.java)
        binding.timerViewModel = viewModel
        binding.lifecycleOwner = this
    }
}
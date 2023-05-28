package com.example.project10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var countDownTimer: CountDownTimer? = null
    private var timerDuration: Long = 10000
    private var pauseOffset: Long = 0
    var tvTimer: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart: Button = findViewById(R.id.start)
        val btnPause: Button = findViewById(R.id.pause)
        val btnReset: Button = findViewById(R.id.reset)

        tvTimer = findViewById(R.id.tvTimer)
        tvTimer?.text = (timerDuration/1000).toString()

        btnStart.setOnClickListener {
            startTimer(pauseOffset)
            btnStart.isClickable = false
        }

        btnPause.setOnClickListener {
            pauseTimer()
            btnStart.isClickable = true
            btnStart.text = "RESUME"
        }

        btnReset.setOnClickListener {
            resetTimer()
            btnStart.isClickable = true
            btnStart.text = "START"

        }

    }

    private fun startTimer(pauseOffsetL: Long) {
        countDownTimer = object : CountDownTimer(timerDuration-pauseOffsetL, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                pauseOffset = timerDuration-millisUntilFinished
                tvTimer?.text = (millisUntilFinished/1000).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@MainActivity, "Timer is finished", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    private fun pauseTimer() {
        countDownTimer?.cancel()
    }

    private fun resetTimer() {
        countDownTimer?.cancel()
        tvTimer?.text = (timerDuration/1000).toString()
        countDownTimer = null
        pauseOffset = 0
    }
}
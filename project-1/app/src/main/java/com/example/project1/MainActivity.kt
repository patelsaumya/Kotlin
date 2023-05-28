package com.example.project1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    // Activity is just a screen
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // activity_main is the layout used for this Activity
        val btnClickMe = findViewById<Button>(R.id.mybutton)
        val tvMyTextView = findViewById<TextView>(R.id.mytextview)
        var timesClicked = 0
        btnClickMe.setOnClickListener {
            timesClicked += 1
            tvMyTextView.text = timesClicked.toString()
            Toast.makeText(this, "Hey Android Developer!", Toast.LENGTH_LONG).show()
        }
    }
}
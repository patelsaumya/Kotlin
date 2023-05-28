package com.example.project4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart: Button = findViewById(R.id.btn_start)
        val etName: EditText = findViewById(R.id.et_name)
        btnStart.setOnClickListener {
            if (etName.text.isEmpty()) {
                // Toast should be displayed in MainActivity so the context is "this"
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_LONG).show()
            } else {
                // intent says from which activity we want to move to which other activity

                // I'm going from "this" activity to QuizQuestionsActivity
                val intent = Intent(this, QuizQuestionsActivity::class.java)
                intent.putExtra(Constants.USER_NAME, etName.text.toString()) // sending data from one activity to another
                startActivity(intent)
                finish() // finish the current activity and do not keep this current activity open
            }
        }
    }
}
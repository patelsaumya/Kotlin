package com.example.project8

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Why Coroutines ?
// Some operations take longer time to complete, and these tasks block the user interface
// And, until they are completed, the user interface is not interacted with.
// Android UI system was built to only accept delays of maximum 5 seconds

// These coroutines run on different thread, and so the UI thread will not block

// Coroutine functions should run in a coroutine scope/block

class MainActivity : AppCompatActivity() {

    var customProgressDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnExecute: Button = findViewById(R.id.btn_execute)
        btnExecute.setOnClickListener {
            showProgressDialog()
            // lifecycleScope is a coroutine block built to properly handle coroutines in any activity class
            lifecycleScope.launch {
                execute("Task executed successfully")
            }
        }
    }

    // "suspend" will make "execute" a coroutine function
    // suspend function cannot be executed in the main thread
    private suspend fun execute(result: String) {
        // withContext is a method created to move an operation into a different thread until it completes its process,
        // and then it is moved back into the original thread.
        // Dispatchers.IO -> InputOutput thread -> which will execute the following task
        withContext(Dispatchers.IO) {
            for(i in 1..100000) {
                Log.e("delay : ", "" + i)
            }
            runOnUiThread {
                cancelProgressDialog()
                Toast.makeText(this@MainActivity, result, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showProgressDialog() {
        customProgressDialog = Dialog(this@MainActivity)
        customProgressDialog?.setContentView(R.layout.dialog_custom_progress)
        customProgressDialog?.show()
    }

    private fun cancelProgressDialog() {
        if (customProgressDialog != null) {
            customProgressDialog?.dismiss()
            customProgressDialog = null
        }
    }
}
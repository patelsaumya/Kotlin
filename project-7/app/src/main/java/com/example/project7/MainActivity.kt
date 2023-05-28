package com.example.project7

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    // For showing Toast, activity is not required
    // SnackBar can only be showed inside an activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageButton: ImageButton = findViewById(R.id.image_button)
        imageButton.setOnClickListener { view ->
            Snackbar.make(view, "You have clicked image button.", Snackbar.LENGTH_LONG).show()
        }

        val btnAlertDialog: Button = findViewById(R.id.btn_alert_dialog)
        btnAlertDialog.setOnClickListener { view ->
            alertDialogLauncher()
        }

        val btnCustomDialog: Button = findViewById(R.id.btn_custom_dialog)
        btnCustomDialog.setOnClickListener {
            customDialogLauncher()
        }

        val btnCustomProgress: Button = findViewById(R.id.btn_custom_progress_dialog)
        btnCustomProgress.setOnClickListener {
            customProgressDialogLauncher()
        }
    }

    private fun alertDialogLauncher() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Alert")
        builder.setMessage("This is Alert Dialog. What is your Action ?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton("Yes") { dialogInterface, _ ->
            Toast.makeText(applicationContext, "clicked yes", Toast.LENGTH_LONG).show()
            dialogInterface.dismiss()
        }
        builder.setNeutralButton("Cancel") { dialogInterface, _ ->
            Toast.makeText(applicationContext, "clicked cancel\noperation cancel", Toast.LENGTH_LONG).show()
            dialogInterface.dismiss()
        }
        builder.setNegativeButton("No") { dialogInterface, _ ->
            Toast.makeText(applicationContext, "clicked no", Toast.LENGTH_LONG).show()
            dialogInterface.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false) // Will not allow user to cancel after clicking on remaining screen area.
        alertDialog.show()
    }

    private fun customDialogLauncher() {
        val customDialog = Dialog(this)
        customDialog.setContentView(R.layout.dialog_custom)
        customDialog.findViewById<TextView>(R.id.tv_submit).setOnClickListener {
            Toast.makeText(applicationContext, "clicked submit", Toast.LENGTH_LONG).show()
            customDialog.dismiss()
        }
        customDialog.findViewById<TextView>(R.id.tv_cancel).setOnClickListener {
            Toast.makeText(applicationContext, "clicked cancel", Toast.LENGTH_LONG).show()
            customDialog.dismiss()
        }
        customDialog.show()
    }

    private fun customProgressDialogLauncher() {
        val customProgressDialog = Dialog(this)
        customProgressDialog.setContentView(R.layout.dialog_custom_progress)
        customProgressDialog.show()
    }
}
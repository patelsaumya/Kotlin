package com.example.project6

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private val cameraResultLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            isGranted ->
            if (isGranted) {
                Toast.makeText(this, "Permission granted for camera.", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission denied for camera.", Toast.LENGTH_LONG).show()
            }
        }

    private val cameraAndLocationResultLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            permissions ->
            permissions.entries.forEach{
                val permissionName = it.key
                val isGranted = it.value
                if (isGranted) {
                    if (permissionName == Manifest.permission.ACCESS_FINE_LOCATION) {
                        Toast.makeText(this, "Permission granted for Fine Location", Toast.LENGTH_LONG).show()
                    } else if (permissionName == Manifest.permission.ACCESS_COARSE_LOCATION) {
                        Toast.makeText(this, "Permission granted for Coarse Location", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Permission granted for Camera", Toast.LENGTH_LONG).show()
                    }
                } else {
                    if (permissionName == Manifest.permission.ACCESS_FINE_LOCATION) {
                        Toast.makeText(this, "Permission denied for Fine Location", Toast.LENGTH_LONG).show()
                    } else if (permissionName == Manifest.permission.ACCESS_COARSE_LOCATION) {
                        Toast.makeText(this, "Permission denied for Coarse Location", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Permission denied for Camera", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCameraPermission: Button = findViewById(R.id.btnCameraPermission)
        btnCameraPermission.setOnClickListener {
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
//                    shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
//                // will be executed when user denied the permission previously
//                showRationaleDialog("Permission Demo requires camera access",
//                "Camera cannot be used because Camera access is denied")
//            } else {
//                cameraResultLauncher.launch(Manifest.permission.CAMERA)
//            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                // will be executed when user denied the Camera permission previously
                showRationaleDialog("Permission Demo requires Camera access",
                    "Camera cannot be used because Camera access is denied")
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                // will be executed when user did not give access to Fine Location previously
                showRationaleDialog("Permission Demo requires Fine Location access",
                    "Fine Location access is denied")
            } else {
                cameraAndLocationResultLauncher.launch(
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                )
            }
        }
    }

    /* Shows rationale dialog for displaying why the app needs permission.
    It is only shown if the user has denied the permission request previously */

    private fun showRationaleDialog(title: String, message: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Cancel") {
                dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }
}
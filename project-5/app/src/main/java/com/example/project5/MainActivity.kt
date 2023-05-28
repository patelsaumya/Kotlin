package com.example.project5

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.media.MediaScannerConnection
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.get
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    private var drawingView: DrawingView? = null
    private var imageButtonCurrentPaint: ImageButton? = null
    var customProgressDialog: Dialog? = null

    companion object {
        private const val STORAGE_PERMISSION_CODE = 1
        private const val GALLERY = 2
        private const val AUTHORITY = "${BuildConfig.APPLICATION_ID}.fileprovider"
    }

    // launch will be based on an Intent
    val openGalleryLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->
            if(result.resultCode == RESULT_OK && result.data != null) {
                val imageBackground: ImageView = findViewById(R.id.iv_background)
                imageBackground.setImageURI(result.data?.data) // URI is location of the data on your device
            }
        }

    // launch will be based on an Array of Strings
    val requestPermission: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            permissions ->
            permissions.entries.forEach {
                val permissionName = it.key
                val isGranted = it.value
                if (isGranted) {
                    if (permissionName == Manifest.permission.READ_MEDIA_IMAGES) {
//                        Toast.makeText(
//                            this@MainActivity,
//                            "Permission Granted! Now you can read the Storage Files.",
//                            Toast.LENGTH_SHORT
//                        ).show()
                        // we can also run intents to go to other applications (in this case, GALLERY)
                        val pickIntent = Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        openGalleryLauncher.launch(pickIntent)
                    } else {
//                        Toast.makeText(
//                            this@MainActivity,
//                            "Permission Granted! Now you can write into the Store.",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        showProgressDialog()
//                        lifecycleScope.launch {
//                            val flDrawingView: FrameLayout = findViewById(R.id.fl_drawing_view_container)
//                            saveBitmapFile(getBitmapFromView(flDrawingView))
//                        }
                    }
                } else {
                    if (permissionName == Manifest.permission.READ_MEDIA_IMAGES || permissionName == Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                        Toast.makeText(
                            this@MainActivity,
                            "Oops! You just Denied the Permission",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawingView = findViewById(R.id.drawing_view)
        drawingView?.setSizeForBrush(20.toFloat())

        val linearLayoutPaintColors = findViewById<LinearLayout>(R.id.ll_paint_colors)
        imageButtonCurrentPaint = linearLayoutPaintColors[1] as ImageButton // black color
        imageButtonCurrentPaint!!.setImageDrawable(
            ContextCompat.getDrawable(this, R.drawable.palette_pressed)
        )

        val ib_brush: ImageButton = findViewById(R.id.ib_brush)
        ib_brush.setOnClickListener {
            showBrushSizeChooserDialog()
        }

        val ib_undo: ImageButton = findViewById(R.id.ib_undo)
        ib_undo.setOnClickListener {
            drawingView?.onClickUndo()
        }

        val ib_save: ImageButton = findViewById(R.id.ib_save)
        ib_save.setOnClickListener {
//            if (isReadAndWriteStorageAllowed()) {
                // in latest android versions: Granting a READ permission also implicitly grants you WRITE permission
            showProgressDialog()
            lifecycleScope.launch {
                val flDrawingView: FrameLayout = findViewById(R.id.fl_drawing_view_container)
                saveBitmapFile(getBitmapFromView(flDrawingView))
            }
//            } else {
//                requestStoragePermission("write")
//            }
        }

        val ib_gallery: ImageButton = findViewById(R.id.ib_gallery)
        ib_gallery.setOnClickListener {
            requestStoragePermission("read")
        }
    }

    private fun showBrushSizeChooserDialog() {
        var brushDialog = Dialog(this)
        brushDialog.setContentView(R.layout.dialog_brush_size)
        brushDialog.setTitle("Brush size: ")
        val smallBtn: ImageButton = brushDialog.findViewById(R.id.ib_small_brush)
        smallBtn.setOnClickListener {
            drawingView?.setSizeForBrush(10.toFloat())
            brushDialog.dismiss()
        }
        val mediumBtn: ImageButton = brushDialog.findViewById(R.id.ib_medium_brush)
        mediumBtn.setOnClickListener {
            drawingView?.setSizeForBrush(20.toFloat())
            brushDialog.dismiss()
        }
        val largeBtn: ImageButton = brushDialog.findViewById(R.id.ib_large_brush)
        largeBtn.setOnClickListener {
            drawingView?.setSizeForBrush(30.toFloat())
            brushDialog.dismiss()
        }
        brushDialog.show()

    }

    fun paintClicked(view: View) {
        if (view !== imageButtonCurrentPaint) {
            val imageButton: ImageButton = view as ImageButton
            val colorTag = imageButton.tag.toString()
            drawingView?.setColor(colorTag)
            imageButton.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.palette_pressed)
            )
            imageButtonCurrentPaint?.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.palette_normal)
            )
            imageButtonCurrentPaint = view
        }
    }

    private fun isReadAndWriteStorageAllowed() : Boolean {
        val result = ContextCompat.checkSelfPermission(this@MainActivity,
            Manifest.permission.READ_MEDIA_IMAGES
        )
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestStoragePermission(readOrWrite: String) {

        if (readOrWrite == "read") {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.READ_MEDIA_IMAGES
                )) {
                showRationaleDialog("Drawing App", "This app needs to access your External Storage")
            } else {
                requestPermission.launch(arrayOf(
                    Manifest.permission.READ_MEDIA_IMAGES
                ))
            }
        } else if (readOrWrite == "write") {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.WRITE_EXTERNAL_STORAGE
                )) {
                showRationaleDialog("Drawing App", "This app needs to access your External Storage")
            } else {
                requestPermission.launch(arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ))
            }
        }
    }

    private fun getBitmapFromView(view: View): Bitmap {
        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) {
            bgDrawable.draw(canvas)
        } else {
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)
        return returnedBitmap
    }

    private suspend fun saveBitmapFile(bitmap: Bitmap?) : String{
        var result = ""
        withContext(Dispatchers.IO) {
            if (bitmap != null) {
                try {
                    val bytes = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, bytes)
                    val f = File(externalCacheDir?.absoluteFile.toString()
                            + File.separator + "DrawingApp_" + System.currentTimeMillis() / 1000 + ".png")
                    val fo = FileOutputStream(f)
                    fo.write(bytes.toByteArray())
                    fo.close()
                    result = f.absolutePath
                    runOnUiThread {
                        cancelProgressDialog()
                        if (result.isNotEmpty()) {
                            Toast.makeText(
                                this@MainActivity,
                                "File saved successfully : $result",
                                Toast.LENGTH_SHORT
                            ).show()
                            shareImage(result)
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "Something went wrong while saving the file.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    result = ""
                    e.printStackTrace()
                }
            }
        }

        return result
    }

    private fun shareImage(result: String) {
//        MediaScannerConnection.scanFile(this@MainActivity, arrayOf(result), null) {
//            path, uri ->
//            val shareIntent = Intent()
//            shareIntent.action = Intent.ACTION_SEND
//            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
//            shareIntent.type = "image/png"
//            startActivity(Intent.createChooser(shareIntent, "Share"))
//        }
        MediaScannerConnection.scanFile(
            this@MainActivity,
            arrayOf(result),
            null
        ) { path, _ ->

            // Use the FileProvider to get a content URI
            val requestFile = File(path)
            val fileUri: Uri? = try {
                FileProvider.getUriForFile(
                    this@MainActivity,
                    AUTHORITY,
                    requestFile)
            } catch (e: IllegalArgumentException) {
                Log.e("File Selector",
                    "The selected file can't be shared: $requestFile")
                null
            }


            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "image/png"
            shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri)

            startActivity(
                Intent.createChooser(
                    shareIntent, "Share"
                )
            )
        }

    }

    private fun showRationaleDialog(title: String, message: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
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
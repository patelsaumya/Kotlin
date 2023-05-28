package com.example.project11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.example.project11.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        tts = TextToSpeech(this, this)
        binding?.btnSpeak?.setOnClickListener { view ->
            if (binding?.etEnteredText?.text!!.isEmpty()) {
                Toast.makeText(
                    this@MainActivity,
                    "Enter a text to speak",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                speakOut(binding?.etEnteredText?.text.toString())
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported")
            }
        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }

    private fun speakOut(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
        // TextToSpeech.QUEUE_FLUSH: delete the previous text which was spoken
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        tts?.stop()
        tts?.shutdown()
    }
}
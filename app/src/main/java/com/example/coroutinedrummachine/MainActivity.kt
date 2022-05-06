package com.example.coroutinedrummachine

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var ring2: MediaPlayer
    private lateinit var ring1: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ring2 = MediaPlayer.create(this, R.raw.ring2)
        ring1 = MediaPlayer.create(this, R.raw.ring1)
        btnStart.setOnClickListener {
            runBlocking {
                launch { playBeats("x-x-x-x-x-x-x-x-x-x-x-x-", R.raw.ring1)
                }
                playBeats("x-----x-----x-----x-----", R.raw.ring2)
            }
        }
    }

    suspend fun playBeats(beats: String, fileId: Int){
        val parts = beats.split("x")
        var count = 0
        for(part in parts){
            count += part.length + 1
            if(part == ""){
                if(fileId == R.raw.ring2)
                    ring2.start()
                else
                    ring1.start()
            }else{
                delay(1000 * (part.length + 1L))
                if(count < beats.length){
                    if(fileId == R.raw.ring2)
                        ring2.start()
                    else
                        ring1.start()
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        ring2.stop()
        ring1.stop()
    }
}

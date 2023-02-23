package edu.quinnipiac.ser210.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer

class MainActivity : AppCompatActivity() {
    lateinit var stopwatch: Chronometer
    var running = false
    var offest: Long = 0

    // Add key Strings for bundle
    val OFFSET_KEY = "offset"
    val RUNNING_KEY = "running"
    val BASE_KEY = "base"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // referrencing stopwatch
        stopwatch = findViewById(R.id.stopwatch)

        if (savedInstanceState != null){
            offest = savedInstanceState.getLong(OFFSET_KEY)
            running = savedInstanceState.getBoolean(RUNNING_KEY)
            if (running){
                stopwatch.base = savedInstanceState.getLong(BASE_KEY)
                stopwatch.start()
            } else{
                setBaseTime()
            }
        }


        // start button logic
        val startButton = findViewById<Button>(R.id.start_button)
        startButton.setOnClickListener{
            if (!running){
                setBaseTime()
                stopwatch.start()
                running = true
            }

            // Pause button logic
            val pauseButton = findViewById<Button>(R.id.pause_button)
            pauseButton.setOnClickListener{
                if (running){
                    saveOffSet()
                    stopwatch.stop()
                    running = false
                }

                // reset button logic
                val resetButton = findViewById<Button>(R.id.reset_button)
                resetButton.setOnClickListener{
                    offest = 0
                    setBaseTime()
                }
            }
        }
    }

    // update stopwatch base
    fun setBaseTime(){
        stopwatch.base = SystemClock.elapsedRealtime() - offest
    }

    // saving the instance of offset
    fun saveOffSet(){
        offest = SystemClock.elapsedRealtime() - stopwatch.base
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putLong(OFFSET_KEY, offest)
        savedInstanceState.putBoolean(RUNNING_KEY, running)
        savedInstanceState.putLong(BASE_KEY, stopwatch.base)
        super.onSaveInstanceState(savedInstanceState)
    }

    override fun onPause() {
        super.onPause()
        if (running){
            saveOffSet()
            stopwatch.stop()
        }
    }

    override fun onResume() {
        super.onResume()
        if (running){
            setBaseTime()
            stopwatch.start()
            offest = 0
        }
    }
}
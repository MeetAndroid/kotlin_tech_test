package com.meet.cradletaskapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import java.io.*
import java.lang.Exception
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    private lateinit var buttonWeather: Button
    private lateinit var buttonFootball: Button
    private lateinit var textView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textView)
        buttonWeather = findViewById(R.id.btnReadWeather)
        buttonWeather.setOnClickListener {
            textView.text =
                String.format(
                    "Day Value:  ${
                        Task().performTask(
                            this, "weather.dat",
                            1, 2, 0
                        )
                    }"
                )
        }

        buttonFootball = findViewById(R.id.btnReadFootball)
        buttonFootball.setOnClickListener {
        }
    }

}
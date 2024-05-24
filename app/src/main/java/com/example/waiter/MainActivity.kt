package com.example.waiter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var tvMessage: TextView
    private var minutesElapsed = 0
    private val timeTickReceiver = TimeTickReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvMessage = findViewById(R.id.tvMessage)
        val btnStopWaiting: Button = findViewById(R.id.btnStopWaiting)
        val backgroundImage: ImageView = findViewById(R.id.backgroundImage)

        btnStopWaiting.setOnClickListener {
            unregisterReceiver(timeTickReceiver)
            Toast.makeText(this, "Прощай, Ждун!", Toast.LENGTH_SHORT).show()
            backgroundImage.visibility = View.GONE
            tvMessage.text = "вы более не созирцаете..."
        }

        registerReceiver(timeTickReceiver, IntentFilter(Intent.ACTION_TIME_TICK))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(timeTickReceiver)
    }

    inner class TimeTickReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == Intent.ACTION_TIME_TICK) {
                minutesElapsed++
                tvMessage.text = "время созерцания: $minutesElapsed мин."
            }
        }
    }

    inner class BatteryLowReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == Intent.ACTION_BATTERY_LOW) {
                tvMessage.text = "накормите Ждуна, силы на исходе!"
            }
        }
    }
}
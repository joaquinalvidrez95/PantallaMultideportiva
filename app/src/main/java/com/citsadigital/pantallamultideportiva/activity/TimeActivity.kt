package com.citsadigital.pantallamultideportiva.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.NumberPicker
import com.citsadigital.pantallamultideportiva.R
import kotlinx.android.synthetic.main.activity_time.*

class TimeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time)
        setupNumberPicker(arrayOf(pickerMinutes, pickerSeconds), 59, 0)
        setupNumberPicker(arrayOf(pickerHours), 99, 0)

        //        mainViewModel.getTimerStopwatch().observe(this, Observer {
//            pickerHours.value = Integer.parseInt(it?.substring(0, 2))
//            pickerMinutes.value = Integer.parseInt(it?.substring(2, 4))
//            pickerSeconds.value = Integer.parseInt(it?.substring(4, 6))
//        })

    }

    private fun setupNumberPicker(pickers: Array<NumberPicker>, max: Int, min: Int) {
        for (p in pickers) {
            p.apply {
                maxValue = max
                minValue = min
            }
        }
    }
}

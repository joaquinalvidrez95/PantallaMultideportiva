package com.citsadigital.pantallamultideportiva.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.citsadigital.pantallamultideportiva.R
import com.citsadigital.pantallamultideportiva.fragment.TimePickerFragment
import com.citsadigital.pantallamultideportiva.model.BoardTime
import com.citsadigital.pantallamultideportiva.model.TIME_FORMAT_HOURS_MINUTES
import com.citsadigital.pantallamultideportiva.model.TIME_FORMAT_MINUTES_SECONDS
import com.citsadigital.pantallamultideportiva.util.BUNDLE_KEY_BOARD_TIME
import kotlinx.android.synthetic.main.activity_time.*

class TimeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time)
        val boardTime = intent.getParcelableExtra<BoardTime>(BUNDLE_KEY_BOARD_TIME)
        val timePickerFragment = TimePickerFragment.newInstance(boardTime)
        supportFragmentManager.beginTransaction().replace(frameLayout_time_picker.id, timePickerFragment).commit()

        Toast.makeText(this, boardTime.toString(), Toast.LENGTH_SHORT).show()


        radio_button_hours_minutes.isChecked = boardTime.format == TIME_FORMAT_HOURS_MINUTES
        radio_button_minutes_seconds.isChecked = boardTime.format == TIME_FORMAT_MINUTES_SECONDS


        //        mainViewModel.getTimerStopwatch().observe(this, Observer {
//            pickerHours.value = Integer.parseInt(it?.substring(0, 2))
//            pickerMinutes.value = Integer.parseInt(it?.substring(2, 4))
//            pickerSeconds.value = Integer.parseInt(it?.substring(4, 6))
//        })
        button_set_time.setOnClickListener {
            boardTime.totalSeconds = timePickerFragment.totalSeconds
            boardTime.format = if (radio_button_hours_minutes.isChecked) TIME_FORMAT_HOURS_MINUTES else TIME_FORMAT_MINUTES_SECONDS
            setResult(Activity.RESULT_OK, Intent().apply { putExtra(BUNDLE_KEY_BOARD_TIME, boardTime) })
            finish()
        }
    }

}

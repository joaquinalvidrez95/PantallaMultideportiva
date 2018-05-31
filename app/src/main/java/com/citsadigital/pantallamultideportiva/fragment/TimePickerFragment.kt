package com.citsadigital.pantallamultideportiva.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker

import com.citsadigital.pantallamultideportiva.R
import com.citsadigital.pantallamultideportiva.model.BoardTime
import com.citsadigital.pantallamultideportiva.util.BUNDLE_KEY_BOARD_TIME
import kotlinx.android.synthetic.main.fragment_time_picker.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class TimePickerFragment : Fragment() {

    val totalSeconds: Int
        get() = number_picker_hours.value * 3600 +
                number_picker_minutes.value * 60 +
                number_picker_seconds.value


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =// Inflate the layout for this fragment
            inflater.inflate(R.layout.fragment_time_picker, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNumberPicker(arrayOf(number_picker_minutes, number_picker_seconds), 59, 0)
        setupNumberPicker(arrayOf(number_picker_hours), 99, 0)

        val mBoardTime = arguments?.getParcelable<BoardTime>(BUNDLE_KEY_BOARD_TIME)

        mBoardTime?.let {
            number_picker_hours.value = it.hours
            number_picker_minutes.value = it.minutes
            number_picker_seconds.value = it.seconds
        }

    }


    private fun setupNumberPicker(pickers: Array<NumberPicker>, max: Int, min: Int) {
        for (p in pickers) {
            p.apply {
                maxValue = max
                minValue = min
            }
        }
    }

    companion object {

        fun newInstance(boardTime: BoardTime): TimePickerFragment {
            return TimePickerFragment().apply {
                arguments = Bundle().apply { putParcelable(BUNDLE_KEY_BOARD_TIME, boardTime) }
            }
        }
    }


}

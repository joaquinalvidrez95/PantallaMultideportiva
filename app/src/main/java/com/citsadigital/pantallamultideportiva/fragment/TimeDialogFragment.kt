package com.citsadigital.pantallamultideportiva.fragment


import android.os.Bundle
import android.support.v7.app.AppCompatDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.citsadigital.pantallamultideportiva.R


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class TimeDialogFragment : AppCompatDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_dialog_time, container, false)
    }


}

package com.citsadigital.pantallamultideportiva.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.citsadigital.pantallamultideportiva.R
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {
    private var count = 120

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_decrease_home.setOnClickListener {
            if (count > 0) count--
            textViewHomeScore.text = count.toString()
        }
        button_increase_home.setOnClickListener {
            count++
            textViewHomeScore.text = count.toString()
        }




    }
}

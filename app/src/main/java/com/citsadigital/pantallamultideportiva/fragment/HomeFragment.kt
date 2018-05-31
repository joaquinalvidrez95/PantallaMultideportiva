package com.citsadigital.pantallamultideportiva.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.citsadigital.pantallamultideportiva.R
import com.citsadigital.pantallamultideportiva.activity.MainActivity
import com.citsadigital.pantallamultideportiva.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), View.OnClickListener {
    private lateinit var mainViewModel: MainViewModel

    override fun onClick(v: View?) {
        if (mainViewModel.isConnected().value == true) {
            when (view?.id) {
                button_increase_home_score.id -> mainViewModel.increaseHomeScore()
                button_increase_home_score.id -> mainViewModel.increaseHomeScore()
                button_decrease_home_score.id -> mainViewModel.decreaseHomeScore()
                button_increase_home_violations.id -> mainViewModel.increaseHomeViolations()
                button_decrease_home_violations.id -> mainViewModel.decreaseHomeViolations()
                button_increase_guest_score.id -> mainViewModel.increaseGuestScore()
                button_decrease_guest_score.id -> mainViewModel.decreaseGuestScore()
                button_increase_guest_violations.id -> mainViewModel.increaseGuestViolations()
                button_decrease_guest_violations.id -> mainViewModel.decreaseGuestViolations()
                button_increase_sets.id -> mainViewModel.increaseSets()
                button_decrease_sets.id -> mainViewModel.decreaseSets()
            }
        } else {
            (activity as MainActivity).showMessage(getString(R.string.message_disconnected))
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = ViewModelProviders.of(activity!!)[MainViewModel::class.java]

//        button_increase_home_score.setOnClickListener(this)
//        button_decrease_home_score.setOnClickListener(this)
//        button_increase_home_violations.setOnClickListener(this)
//        button_decrease_home_violations.setOnClickListener(this)
//        button_increase_guest_score.setOnClickListener(this)
//        button_decrease_guest_score.setOnClickListener(this)
//        button_increase_guest_violations.setOnClickListener(this)
//        button_decrease_guest_violations.setOnClickListener(this)
//        button_increase_sets.setOnClickListener(this)
//        button_decrease_sets.setOnClickListener(this)

        button_increase_home_score.setOnClickListener { mainViewModel.increaseHomeScore() }
        button_decrease_home_score.setOnClickListener { mainViewModel.decreaseHomeScore() }
        button_increase_home_violations.setOnClickListener { mainViewModel.increaseHomeViolations() }
        button_decrease_home_violations.setOnClickListener { mainViewModel.decreaseHomeViolations() }
        button_increase_guest_score.setOnClickListener { mainViewModel.increaseGuestScore() }
        button_decrease_guest_score.setOnClickListener { mainViewModel.decreaseGuestScore() }
        button_increase_guest_violations.setOnClickListener { mainViewModel.increaseGuestViolations() }
        button_decrease_guest_violations.setOnClickListener { mainViewModel.decreaseGuestViolations() }
        button_increase_sets.setOnClickListener { mainViewModel.increaseSets() }
        button_decrease_sets.setOnClickListener { mainViewModel.decreaseSets() }

        edit_text_home_name.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                mainViewModel.sendHomeName(v.text.toString())
                true
            }
            false
        }

        edit_text_guest_name.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                mainViewModel.sendGuestName(v.text.toString())
                true
            }
            false
        }


        mainViewModel.getHomeScore().observe(this, Observer {
            text_view_home_score.text = it?.toString()
        })
        mainViewModel.getHomeViolations().observe(this, Observer {
            text_view_home_violations.text = it?.toString()
        })
        mainViewModel.getGuestScore().observe(this, Observer {
            text_view_guest_score.text = it?.toString()
        })
        mainViewModel.getGuestViolations().observe(this, Observer {
            text_view_guest_violations.text = it?.toString()
        })
        mainViewModel.getNumberOfSet().observe(this, Observer {
            text_view_number_of_set.text = it.toString()
        })

        mainViewModel.getTime().observe(this, Observer {
            text_view_time.text = it?.time
        })


    }
}

package com.citsadigital.pantallamultideportiva.activity

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import com.citsadigital.pantallamultideportiva.R
import com.citsadigital.pantallamultideportiva.fragment.HomeFragment
import com.citsadigital.pantallamultideportiva.fragment.LoginDialogFragment
import com.citsadigital.pantallamultideportiva.model.BoardTime
import com.citsadigital.pantallamultideportiva.util.*
import com.citsadigital.pantallamultideportiva.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG_HOME_FRAGMENT = "TAG_HOME"
private const val TAG_SETTINGS_FRAGMENT = "TAG_SETTINGS"
const val REQUEST_DEVICE = 13
const val REQUEST_BOARD_TIME = 12
const val REQUEST_ENABLE_BT = 10

class MainActivity : AppCompatActivity() {

    private var mainViewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        mainViewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
        mainViewModel?.isConnected()?.observe(
                this,
                Observer { t ->
                    supportActionBar?.title =
                            if (t == true)
                                getString(R.string.toolbar_title_connected)
                            else getString(R.string.toolbar_title_disconnected)
                })
        mainViewModel?.getConnectionState()?.observe(
                this,
                Observer { bundle ->
                    val message = bundle?.getString(BUNDLE_KEY_MESSAGE) ?: ""
                    when (bundle?.getInt(BUNDLE_KEY_DEVICE_STATE)) {
                        BluetoothConstants.DeviceState.CONNECTED ->
                            LoginDialogFragment.newInstance(
                                    PreferenceManager
                                            .getDefaultSharedPreferences(this)
                                            .getString(
                                                    getString(R.string.pref_key_password),
                                                    getString(R.string.pref_default_password)
                                            )
                            ).show(supportFragmentManager, "")
                        BluetoothConstants.DeviceState.DISPLAY_CONNECTED,
                        BluetoothConstants.DeviceState.DISCONNECTED,
                        BluetoothConstants.DeviceState.CONNECTION_FAILED -> showMessage(message)

                    }

                })
        mainViewModel?.getMessage()?.observe(this, Observer { t -> showMessage(t ?: "") })



        setFragment(HomeFragment(), TAG_HOME_FRAGMENT)

//        bottom_navigation_view.setOnNavigationItemSelectedListener {
//            when (it.itemId) {
//                R.id.bottom_navigation_home -> {
//                    Log.d(javaClass.name, "home")
//                    var homeFragment = supportFragmentManager.findFragmentByTag(TAG_HOME_FRAGMENT)
//                    if (homeFragment == null){
//                        Log.d("New", "Nuevo home fragment")
//                        homeFragment = HomeFragment()
//                    }
//                    setFragment(homeFragment, TAG_HOME_FRAGMENT)
//                    return@setOnNavigationItemSelectedListener true
//                }
//                R.id.bottom_navigation_settings -> {
//                    Log.d(javaClass.name, "settings")
//                    var settingsFragment = supportFragmentManager.findFragmentByTag(TAG_SETTINGS_FRAGMENT)
//                    if (settingsFragment == null) settingsFragment = SettingsFragment()
//                    setFragment(settingsFragment, TAG_SETTINGS_FRAGMENT)
//                    return@setOnNavigationItemSelectedListener true
//                }
//                else -> {
//                    Log.d(javaClass.name, "None")
//                    return@setOnNavigationItemSelectedListener false
//                }
//            }
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) return
        when (requestCode) {
            REQUEST_DEVICE -> {
                val device = data?.extras?.getParcelable<BluetoothDevice>(BUNDLE_KEY_DEVICE)
                device?.let { mainViewModel?.onDeviceSelected(device) }
            }
            REQUEST_BOARD_TIME -> {
                val boardTime = data?.extras?.getParcelable<BoardTime>(BUNDLE_KEY_BOARD_TIME)
                showMessage(boardTime.toString())
                mainViewModel?.setBoardTime(boardTime)
            }
        }


    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_menu_about -> startActivity(Intent(this, AboutActivity::class.java))
            R.id.action_menu_bluetooth -> {
                if (BluetoothAdapter.getDefaultAdapter().isEnabled) {
                    startActivityForResult(Intent(this, BluetoothActivity::class.java), REQUEST_DEVICE)
                } else {
                    startActivityForResult(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_ENABLE_BT)
                }
            }
            R.id.action_menu_play -> {
                if (mainViewModel?.isConnected()?.value == true) {
                    mainViewModel?.playBoard()
                } else {
                    showMessage(getString(R.string.message_disconnected))
                }
            }
            R.id.action_menu_pause -> {
                if (mainViewModel?.isConnected()?.value == true) {
                    mainViewModel?.pauseBoard()
                } else {
                    showMessage(getString(R.string.message_disconnected))
                }
            }
            R.id.action_menu_reset_board -> {
                if (mainViewModel?.isConnected()?.value == true) {
                    mainViewModel?.resetBoard()
                } else {
                    showMessage(getString(R.string.message_disconnected))
                }
            }
            R.id.action_menu_time -> {
//                TimeDialogFragment().show(supportFragmentManager, "")
                val boardTime = mainViewModel?.getTime()?.value
                boardTime?.let {
                    startActivityForResult(Intent(
                            this,
                            TimeActivity::class.java).apply {
                        putExtra(BUNDLE_KEY_BOARD_TIME, boardTime)
//                        putExtra(BUNDLE_KEY_BOARD_TIME_FORMAT, boardTime.format)
//                        putExtra(BUNDLE_KEY_BOARD_TIME_COUNTING_MODE, boardTime.countMode)
                    },
                            REQUEST_BOARD_TIME)
                }

            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction().replace(frameLayout.id, fragment, tag).commit()
    }

    fun showMessage(text: String) =
            Snackbar.make(frameLayout, text, Snackbar.LENGTH_LONG).show()
}

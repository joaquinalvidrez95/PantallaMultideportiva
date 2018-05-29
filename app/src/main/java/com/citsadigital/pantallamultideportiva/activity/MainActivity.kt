package com.citsadigital.pantallamultideportiva.activity

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import com.citsadigital.pantallamultideportiva.R
import com.citsadigital.pantallamultideportiva.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG_HOME_FRAGMENT = "TAG_HOME"
private const val TAG_SETTINGS_FRAGMENT = "TAG_SETTINGS"
const val REQUEST_DEVICE = 13
const val REQUEST_ENABLE_BT = 10

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setTitle(R.string.toolbar_title_disconnected)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

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
}

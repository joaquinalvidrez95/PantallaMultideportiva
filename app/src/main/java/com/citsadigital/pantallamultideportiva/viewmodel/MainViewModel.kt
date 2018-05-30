package com.citsadigital.pantallamultideportiva.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.bluetooth.BluetoothDevice
import android.os.Bundle
import com.citsadigital.pantallamultideportiva.R
import com.citsadigital.pantallamultideportiva.model.BoardTime
import com.citsadigital.pantallamultideportiva.model.Team
import com.citsadigital.pantallamultideportiva.util.*


class MainViewModel(application: Application) : BaseMainViewModel(application) {

    private val homeScore = MutableLiveData<Int>()
    private val homeViolations = MutableLiveData<Int>()
    private val guestScore = MutableLiveData<Int>()
    private val guestViolations = MutableLiveData<Int>()
    private val numberOfSet = MutableLiveData<Int>()
    private val guestTeam = Team()
    private val homeTeam = Team()
    private val time = MutableLiveData<BoardTime>()

    init {
        homeScore.value = 0
        homeViolations.value = 0
        guestScore.value = 0
        guestViolations.value = 0
        numberOfSet.value = 0
        time.value = BoardTime()
    }


    override fun onDataReceived(b: Bundle) {
        val readBuf = b.getString(BUNDLE_KEY_MESSAGE)
        val device: BluetoothDevice = b.getParcelable(BUNDLE_KEY_DEVICE)

        //android.util.Log.e(javaClass.name, "Recibido: $readBuf")
        val bundle = Bundle().apply { putParcelable(BUNDLE_KEY_DEVICE, device) }

        when (readBuf[0].toString()) {
            BluetoothConstants.Contract.ON_FEEDBACK_RECEIVED -> {
                isConnectionAutomatic = false
                sharedPreferences
                        .edit()
                        .putString(app.getString(R.string.pref_key_device_address), device.address)
                        .apply()
//                sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
                fixControls(readBuf)
//                isTimeToRefreshLayout.value = true
//                sharedPreferences.registerOnSharedPreferenceChangeListener(this)

                bundle.apply {
                    putInt(BUNDLE_KEY_DEVICE_STATE, BluetoothConstants.DeviceState.DISPLAY_CONNECTED)
                    putString(BUNDLE_KEY_MESSAGE, app.getString(R.string.message_device_connected, device.name))
                }
                connectionState.value = bundle
                isConnected.value = true
            }
            BluetoothConstants.Contract.ON_PIN_WRONG -> {
                bundle.apply {
                    putInt(BUNDLE_KEY_DEVICE_STATE, BluetoothConstants.DeviceState.INVALID_PASSWORD)
                    putString(BUNDLE_KEY_MESSAGE, app.getString(R.string.message_password))
                }
                connectionState.value = bundle
                if (isConnectionAutomatic) {
                    isConnectionAutomatic = false
                    closeConnection()
                }
            }

            else -> {
                bundle.apply {
                    putInt(BUNDLE_KEY_DEVICE_STATE, BluetoothConstants.DeviceState.INVALID_DEVICE)
                    putString(BUNDLE_KEY_MESSAGE, app.getString(R.string.message_invalid_device))
                }
                connectionState.value = bundle
            }
        }

    }

    override fun fixControls(readBuf: String) {
        try {
            val editor = sharedPreferences
                    .edit()
                    .putBoolean(app.getString(R.string.pref_key_power), toBoolean(readBuf[1].toInt() - 48))
                    .putInt(app.getString(R.string.pref_key_speed), readBuf[2].toInt() - 48)
                    .putInt(app.getString(R.string.pref_key_brightness), readBuf[24].toInt() - 48)
                    .putInt(app.getString(R.string.pref_key_time_buy), Integer.parseInt(readBuf.substring(25, 27)))
                    .putInt(app.getString(R.string.pref_key_time_sell), Integer.parseInt(readBuf.substring(27, 29)))
                    .putString(app.getString(R.string.pref_key_effect), readBuf.substring(29, 30))
                    .putInt(app.getString(R.string.pref_key_effect_speed), Integer.parseInt(readBuf.substring(30, 31)))




            editor.apply()
        } catch (e: StringIndexOutOfBoundsException) {

        }

    }


//    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
//        when (key) {
//
//            app.getString(R.string.pref_key_power) ->
//                bluetoothService.write("\n${BluetoothConstants.Contract.POWER}" +
//                        "${toInteger(sharedPreferences?.getBoolean(key, true) == true)}\n")
//            app.getString(R.string.pref_key_brightness) ->
//                bluetoothService.write("\n${BluetoothConstants.Contract.BRIGHTNESS}$brightness\n")
//
//            app.getString(R.string.pref_key_speed) ->
//                bluetoothService.write("\n${BluetoothConstants.Contract.SPEED}$speed\n")
//            app.getString(R.string.pref_key_view) ->
//                bluetoothService.write("\n${BluetoothConstants.Contract.CURRENT_VIEW}$currentView\n")
//
//            app.getString(R.string.pref_key_time_buy), app.getString(R.string.pref_key_time_sell) ->
//                bluetoothService.write("\n%s%02d%02d\n".format(
//                        BluetoothConstants.Contract.TIME_EXCHANGES, timeBuy, timeSell))
//            app.getString(R.string.pref_key_effect) -> {
//                bluetoothService.write("\n${BluetoothConstants.Contract.EFFECT}$effect\n")
//            }
//            app.getString(R.string.pref_key_effect_speed) -> {
//                bluetoothService.write("\n${BluetoothConstants.Contract.EFFECT_SPEED}$effectSpeed\n")
//            }
//        }
//    }


    fun playBoard() {

    }

    fun resetBoard() {

    }

    fun pauseBoard() {

    }

    fun increaseHomeScore() {
        increaseTeamScore(homeScore, homeTeam)
        bluetoothService.write("%s%03d\n".format(BluetoothConstants.Contract.HOME_SCORE, homeTeam.score))
    }

    fun decreaseHomeScore() {
        decreaseTeamScore(homeScore, homeTeam)
        bluetoothService.write("%s%03d\n".format(BluetoothConstants.Contract.HOME_SCORE, homeTeam.score))
    }


    fun increaseGuestScore() {
        increaseTeamScore(guestScore, guestTeam)
    }

    fun decreaseGuestScore() {
        guestTeam.decreaseScore()
        guestScore.value = guestTeam.score
    }


    fun increaseHomeViolations() {
        increaseViolations(homeViolations, homeTeam)
    }

    fun decreaseHomeViolations() {
        decreaseViolations(homeViolations, homeTeam)

    }

    fun increaseGuestViolations() {
        increaseViolations(guestViolations, guestTeam)
    }

    fun decreaseGuestViolations() {
        decreaseViolations(guestViolations, guestTeam)
    }

    private fun increaseTeamScore(score: MutableLiveData<Int>, team: Team) {
        team.increaseScore()
        score.value = team.score
    }

    private fun decreaseTeamScore(score: MutableLiveData<Int>, team: Team) {
        team.decreaseScore()
        score.value = team.score
    }

    private fun increaseViolations(violations: MutableLiveData<Int>, team: Team) {
        team.increaseViolations()
        violations.value = team.numberOfViolations
    }

    private fun decreaseViolations(violations: MutableLiveData<Int>, team: Team) {
        team.decreaseViolations()
        violations.value = team.numberOfViolations
    }

    fun getHomeScore(): LiveData<Int> = homeScore
    fun getGuestScore(): LiveData<Int> = guestScore
    fun getHomeViolations(): LiveData<Int> = homeViolations
    fun getGuestViolations(): LiveData<Int> = guestViolations
    fun getNumberOfSet(): LiveData<Int> = numberOfSet
    fun getTime(): LiveData<BoardTime> = time

    fun increaseSets() {
        numberOfSet.value?.let { if (it < 9) numberOfSet.value = it + 1 }
    }

    fun decreaseSets() {
        numberOfSet.value?.let { if (it > 0) numberOfSet.value = it - 1 }
    }

    fun setBoardTime(boardTime: BoardTime?) {
        time.value = boardTime
    }


}
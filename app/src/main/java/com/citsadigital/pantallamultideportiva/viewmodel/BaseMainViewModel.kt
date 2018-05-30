package com.citsadigital.pantallamultideportiva.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.*
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.preference.PreferenceManager
import com.citsadigital.pantallamultideportiva.R
import com.citsadigital.pantallamultideportiva.util.*


/**
 * Created by Joaquín Alan Alvidrez Soto on 05/04/2018.
 */
abstract class BaseMainViewModel(application: Application) : AndroidViewModel(application),
        BluetoothService.IBluetoothServiceListener {
    protected var isConnectionAutomatic: Boolean = true

    protected val message = MutableLiveData<String>()
    protected val app: Application = getApplication()
    protected val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(app)

    protected val isConnected = MutableLiveData<Boolean>().apply { value = false }
//    protected val isTimeToRefreshLayout = MutableLiveData<Boolean>()

    protected val bluetoothService: BluetoothService
    protected val connectionState = MutableLiveData<Bundle>()
    private var lastDevice: BluetoothDevice? = null

    private val deviceReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val device = intent?.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)

            when (intent?.action) {
                BluetoothDevice.ACTION_ACL_DISCONNECTED -> {
                    if (isConnectionAutomatic || device?.address != lastDevice?.address) return
//                    sharedPreferences
//                            .unregisterOnSharedPreferenceChangeListener(this@BaseMainViewModel)
                    isConnected.value = false
                    connectionState.value = Bundle().apply {
                        putParcelable(BUNDLE_KEY_DEVICE, device)
                        putInt(BUNDLE_KEY_DEVICE_STATE, BluetoothConstants.DeviceState.DISCONNECTED)
                        putString(BUNDLE_KEY_MESSAGE,
                                app.getString(R.string.message_connection_lost, device?.name))
                    }
                }
            }

        }
    }


    init {
        BluetoothAdapter.getDefaultAdapter().enable()
        app.registerReceiver(
                deviceReceiver,
                IntentFilter().apply {
                    addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
                    addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
                })

        val handler = Handler(
                Handler.Callback { msg ->
                    onDataReceived(msg.obj as Bundle)
                    true
                }
        )

        bluetoothService = BluetoothService(handler, this)
        val defaultAddress = app.getString(R.string.pref_default_device_address)
        val deviceAddress = sharedPreferences.getString(app.getString(R.string.pref_key_device_address), defaultAddress)
        if (deviceAddress != defaultAddress && BluetoothAdapter.getDefaultAdapter().isEnabled) {
            onDeviceSelected(BluetoothAdapter.getDefaultAdapter().getRemoteDevice(deviceAddress))
        } else {
            isConnectionAutomatic = false
        }
    }

    abstract fun onDataReceived(bundle: Bundle)

    fun isConnected(): LiveData<Boolean> = isConnected

    fun getMessage(): LiveData<String> = message

    protected abstract fun fixControls(readBuf: String)

    override fun onCleared() {
        super.onCleared()
//        PreferenceManager
//                .getDefaultSharedPreferences(getApplication())
//                .unregisterOnSharedPreferenceChangeListener(this)

        app.unregisterReceiver(deviceReceiver)
        bluetoothService.stop()
    }

    fun getConnectionState(): LiveData<Bundle> = connectionState


    fun onDeviceSelected(device: BluetoothDevice) =
            bluetoothService.startClient(device, BluetoothConstants.uuid, app)

    override fun onConnectionFailed(device: BluetoothDevice?) {
        if (isConnectionAutomatic) {
            isConnectionAutomatic = false
            return
        }

        connectionState.postValue(Bundle().apply {
            putInt(BUNDLE_KEY_DEVICE_STATE, BluetoothConstants.DeviceState.CONNECTION_FAILED)
            putString(BUNDLE_KEY_MESSAGE,
                    app.getString(R.string.message_connection_failed, device?.name))
            putParcelable(BUNDLE_KEY_DEVICE, device)
        })
    }

    override fun onConnectionStarted(device: BluetoothDevice) {
        lastDevice = device
        if (isConnectionAutomatic) {
            Handler(Looper.getMainLooper()).postDelayed({
                sendPassword(sharedPreferences.getString(app.getString(R.string.pref_key_password),
                        app.getString(R.string.pref_default_password)))
            }, 100)

        } else {
            connectionState.postValue(Bundle().apply {
                putInt(BUNDLE_KEY_DEVICE_STATE, BluetoothConstants.DeviceState.CONNECTED)
                putParcelable(BUNDLE_KEY_DEVICE, device)
            })
        }

    }

    fun sendPassword(passwordText: String) =
            bluetoothService.write("\n${BluetoothConstants.Contract.REQUEST}$passwordText\n")

    fun closeConnection() {
//        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        bluetoothService.stop()
    }


//    fun isTimeToRefreshLayout(): LiveData<Boolean> = isTimeToRefreshLayout

}
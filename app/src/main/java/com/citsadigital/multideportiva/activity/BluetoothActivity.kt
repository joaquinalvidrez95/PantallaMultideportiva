package com.citsadigital.multideportiva.activity

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.citsadigital.multideportiva.R
import com.citsadigital.multideportiva.adapter.DeviceAdapter
import com.citsadigital.multideportiva.util.BUNDLE_KEY_DEVICE
import kotlinx.android.synthetic.main.activity_bluetooth.*


class BluetoothActivity : AppCompatActivity(), DeviceAdapter.OnDeviceClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth)

        val devices = BluetoothAdapter.getDefaultAdapter().bondedDevices
        textview_message_bonded_devices.visibility = if (devices.isEmpty()) View.VISIBLE else View.GONE


        devicesList.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = DeviceAdapter(ArrayList(devices), this@BluetoothActivity)
        }

    }

    override fun onDeviceClick(device: BluetoothDevice) {

        if (BluetoothAdapter.getDefaultAdapter().isEnabled) {
            setResult(
                    android.app.Activity.RESULT_OK,
                    Intent().apply { putExtra(BUNDLE_KEY_DEVICE, device) })
            finish()
        } else {
            startActivityForResult(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_ENABLE_BT)
        }
    }
}

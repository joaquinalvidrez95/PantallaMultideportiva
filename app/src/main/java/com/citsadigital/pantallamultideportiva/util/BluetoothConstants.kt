package com.citsadigital.pantallamultideportiva.util

import java.util.*


object BluetoothConstants {
    val uuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    object Contract {
        const val HOME_SCORE = "e"
        const val GUEST_SCORE = "e"
        const val HOME_VIOLATIONS = "e"
        const val GUEST_VIOLATIONS = "e"
        const val SET = "e"
        const val HOME_NAME = "e"
        const val GUEST_NAME = "e"

        const val POWER = "p"

        const val REQUEST = "r"


        const val ON_PIN_WRONG = "J"
        const val ON_FEEDBACK_RECEIVED = "w"
    }

    object DeviceState {
        const val DISCONNECTED = 0
        const val CONNECTED = 1
        const val DISPLAY_CONNECTED = 2
        const val CONNECTION_FAILED = 3
        const val INVALID_PASSWORD = 4
        const val INVALID_DEVICE = 5
    }


}
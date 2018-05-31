package com.citsadigital.pantallamultideportiva.util

import java.util.*


object BluetoothConstants {
    val uuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    object Contract {
        const val HOME_SCORE = "a"
        const val GUEST_SCORE = "b"
        const val HOME_VIOLATIONS = "c"
        const val GUEST_VIOLATIONS = "d"
        const val SET = "e"
        const val HOME_NAME = "f"
        const val GUEST_NAME = "g"
        const val TIME = "h"
        const val TIME_FORMAT = "i"
        const val COUNTING_MODE = "j"
        const val START_STOP = "k"
        const val RESET_TIME = "l"
        const val RESET_BOARD = "m"
        const val POWER = "p"

        const val REQUEST = "r"

        const val ON_PIN_WRONG = "J"
        const val ON_FEEDBACK_RECEIVED = "z"
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
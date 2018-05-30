package com.citsadigital.pantallamultideportiva.util

/**
 * Created by Joaquín Alan Alvidrez Soto on 24/04/2018.
 */


fun toInteger(boolean: Boolean) = if (boolean) 1 else 0
fun toBoolean(int: Int) = int == 1


const val BUNDLE_KEY_DEVICE: String = "key_device"
const val BUNDLE_KEY_DEVICE_STATE: String = "key_device_state"
const val BUNDLE_KEY_MESSAGE: String = "key_message"
const val BUNDLE_KEY_BOARD_TIME: String = "key_board_time"
const val BUNDLE_KEY_BOARD_TIME_SECONDS: String = "key_board_time_seconds"
const val BUNDLE_KEY_BOARD_TIME_FORMAT: String = "key_board_time_format"
const val BUNDLE_KEY_BOARD_TIME_COUNTING_MODE: String = "key_board_time_counting_mode"
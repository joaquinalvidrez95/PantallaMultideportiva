package com.citsadigital.multideportiva.util

/**
 * Created by Joaquín Alan Alvidrez Soto on 24/04/2018.
 */


fun toInteger(boolean: Boolean) = if (boolean) 1 else 0

fun toBoolean(int: Int) = int == 1


const val BUNDLE_KEY_DEVICE = "key_device"
const val BUNDLE_KEY_DEVICE_STATE = "key_device_state"
const val BUNDLE_KEY_MESSAGE = "key_message"
const val BUNDLE_KEY_BOARD_TIME = "key_board_time"

const val COMMAND_START = 1
const val COMMAND_PAUSE = 0

const val COMMAND_POWER_ON = 1
const val COMMAND_POWER_OFF = 0


//const val BUNDLE_KEY_BOARD_TIME_SECONDS: String = "key_board_time_seconds"
//const val BUNDLE_KEY_BOARD_TIME_FORMAT: String = "key_board_time_format"
//const val BUNDLE_KEY_BOARD_TIME_COUNTING_MODE: String = "key_board_time_counting_mode"
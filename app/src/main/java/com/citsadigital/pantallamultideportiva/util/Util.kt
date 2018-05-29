package com.citsadigital.pantallamultideportiva.util

import android.widget.TextView

/**
 * Created by Joaquín Alan Alvidrez Soto on 24/04/2018.
 */
fun updateCounter(textViewCounter: TextView, maxLength: Int, numberOfCharacters: Int) {
    textViewCounter.text = "${maxLength - numberOfCharacters}/$maxLength"
}

fun toInteger(boolean: Boolean) = if (boolean) 1 else 0
fun toBoolean(int: Int) = int == 1


const val BUNDLE_KEY_DEVICE: String = "key_device"
const val BUNDLE_KEY_DEVICE_STATE: String = "key_device_state"
const val BUNDLE_KEY_MESSAGE: String = "key_message"
package com.citsadigital.pantallamultideportiva.model

/**
 * Created by Joaquín Alan Alvidrez Soto on 30/05/2018.
 */
data class BoardTime(var timeInSeconds: Int = 0, var format: TimeFormat, var countMode: CountMode) {
    val time: String
        get() {
            return when (format) {
                TimeFormat.HOURS_MINUTES -> {
                    "%d:d".format(timeInSeconds / 3600, timeInSeconds / 60 % 60)
                }
                TimeFormat.MINUTES_SECONDS -> {
                    "%d:d".format(timeInSeconds / 60, timeInSeconds % 60)
                }
            }
        }
}
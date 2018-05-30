package com.citsadigital.pantallamultideportiva.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Joaquín Alan Alvidrez Soto on 30/05/2018.
 */

const val TIME_FORMAT_HOURS_MINUTES = 0
const val TIME_FORMAT_MINUTES_SECONDS = 1
const val COUNTING_MODE_ASCENDING = 0
const val COUNTING_MODE_DESCENDING = 1

fun secondsToHours(totalSeconds: Int) = totalSeconds / 3600
fun secondsToMinutes(totalSeconds: Int) = totalSeconds / 60 % 60
fun totalSecondsToSeconds(totalSeconds: Int) = totalSeconds % 60

data class BoardTime(var timeInSeconds: Int = 5632, var format: Int = 0, var countMode: Int = 0) : Parcelable {
    val time: String
        get() {
            return when (format) {
                TIME_FORMAT_HOURS_MINUTES -> {
                    "%02d:%02d".format(secondsToHours(timeInSeconds), secondsToMinutes(timeInSeconds))
                }
                TIME_FORMAT_MINUTES_SECONDS -> {
                    "%02d:%02d".format(timeInSeconds / 60, totalSecondsToSeconds(timeInSeconds))
                }
                else -> {
                    ""
                }
            }
        }

    val hours: Int get() = secondsToHours(timeInSeconds)
    val minutes: Int get() = timeInSeconds / 60 % 60
    val seconds: Int get() = timeInSeconds % 60

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(timeInSeconds)
        parcel.writeInt(format)
        parcel.writeInt(countMode)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<BoardTime> {
        override fun createFromParcel(parcel: Parcel): BoardTime = BoardTime(parcel)

        override fun newArray(size: Int): Array<BoardTime?> = arrayOfNulls(size)
    }


}
package com.citsadigital.multideportiva.model

/**
 * Created by Joaquín Alan Alvidrez Soto on 30/05/2018.
 */
data class Team(var score: Int = 0, var numberOfViolations: Int = 0, var name: String = "") {

//    private val score = MutableLiveData<Int>()
//    private val numberOfViolations = MutableLiveData<Int>()

    fun increaseScore() {
        if (score < 999) score++
    }

    fun decreaseScore() {
        if (score > 0) score--
    }

    fun increaseViolations() {
        if (numberOfViolations < 9) numberOfViolations++
    }

    fun decreaseViolations() {
        if (numberOfViolations > 0) numberOfViolations--
    }
}
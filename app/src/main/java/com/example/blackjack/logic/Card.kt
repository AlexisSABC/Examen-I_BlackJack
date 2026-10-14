package com.example.blackjack.logic

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class Card(val figure: String, val number: Int) {
    var isVisible by mutableStateOf(true)

    var textCard by mutableStateOf(
        (when (number) {
            11 -> "J"
            12 -> "Q"
            13 -> "K"
            else -> number.toString()
        }) + figure
    )
}
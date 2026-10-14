package com.example.blackjack

data class GameData(
    val winner: String,
    val playerName: String,
    val targetPoints: Int,
    val playerPoints: Int,
    val crupierPoints: Int,
    val gameDate: String,
    val playerHand: String,
    val crupierHand: String)
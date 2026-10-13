package com.example.blackjack.logic

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class Player(val playerName: String, val targetPoints: Int) {
    // Atributos
    val cards = mutableStateListOf<Card>() // Set de cartas
    var playerPoints by mutableIntStateOf(0)

    // Determinar puntos del jugador
    private fun determinatePoints(){
        playerPoints = 0
        val cardsCopy = mutableStateListOf<Card>().apply {
            addAll(cards)
        }

        for(card in cardsCopy){
            if(card.isVisible){
                when(card.number){
                    11, 12, 13 -> playerPoints += 10
                    1 -> if((11 + playerPoints) <= targetPoints) playerPoints += 11 else playerPoints += 1
                    else -> playerPoints += card.number
                }
            }
        }
    }

    // Agregar cartas iniciales al jugador
    fun addCard(card: Card){
        cards.add(card)
        determinatePoints()
    }

    // Obtener cartas: HIT
    fun hitCard(card: Card){
        cards.add(card)
        determinatePoints()
    }

    // Determinar si jugador se paso de los n puntos
    fun stillInGame(targetPoints: Int): Int{
        return if(playerPoints > targetPoints) 1 else if(playerPoints == targetPoints) 2 else 3
    }

    // Revelar todas las cartas
    fun revealAllCards(){
        for(card in cards){
            card.isVisible = true
        }
        determinatePoints()
    }

    // Obtener cartas como CSV
    fun getAllCards(): String {
        return cards.joinToString(",") { it.textCard }
    }
}
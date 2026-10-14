package com.example.blackjack.logic

import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlin.math.roundToInt

public class BlackJackLogic{
    // Jugadores como atributos
    var player: Player
    var crupier: Player
    val deck = ArrayList<Card>()
    var targetPoints: Int = 21

    // Inicializar juego
    constructor(playerName: String, points: Int){
        // Crear personajes
        targetPoints = points
        player = Player(playerName, targetPoints)
        crupier = Player("Cupier", targetPoints)

        // Crear mazo de cartas
        for(i in 1..13){
            deck.add(Card(number = i, figure = "♠"))
            deck.add(Card(number = i, figure = "♣"))
            deck.add(Card(number = i, figure = "♥"))
            deck.add(Card(number = i, figure = "♦"))
        }
        deck.shuffle()

        // Agregar una carta al jugador
        player.addCard(deck.removeAt(deck.lastIndex))

        // Agregar una carta al Crupier
        crupier.addCard(deck.removeAt(deck.lastIndex))

        // Agregar ultima carta al jugador
        player.addCard(deck.removeAt(deck.lastIndex))

        // Agregar ultima carta al Crupier
        val lastCrupCard = deck.removeAt(deck.lastIndex)
        lastCrupCard.isVisible = false
        crupier.addCard(lastCrupCard)
    }

    // Realizar HIT para cualquier jugador
    fun hitCards(playerIndex: Int){
        if(playerIndex == 1){ // Jugador
            player.hitCard(deck.removeAt(deck.lastIndex))
        }else{ // Crupier
            while(crupier.playerPoints < ((targetPoints * 17.0 / 21.0).roundToInt())){
                crupier.hitCard(deck.removeAt(deck.lastIndex))
            }
        }
    }

    // Revelar carta del crupier
    fun revealCrupierCard(){
        crupier.revealAllCards()
        hitCards(0)
    }

    // Determinar si el Crupier ya llego hasta los 17 puntos o proporcional
    fun crupierHasLimit(): Boolean{
        return if(crupier.playerPoints >= ((targetPoints * 17.0 / 21.0).roundToInt())) true else false
    }

    // Determinar estado de juego
    fun gameState(): Int{
        if(player.playerPoints > targetPoints){ // Crupier gana si jugador pasa los puntos
            return 2
        }

        if(crupier.playerPoints > targetPoints){ // Jugador gana si el Crupier supera los puntos
            return 1
        }

        if(player.playerPoints == targetPoints){ // Jugador gana ya que obtuvo exactamente los puntos
            return 1
        }

        if(crupierHasLimit()){
            if(player.playerPoints > crupier.playerPoints){ // Jugador gana si tiene mas puntos que el crupier
                return 1
            }

            if(crupier.playerPoints > player.playerPoints){ // Crupier  gana si tiene mas puntos que el crupier
                return 2
            }

            if(crupier.playerPoints == targetPoints){ // Crupier gana por obtener exactamente los puntos
                return 2
            }

            if(crupier.playerPoints == player.playerPoints){ // Empate
                return 4

            }
        }

        // Juego sigue
        return 3
    }

    // Obtener puntos del jugador
    fun getPlayerPoints(): Int{
        return player.playerPoints
    }

    // Obtener puntos del Crupier
    fun getCrupierPoints(): Int{
        return crupier.playerPoints
    }

    // Obtener cartas del jugador
    fun getPlayerCards(): SnapshotStateList<Card> {
        return player.cards
    }

    // Obtener cartas del Crupier
    fun getCrupierCards(): SnapshotStateList<Card>{
        return crupier.cards
    }

    // Devolver cartas finales del jugador
    fun getFinalPlayerCards(): String{
        return player.getAllCards()
    }

    // Devolver cartas finales del Crupier
    fun getFinalCrupierCards(): String{
        return crupier.getAllCards()
    }
}
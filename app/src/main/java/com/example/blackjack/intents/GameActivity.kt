package com.example.blackjack.intents

import android.app.Activity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blackjack.logic.BlackJackLogic
import com.example.blackjack.models.TextModel
import com.example.blackjack.ui.theme.ExamenIBlackJackTheme
import com.example.blackjack.ui.theme.ThemePreferences
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.platform.LocalContext
import com.example.blackjack.TinyDB
import com.example.blackjack.logic.Card
import com.example.blackjack.models.ButtonModel
import com.example.blackjack.models.CardModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.collections.chunked
import kotlin.collections.forEach

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val themePreferences = ThemePreferences(this)

        // Recuperar nombre de jugador y los puntos seleccionados
        val playerName = intent.getStringExtra("playerName") ?: "Player"
        val targetPoints = intent.getIntExtra("targetPoints", 21)

        setContent{
            var selectedTheme by rememberSaveable { mutableStateOf(themePreferences.getTheme()) }

            ExamenIBlackJackTheme(selectedTheme = selectedTheme){
                val tinyDB = remember { TinyDB(applicationContext) }

                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background){
                    buildGameWindow(
                        modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center),
                        playerName = playerName,
                        targetPoints = targetPoints,
                        tinyDB = tinyDB)
                }
            }
        }
    }
}

@Composable
fun buildGameWindow(modifier: Modifier = Modifier, playerName: String, targetPoints: Int, tinyDB: TinyDB){
    // Objeto logico
    val logic = remember { BlackJackLogic(playerName = playerName, points = targetPoints) }
    var playerStillInGame by remember { mutableStateOf(true) }
    var gameStatus by remember { mutableStateOf("En juego") }
    var gameFinished by remember{ mutableStateOf(false) }

    val context = LocalContext.current

    // GUI
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Titulo de activity
                Spacer(Modifier.height(30.dp))
                TextModel(text = "Examen I - BlackJack\nJuego", fontSize = 22)

                // Puntos objetivos
                Spacer(Modifier.height(20.dp))
                TextModel(text = "Objetivo: " + targetPoints + " puntos", fontSize = 20)

                // Estado de juego
                Spacer(Modifier.height(20.dp))
                TextModel(text = gameStatus, fontSize = 20)

                // Mostrar nombre y puntos de Crupier
                Spacer(Modifier.height(20.dp))
                TextModel(text = "- - - - Crupier / Puntos = " + logic.getCrupierPoints() + " - - - -", fontSize = 20)

                // Mostrar cartas del crupier
                Spacer(Modifier.height(8.dp))
                cardRows(logic.getCrupierCards())

                // Mostrar nombre y puntos de jugador
                Spacer(Modifier.height(30.dp))
                TextModel(text = "- - - - " + playerName + " / Puntos = " + logic.getPlayerPoints() + " - - - -", fontSize = 20)

                // Mostrar cartas del jugador
                Spacer(Modifier.height(8.dp))
                cardRows(logic.getPlayerCards())

                // Texto de eleccion
                Spacer(Modifier.height(30.dp))
                TextModel(text = "- - - - Elige, " + playerName + " - - - -", fontSize = 20)

                Spacer(Modifier.height(8.dp))
                ButtonModel(
                    text = "HIT",
                    enabled = playerStillInGame,
                    onClick = {
                        logic.hitCards(1)

                        when(logic.gameState()){
                            2 -> { gameStatus = "Crupier gana"; playerStillInGame = false; gameFinished = true }
                            1 -> { gameStatus = playerName + " gana"; playerStillInGame = false; gameFinished = true }
                            else -> { gameStatus = "En juego" }
                        }
                    })

                Spacer(Modifier.height(8.dp))
                ButtonModel(
                    text = "STAND",
                    enabled = playerStillInGame,
                    onClick = {
                        playerStillInGame = false
                        logic.revealCrupierCard()

                        when(logic.gameState()){
                            1 -> { gameStatus = playerName + " gana" }
                            2 -> { gameStatus = "Crupier gana" }
                            4 -> { gameStatus = "Empate" }
                            else -> { gameStatus = "Error" }
                        }

                        gameFinished = true
                    })

                Spacer(Modifier.height(20.dp))
                ButtonModel(text = "Regresar al inicio", enabled = gameFinished, onClick = {
                    // Guardar nombres en TinyDB
                    when(logic.gameState()){
                        1 -> { tinyDB.putString("winner", playerName) }
                        2 -> { tinyDB.putString("winner", "Crupier") }
                        else -> { tinyDB.putString("winner", "Empate") }
                    }

                    // Guardar datos generales en TinyDB
                    tinyDB.putString("playerName", playerName)
                    tinyDB.putInt("targetPoints", targetPoints)

                    // Guardar puntos en TinyDB
                    tinyDB.putInt("playerPoints", logic.getPlayerPoints())
                    tinyDB.putInt("crupierPoints", logic.getCrupierPoints())

                    // Guardar fecha de juego
                    tinyDB.putString("gameDate", getDate())

                    // Guardar manos de jugadores
                    tinyDB.putString("playerHand", logic.getFinalPlayerCards())
                    tinyDB.putString("crupierHand", logic.getFinalCrupierCards())

                    // Terminar activity
                    (context as? Activity)?.finish()
                })

                // Controlar caso de juego
                if(logic.getPlayerPoints() == 21){
                    playerStillInGame = false
                    logic.revealCrupierCard()
                    when(logic.gameState()){
                        1 -> { gameStatus = playerName + " gana" }
                        2, 6 -> { gameStatus = "Crupier gana" }
                        else -> { gameStatus = "Empate" }
                    }
                    gameFinished = true
                }
            }
        }
    }
}

// Obtener fecha y hora actual
fun getDate(): String {
    val format = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
    return format.format(Date())
}

// Filas de cartas
@Composable
fun cardRows(cardSnap: SnapshotStateList<Card>){
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)){
        cardSnap.chunked(3).forEach { cardsRow ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)){
                cardsRow.forEach { card ->
                    if (!card.isVisible) {
                        CardModel(text = "¿ ?")
                    } else {
                        CardModel(text = card.textCard)
                    }
                }
            }
        }
    }
}
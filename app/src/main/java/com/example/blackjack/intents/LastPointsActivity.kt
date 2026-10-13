package com.example.blackjack.intents

import android.app.Activity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.blackjack.TinyDB
import com.example.blackjack.models.ButtonModel
import com.example.blackjack.models.CardModel
import com.example.blackjack.models.TextModel
import com.example.blackjack.ui.theme.ExamenIBlackJackTheme
import com.example.blackjack.ui.theme.ThemePreferences
import kotlin.collections.chunked
import kotlin.collections.forEach

class LastPointsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val themePreferences = ThemePreferences(this)

        setContent{
            var selectedTheme by rememberSaveable { mutableStateOf(themePreferences.getTheme()) }

            ExamenIBlackJackTheme(selectedTheme = selectedTheme){
                val tinyDB = remember { TinyDB(applicationContext) }
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background){
                    buildPointsWindow(Modifier.fillMaxSize().wrapContentSize(Alignment.Center), tinyDB = tinyDB)
                }
            }
        }
    }
}

@Composable
fun buildPointsWindow(modifier: Modifier = Modifier, tinyDB: TinyDB){
    val context = LocalContext.current

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
                Spacer(Modifier.height(30.dp))
                TextModel(text = "Examen I - BlackJack\nJuego anterior", fontSize = 22)

                Spacer(Modifier.height(20.dp))
                TextModel(text = "Fecha del juego anterior:\n" + tinyDB.getString("gameDate"), fontSize = 18)

                Spacer(Modifier.height(20.dp))
                TextModel(text = "Puntos objetivos:\n" + tinyDB.getInt("targetPoints"), fontSize = 18)

                Spacer(Modifier.height(30.dp))
                if(tinyDB.getString("winner").equals("Crupier")){ // GUI si Crupier gano
                    TextModel(text = "Ganador del juego anterior:\nCrupier", fontSize = 18)

                    Spacer(Modifier.height(8.dp))
                    TextModel(text = "" + tinyDB.getInt("crupierPoints") + " puntos", fontSize = 18)

                    Spacer(Modifier.height(8.dp))
                    cardRows(tinyDB.getString("crupierHand").split(","))

                    Spacer(Modifier.height(30.dp))
                    TextModel(text = "Mano del jugador " + tinyDB.getString("playerName"), fontSize = 18)

                    Spacer(Modifier.height(8.dp))
                    TextModel(text = "" + tinyDB.getInt("playerPoints") + " puntos", fontSize = 18)

                    Spacer(Modifier.height(8.dp))
                    cardRows(tinyDB.getString("playerHand").split(","))

                }else if(tinyDB.getString("winner").equals("Empate")){ // GUI si fue empate
                    TextModel(text = "Juego anterior termino en:\nEmpate", fontSize = 18)

                    Spacer(Modifier.height(8.dp))
                    TextModel(text = "Mano del jugador " + tinyDB.getString("playerName"), fontSize = 18)

                    Spacer(Modifier.height(8.dp))
                    TextModel(text = "" + tinyDB.getInt("playerPoints") + " puntos", fontSize = 18)

                    Spacer(Modifier.height(8.dp))
                    cardRows(tinyDB.getString("playerHand").split(","))

                    Spacer(Modifier.height(30.dp))
                    TextModel(text = "Mano del Crupier", fontSize = 18)

                    Spacer(Modifier.height(8.dp))
                    TextModel(text = "" + tinyDB.getInt("crupierPoints") + " puntos", fontSize = 18)

                    Spacer(Modifier.height(8.dp))
                    cardRows(tinyDB.getString("crupierHand").split(","))

                }else{ // GUI si el jugador gano
                    TextModel(text = "Ganador del juego anterior:\n" + tinyDB.getString("playerName"), fontSize = 18)

                    Spacer(Modifier.height(8.dp))
                    TextModel(text = "" + tinyDB.getInt("playerPoints") + " puntos", fontSize = 18)

                    Spacer(Modifier.height(8.dp))
                    cardRows(tinyDB.getString("playerHand").split(","))

                    Spacer(Modifier.height(30.dp))
                    TextModel(text = "Mano del Crupier", fontSize = 18)

                    Spacer(Modifier.height(8.dp))
                    TextModel(text = "" + tinyDB.getInt("crupierPoints") + " puntos", fontSize = 18)

                    Spacer(Modifier.height(8.dp))
                    cardRows(tinyDB.getString("crupierHand").split(","))
                }

                Spacer(Modifier.height(30.dp))
                ButtonModel(text = "Regresar al inicio", onClick = { (context as? Activity)?.finish() }, enabled = true)
            }
        }
    }
}

// Filas de cartas
@Composable
fun cardRows(cardList: List<String>){
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)){
        cardList.chunked(3).forEach { cardsRow ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)){
                cardsRow.forEach { card ->
                    CardModel(text = card)
                }
            }
        }
    }
}
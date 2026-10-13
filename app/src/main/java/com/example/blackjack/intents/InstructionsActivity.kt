package com.example.blackjack.intents

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.blackjack.models.ButtonModel
import com.example.blackjack.models.TextModel
import com.example.blackjack.ui.theme.AppTheme
import com.example.blackjack.ui.theme.ExamenIBlackJackTheme
import com.example.blackjack.ui.theme.ThemePreferences

class InstructionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val themePreferences = ThemePreferences(this)

        setContent{
            var selectedTheme by rememberSaveable { mutableStateOf(themePreferences.getTheme()) }

            ExamenIBlackJackTheme(selectedTheme = selectedTheme){
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background){
                    buildInstructionsWindow(Modifier.fillMaxSize().wrapContentSize(Alignment.Center))
                }
            }
        }
    }
}

@Composable
fun buildInstructionsWindow(modifier: Modifier = Modifier){
    // Lista de valores de cartas
    val cardValues = listOf(
        "As = 1 o 11 segun la mano que tengas.",
        "2 a 10: Mismo numero que muestra la carta.",
        "J, Q, K = 10 puntos cada uno."
    )

    // Lista de reglas
    val gameRules = listOf(
        "Es un juego entre dos participantes: tu y el Crupier.",
        "Tu recibes dos cartas visibles para ambos.",
        "El Crupier recibe dos cartas: una visible y otra oculta.",
        "Sumas los valores de tus cartas.",
        "Tienes dos acciones basicas: HIT o STAND.",
        "HIT: Tomas una carta adicional para acercarte a 21 sin superarlo.",
        "STAND: Terminas tu turno y mantienes tu puntuacion actual.",
        "Si superas los 21 puntos, pierdes inmediatamente.",
        "Si te plantas sin superar 21, el Crupier revela su carta oculta.",
        "El Crupier pide cartas mientras tenga 16 puntos o menos.",
        "El Crupier se planta cuando alcanza 17 puntos o mas.",
        "Si el Crupier supera 21 y tu no, ganas el encuentro.",
        "Si ninguno supera 21, gana quien tenga la puntuacion mas alta.",
        "Si ambos tienen la misma puntuacion, el encuentro termina en empate.")

    val context = LocalContext.current

    // Despliegue de textos
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
                TextModel(text = "Examen I - BlackJack\nInstrucciones", fontSize = 22)

                Column {
                    cardValues.forEach { cardVal ->
                        Spacer(Modifier.height(8.dp))
                        TextModel(text = "-) $cardVal", fontSize = 12)
                    }
                }

                Spacer(Modifier.height(20.dp))
                TextModel(text = "Reglas de juego:", fontSize = 18)

                Column {
                    gameRules.forEach { gameRule ->
                        Spacer(Modifier.height(8.dp))
                        TextModel(text = "-) $gameRule", fontSize = 12)
                    }
                }

                Spacer(Modifier.height(20.dp))
                ButtonModel(text = "Regresar al inicio", onClick = { (context as? Activity)?.finish() }, enabled = true)
            }
        }
    }
}
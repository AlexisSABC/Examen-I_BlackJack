package com.example.blackjack

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.benchmark.traceprocessor.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import com.example.blackjack.intents.GameActivity
import com.example.blackjack.intents.InstructionsActivity
import com.example.blackjack.intents.LastPointsActivity
import com.example.blackjack.ui.theme.AppTheme
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.blackjack.ui.theme.ExamenIBlackJackTheme
import com.example.blackjack.ui.theme.ThemePreferences
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import com.example.blackjack.models.ButtonModel
import com.example.blackjack.models.TextModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val themePreferences = ThemePreferences(this)

        setContent {
            var selectedTheme by rememberSaveable { mutableStateOf(themePreferences.getTheme()) }

            ExamenIBlackJackTheme(selectedTheme = selectedTheme) {
                Surface(modifier = Modifier.fillMaxSize()){
                    val tinyDB = remember { TinyDB(applicationContext) }
                    buildStartWindow(
                        modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center),
                        tinyDB = tinyDB,
                        onThemeChange = { theme ->
                            selectedTheme = theme
                            themePreferences.saveTheme(theme)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun buildStartWindow(modifier: Modifier = Modifier, onThemeChange: (AppTheme) -> Unit, tinyDB: TinyDB){
    // Controladores
    var playerName by remember { mutableStateOf("") }
    var pointsGame by remember { mutableStateOf("21") }
    val context = LocalContext.current

    // GUI
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        item{
            Column(horizontalAlignment = Alignment.CenterHorizontally){
                Spacer(Modifier.height(30.dp))
                TextModel(text = "Examen I - BlackJack\nInicio", fontSize = 22)

                Spacer(Modifier.height(20.dp))
                TextModel(text = "Configurar juego:", fontSize = 18)

                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = playerName,
                    onValueChange = { playerName = it },
                    placeholder = { Text("Ingresa tu nombre...") },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .width(250.dp)
                        .height(60.dp))

                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = pointsGame,
                    onValueChange = { pointsGame = it },
                    placeholder = { Text("Puntos objetivos >= 21...")},
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .width(250.dp)
                        .height(60.dp))

                Spacer(Modifier.height(8.dp))
                ButtonModel(
                    text = "Jugar",
                    enabled = true,
                    onClick = {
                        // Establecer valor de puntos por defecto
                        if((pointsGame.isBlank()) || (Integer.parseInt(pointsGame) < 21)){
                            pointsGame = "21"
                        }

                        // Establecer nombre de jugador por defecto
                        if(playerName.isBlank()){
                            playerName = "Jugador"
                        }

                        // Redirigir a actividad de juego
                        val destiny = Intent(
                            context,
                            GameActivity::class.java
                        ).apply{
                            putExtra("targetPoints", Integer.parseInt(pointsGame))
                            putExtra("playerName", playerName)
                        }
                        context.startActivity(destiny)
                    })

                Spacer(Modifier.height(40.dp))
                TextModel(text = "Elige tu opcion:", fontSize = 18)

                Spacer(Modifier.height(8.dp))
                ButtonModel(
                    text = "Ver instrucciones",
                    enabled = true,
                    onClick = {
                        // Redirigir a actividad de instrucciones
                        val destiny = Intent(
                            context,
                            InstructionsActivity::class.java
                        )
                        context.startActivity(destiny)
                    })

                Spacer(Modifier.height(8.dp))
                ButtonModel(
                    text = "Consultar jugada anterior",
                    enabled = true,
                    onClick = {
                        // Redirigir a actividad de juego o mensaje de no guardadi
                        if(tinyDB.getString("winner").isNullOrEmpty()){
                            Toast.makeText(
                                context,
                                "Sin partidas previas",
                                Toast.LENGTH_SHORT
                            ).show()

                        }else{
                            val destiny = Intent(
                                context,
                                LastPointsActivity::class.java
                            )
                            context.startActivity(destiny)
                        }
                    })

                Spacer(Modifier.height(40.dp))
                TextModel(text = "Elige tu tema de colores:", fontSize = 18)

                Spacer(Modifier.height(8.dp))
                ButtonModel(text = "Clasico", onClick = { onThemeChange(AppTheme.Classic) }, enabled = true)

                Spacer(Modifier.height(8.dp))
                ButtonModel(text = "Oscuro", onClick = { onThemeChange(AppTheme.Dark) }, enabled = true)

                Spacer(Modifier.height(8.dp))
                ButtonModel(text = "Neon", onClick = { onThemeChange(AppTheme.Neon) }, enabled = true)

                Spacer(Modifier.height(8.dp))
                ButtonModel(text = "Casino", onClick = { onThemeChange(AppTheme.Casino) }, enabled = true)

                Spacer(Modifier.height(8.dp))
                ButtonModel(text = "Quantico", onClick = { onThemeChange(AppTheme.Quantum) }, enabled = true)

                Spacer(Modifier.height(8.dp))
                ButtonModel(text = "Bodega Aurrera", onClick = { onThemeChange(AppTheme.Bodega_Aurrera) }, enabled = true)
            }
        }
    }
}
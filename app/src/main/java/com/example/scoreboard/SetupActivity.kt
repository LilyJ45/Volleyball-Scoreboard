package com.example.scoreboard

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
class SetupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gameRule = intent.getStringExtra("GAME_RULE") ?: "WIN_BY_2"

        setContent {
            BackHandler {
                val intent = Intent(this@SetupActivity, RulesActivity::class.java)
                startActivity(intent)
                finish()
            }

            var n1 by remember { mutableStateOf("") }
            var n2 by remember { mutableStateOf("") }
            var c1 by remember { mutableStateOf("BLUE") }
            var c2 by remember { mutableStateOf("RED") }

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("") },
                        navigationIcon = {
                            IconButton(onClick = {
                                val intent = Intent(this@SetupActivity, RulesActivity::class.java)
                                startActivity(intent)
                                finish()
                            }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Go Back")
                            }
                        }
                    )
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Setup Teams", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(24.dp))

                    TextField(value = n1, onValueChange = { n1 = it }, label = { Text("Home Team") }, modifier = Modifier.fillMaxWidth(0.8f))
                    Row(Modifier.padding(vertical = 8.dp)) {
                        Button(onClick = { c1 = "BLUE" }, modifier = Modifier.padding(end=8.dp)) { Text("Blue") }
                        Button(onClick = { c1 = "GREEN" }) { Text("Green") }
                    }

                    Spacer(Modifier.height(16.dp))

                    TextField(value = n2, onValueChange = { n2 = it }, label = { Text("Away Team") }, modifier = Modifier.fillMaxWidth(0.8f))
                    Row(Modifier.padding(vertical = 8.dp)) {
                        Button(onClick = { c2 = "RED" }, modifier = Modifier.padding(end=8.dp)) { Text("Red") }
                        Button(onClick = { c2 = "YELLOW" }) { Text("Yellow") }
                    }

                    Button(
                        onClick = {
                            val intent = Intent(this@SetupActivity, MainActivity::class.java).apply {
                                putExtra("GAME_RULE", gameRule)
                                putExtra("T1_NAME", n1.ifBlank { "Home" })
                                putExtra("T1_COLOR", c1)
                                putExtra("T2_NAME", n2.ifBlank { "Away" })
                                putExtra("T2_COLOR", c2)
                            }
                            startActivity(intent)
                            finish()
                        },
                        modifier = Modifier.padding(top = 32.dp).fillMaxWidth(0.6f).height(50.dp)
                    ) { Text("Start Match") }
                }
            }
        }
    }
}
package com.example.scoreboard

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.jvm.java

class LevelSelectActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: LevelViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsState()

            Column(modifier = Modifier.fillMaxSize().systemBarsPadding().padding(16.dp)) {
                Text("Select League Level", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))

                when (val state = uiState) {
                    is UiState.Loading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                    is UiState.Error -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Error: ${state.message}", color = Color.Red)
                        }
                    }
                    is UiState.Success -> {
                        LazyColumn(modifier = Modifier.weight(1f)) {
                            items(state.levels) { level ->
                                Button(
                                    onClick = {
                                        val intent = Intent(this@LevelSelectActivity, LevelInfoActivity::class.java)
                                        intent.putExtra("LEVEL_DATA", Json.encodeToString(level))
                                        startActivity(intent)
                                    },
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).height(60.dp)
                                ) {
                                    Text(level.level, fontSize = 18.sp)
                                }
                            }

                            item {
                                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
                                Button(
                                    onClick = {
                                        val intent = Intent(this@LevelSelectActivity, RulesActivity::class.java)
                                        startActivity(intent)
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).height(60.dp)
                                ) {
                                    Text("Churchball (Custom Rules)", fontSize = 18.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
package com.example.scoreboard

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class VictoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val winnerName = intent.getStringExtra("WINNER_NAME") ?: "A Team"

        setContent {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Match Over!", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.height(24.dp))

                Text("$winnerName Win!", fontSize = 48.sp, fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center)
                Spacer(Modifier.height(16.dp))

                Spacer(Modifier.height(48.dp))

                Button(
                    onClick = {
                        val intent = Intent(this@VictoryActivity, RulesActivity::class.java)
                        startActivity(intent)
                        finish()
                    },
                    modifier = Modifier.fillMaxWidth(0.6f).height(60.dp)
                ) {
                    Text("Play Again", fontSize = 20.sp)
                }
            }
        }
    }
}
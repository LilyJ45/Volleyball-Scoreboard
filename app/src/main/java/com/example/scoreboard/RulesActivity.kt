package com.example.scoreboard

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.jvm.java

class RulesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(painter = painterResource(id = R.drawable.volleyball), contentDescription = null, Modifier.size(120.dp))
                Spacer(Modifier.height(16.dp))
                Text("Volleyball Scoreboard", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(32.dp))

                Text("Select Game Rules:", fontSize = 20.sp)
                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = { launchSetup("WIN_BY_2") },
                    modifier = Modifier.fillMaxWidth(0.8f).height(50.dp)
                ) { Text("Standard (Win by 2)", fontSize = 18.sp) }

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = { launchSetup("STRICT_25") },
                    modifier = Modifier.fillMaxWidth(0.8f).height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) { Text("Strict (First to 25)", fontSize = 18.sp) }
            }
        }
    }

    private fun launchSetup(rule: String) {
        val intent = Intent(this, SetupActivity::class.java)
        intent.putExtra("GAME_RULE", rule)
        startActivity(intent)
        finish()
    }
}
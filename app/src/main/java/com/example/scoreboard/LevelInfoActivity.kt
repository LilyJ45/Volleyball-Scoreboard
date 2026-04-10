package com.example.scoreboard

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


class LevelInfoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val levelJson = intent.getStringExtra("LEVEL_DATA") ?: ""
        val levelData = Json.decodeFromString<VolleyballLevel>(levelJson)

        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
                    .padding(16.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(this@LevelInfoActivity)
                        .data(levelData.imageUrl)
                        .setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                        .crossfade(true)
                        .build(),
                    contentDescription = "${levelData.level} Image",
                    modifier = Modifier.fillMaxWidth().height(220.dp).padding(16.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())) {
                    Text("Rules Summary", fontWeight = FontWeight.Bold, fontSize = 26.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("• Points to win set: ${levelData.rules.pointsToWinSet}", fontSize = 18.sp)
                    Text("• Deciding set points: ${levelData.rules.pointsToWinDecidingSet}", fontSize = 18.sp)
                    Text("• Must win by two: ${if (levelData.rules.winByTwo) "Yes" else "No"}", fontSize = 18.sp)
                    Text("• Max Sets: ${levelData.rules.maxSets}", fontSize = 18.sp)

                    Spacer(modifier = Modifier.height(24.dp))

                    Text("Court Specs", fontWeight = FontWeight.Bold, fontSize = 26.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("• Men's Net: ${levelData.courtSpecs.netHeightMens}", fontSize = 18.sp)
                    Text("• Women's Net: ${levelData.courtSpecs.netHeightWomens}", fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    OutlinedButton(
                        onClick = { finish() },
                        modifier = Modifier.weight(1f).height(55.dp)
                    ) {
                        Text("Back", fontSize = 18.sp)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = {
                            val intent = Intent(this@LevelInfoActivity, SetupActivity::class.java).apply {
                                putExtra("GAME_RULE", if(levelData.rules.winByTwo) "WIN_BY_2" else "STRICT_25")
                            }
                            startActivity(intent)
                        },
                        modifier = Modifier.weight(1f).height(55.dp)
                    ) {
                        Text("Next (Teams)", fontSize = 18.sp)
                    }
                }
            }
        }
    }
}
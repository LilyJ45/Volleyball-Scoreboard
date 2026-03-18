package com.example.scoreboard

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.jvm.java

@Composable
fun ScoreboardContent(gameRule: String, n1: String, c1: Color, n2: String, c2: Color) {
    val context = LocalContext.current
    var p1 by rememberSaveable { mutableIntStateOf(0) }; var p2 by rememberSaveable { mutableIntStateOf(0) }
    var sets1 by rememberSaveable { mutableIntStateOf(0) }; var sets2 by rememberSaveable { mutableIntStateOf(0) }

    Column(Modifier.fillMaxSize()) {
        Row(Modifier.weight(1f)) {
            SwipeableScoreBox(
                name = n1, color = c1, score = p1, sets = sets1, modifier = Modifier.weight(1f),
                onScoreChange = { newScore ->
                    p1 = newScore
                    val isWin = if (gameRule == "STRICT_25") p1 >= 25 else (p1 >= 25 && p1 >= p2 + 2)

                    if (isWin) {
                        sets1++; p1 = 0; p2 = 0
                        if (sets1 == 3) {
                            val intent = android.content.Intent(context, VictoryActivity::class.java).apply {
                                putExtra("WINNER_NAME", n1)
                            }
                            context.startActivity(intent)
                            (context as? android.app.Activity)?.finish()
                        } else {
                            Toast.makeText(context, "$n1 won the set!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            )
            SwipeableScoreBox(
                name = n2, color = c2, score = p2, sets = sets2, modifier = Modifier.weight(1f),
                onScoreChange = { newScore ->
                    p2 = newScore
                    val isWin = if (gameRule == "STRICT_25") p2 >= 25 else (p2 >= 25 && p2 >= p1 + 2)

                    if (isWin) {
                        sets2++; p1 = 0; p2 = 0
                        if (sets2 == 3) {
                            val intent = android.content.Intent(context, VictoryActivity::class.java).apply {
                                putExtra("WINNER_NAME", n2)
                            }
                            context.startActivity(intent)
                            (context as? android.app.Activity)?.finish()
                        } else {
                            Toast.makeText(context, "$n2 won the set!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            )
        }

        Button(
            onClick = { p1 = 0; p2 = 0; sets1 = 0; sets2 = 0 },
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
        ) {
            Text("Reset Match", fontSize = 18.sp, modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun SwipeableScoreBox(name: String, color: Color, score: Int, sets: Int, modifier: Modifier, onScoreChange: (Int) -> Unit) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .background(color.copy(0.1f))
            .pointerInput(score) {
                var dragAmountAccumulator = 0f
                var hasTriggered = false

                detectVerticalDragGestures(
                    onDragStart = {
                        dragAmountAccumulator = 0f
                        hasTriggered = false
                    },
                    onVerticalDrag = { change, dragAmount ->
                        change.consume()

                        if (!hasTriggered) {
                            dragAmountAccumulator += dragAmount

                            if (dragAmountAccumulator < -30f) {
                                onScoreChange(score + 1)
                                hasTriggered = true
                            }
                            else if (dragAmountAccumulator > 30f && score > 0) {
                                onScoreChange(score - 1)
                                hasTriggered = true
                            }
                        }
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(name, fontWeight = FontWeight.Bold, fontSize = 28.sp)
            Text("Sets Won: $sets", fontSize = 16.sp, color = Color.Gray)
            Spacer(Modifier.height(16.dp))
            Text("$score", fontSize = 100.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))
            Text("Swipe Up/Down", fontSize = 12.sp, color = Color.Gray)
        }
    }
}
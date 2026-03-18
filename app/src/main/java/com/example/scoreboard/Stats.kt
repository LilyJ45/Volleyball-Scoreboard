package com.example.scoreboard

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StatsScreen(players: List<Player>) {
    val headers = listOf("Kill", "Ast", "Ace", "Blk", "Dig", "Set")
    Column(Modifier.fillMaxSize().padding(8.dp)) {
        Text("Swipe Box Up/Down to Change", Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontSize = 12.sp, color = Color.Gray)
        Spacer(Modifier.height(4.dp))
        Row(Modifier.fillMaxWidth().background(Color.LightGray).padding(vertical = 8.dp)) {
            Text("Player", Modifier.weight(1.5f).padding(start = 4.dp), fontWeight = FontWeight.Bold, fontSize = 12.sp)
            headers.forEach { Text(it, Modifier.weight(1f), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 11.sp) }
        }
        LazyColumn {
            items(players) { player ->
                Row(Modifier.fillMaxWidth().padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(player.name, Modifier.weight(1.5f).padding(start = 4.dp), fontSize = 13.sp)
                    repeat(6) { SwipeableStatBox(Modifier.weight(1f)) }
                }
                HorizontalDivider(thickness = 0.5.dp)
            }
        }
    }
}

@Composable
fun SwipeableStatBox(modifier: Modifier) {
    var count by rememberSaveable { mutableIntStateOf(0) }

    Box(
        modifier = modifier
            .height(40.dp)
            .padding(horizontal = 2.dp)
            .background(Color(0xFFF5F5F5))
            .pointerInput(Unit) {
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

                            if (dragAmountAccumulator < -60f) {
                                count++
                                hasTriggered = true
                            }
                            else if (dragAmountAccumulator > 60f && count > 0) {
                                count--
                                hasTriggered = true
                            }
                        }
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text("$count", fontSize = 16.sp)
    }
}
package com.example.scoreboard

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RosterScreen(players: MutableList<Player>) {
    var name by remember { mutableStateOf("") }
    var pos by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Team Roster", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        TextField(value = name, onValueChange = { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
        TextField(value = pos, onValueChange = { pos = it }, label = { Text("Position") }, modifier = Modifier.fillMaxWidth())
        Button(onClick = { if(name.isNotBlank()){ players.add(Player(name, pos)); name=""; pos="" } }, Modifier.padding(top = 8.dp)) { Text("Add Player") }

        LazyColumn(Modifier.fillMaxSize().padding(top = 16.dp)) {
            items(players) { player ->
                Card(Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { Toast.makeText(context, "${player.name} selected!", Toast.LENGTH_SHORT).show()
                    }) {
                    Row(Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Text(player.name, fontWeight = FontWeight.Bold)
                            Text(player.position, fontSize = 12.sp, color = Color.Gray)
                        }
                        IconButton(onClick = { players.remove(player) }) { Icon(Icons.Default.Delete, null, tint = Color.Red) }
                    }
                }
            }
        }
    }
}
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavDestination.Companion.hasRoute

data class Player(val name: String, val position: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gameRule = intent.getStringExtra("GAME_RULE") ?: "WIN_BY_2"
        val team1Name = intent.getStringExtra("T1_NAME") ?: "Home"
        val team1Color = intent.getStringExtra("T1_COLOR") ?: "BLUE"
        val team2Name = intent.getStringExtra("T2_NAME") ?: "Away"
        val team2Color = intent.getStringExtra("T2_COLOR") ?: "RED"

        setContent {
            val playerList = remember { mutableStateListOf<Player>() }
            MainApp(gameRule, team1Name, team1Color, team2Name, team2Color, playerList)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(rule: String, t1Name: String, t1Color: String, t2Name: String, t2Color: String, players: MutableList<Player>) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val context = LocalContext.current

    // Dialog State
    var showExitDialog by remember { mutableStateOf(false) }

    BackHandler {
        showExitDialog = true
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Exit Match?") },
            text = { Text("Are you sure you want to go back to the main menu? All setup choices, scores, stats, and roster data will be permanently lost.") },
            confirmButton = {
                TextButton(onClick = {
                    showExitDialog = false
                    val intent = Intent(context, RulesActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                    (context as? android.app.Activity)?.finish()
                }) { Text("Yes, Exit", color = Color.Red) }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) { Text("Cancel") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Match in Progress") },
                navigationIcon = {
                    // This is the visible back button
                    IconButton(onClick = { showExitDialog = true }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Exit Match")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                val items = listOf(BottomNavigation.Score, BottomNavigation.Roster, BottomNavigation.Stats)
                items.forEach { item ->
                    val isSelected = currentDestination?.hasRoute(item.route::class) == true
                    NavigationBarItem(
                        selected = isSelected,
                        icon = { Icon(item.selectedIcon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        onClick = {
                            if (!isSelected) {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(navController, startDestination = Destination.Score, modifier = Modifier.padding(padding)) {
            composable<Destination.Score> { ScoreboardContent(rule, t1Name, getActualColor(t1Color), t2Name, getActualColor(t2Color)) }
            composable<Destination.Roster> { RosterScreen(players) }
            composable<Destination.Stats> { StatsScreen(players) }
        }
    }
}

fun getActualColor(key: String): Color = when (key) {
    "BLUE" -> Color.Blue; "GREEN" -> Color.Green; "RED" -> Color.Red; "YELLOW" -> Color.Yellow; else -> Color.Gray
}
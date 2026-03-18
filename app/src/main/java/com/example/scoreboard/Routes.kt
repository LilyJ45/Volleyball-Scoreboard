package com.example.scoreboard

import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.List
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

@kotlinx.serialization.Serializable
sealed class Destination(val label: String) {
    @kotlinx.serialization.Serializable
    data object Score: Destination("Score")
    @kotlinx.serialization.Serializable
    data object Roster: Destination("Roster")
    @kotlinx.serialization.Serializable
    data object Stats: Destination("Stats")
}
sealed class BottomNavigation(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int,
    val route: Destination
) {
    data object Score : BottomNavigation("Score",
        Icons.Filled.Star, Icons.Outlined.Star, 0, Destination.Score
    )
    data object Roster : BottomNavigation("Roster",
        Icons.Filled.Person, Icons.Outlined.Person, 0, Destination.Roster
    )
    data object Stats : BottomNavigation("Stats",
        Icons.Filled.List, Icons.Outlined.List, 0, Destination.Stats
    )
}
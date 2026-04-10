package com.example.scoreboard

import kotlinx.serialization.Serializable

@Serializable
data class VolleyballLevel(
    val id: String,
    val level: String,
    val governingBody: String,
    val imageUrl: String? = null,
    val rules: Rules,
    val courtSpecs: CourtSpecs
)

@Serializable
data class Rules(
    val pointsToWinSet: Int,
    val pointsToWinDecidingSet: Int,
    val winByTwo: Boolean,
    val maxSets: Int,
    val liberoAllowed: Boolean,
    val substitutionsPerSet: Int
)

@Serializable
data class CourtSpecs(
    val netHeightMens: String,
    val netHeightWomens: String,
    val courtDimensions: String
)
package com.example.scoreboard

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import io.ktor.http.ContentType

class VolleyballRepository {
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                },
                contentType = ContentType.Any
            )
        }
    }

    suspend fun getLevels(): List<VolleyballLevel> {
        val apiUrl = "https://gist.githubusercontent.com/LilyJ45/c15d69e4d38399145b769e2e86440a9b/raw/volleyball_levels.json?t=${System.currentTimeMillis()}"
        return client.get(apiUrl).body()
    }
}
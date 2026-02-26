package com.example.tallerrick.network

import com.example.tallerrick.model.CharacterResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object CharacterRepository {

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun getCharacters(): CharacterResponse {
        return client.get("https://rickandmortyapi.com/api/character/?page=3").body()
    }
}
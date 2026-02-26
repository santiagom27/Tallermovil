package com.example.tallerrick.model

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val name: String
)

@Serializable
data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val origin: Location,
    val location: Location,
    val image: String
)

@Serializable
data class ApiInfo(
    val count: Int,
    val pages: Int
)

@Serializable
data class CharacterResponse(
    val info: ApiInfo,
    val results: List<Character>
)
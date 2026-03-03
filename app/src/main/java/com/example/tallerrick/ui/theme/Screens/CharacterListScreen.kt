package com.example.tallerrick.ui.theme.Screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tallerrick.model.Character
import com.example.tallerrick.ui.theme.Components.CharacterItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListScreen(
    characters: List<Character>,
    isLoading: Boolean,
    error: String?,
    onRetry: () -> Unit,
    isDarkMode: Boolean,
    onToggleDarkMode: () -> Unit,
    onCharacterClick: (Character) -> Unit
) {
    val countOnScreen = characters.size

    if (!isLoading && error == null) {
        Log.d("CharacterList", "Cantidad de personajes: $countOnScreen")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Personajes: $countOnScreen") },
                actions = {
                    IconButton(onClick = onToggleDarkMode) {
                        Icon(
                            imageVector = if (isDarkMode) Icons.Filled.Brightness7 else Icons.Filled.Brightness4,
                            contentDescription = if (isDarkMode) "Activar modo claro" else "Activar modo oscuro"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }
            }

            error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Error: $error")
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(onClick = onRetry) { Text("Reintentar") }
                    }
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    items(characters) { character ->
                        CharacterItem(
                            character = character,
                            onClick = { onCharacterClick(character) }
                        )
                    }
                }
            }
        }
    }
}
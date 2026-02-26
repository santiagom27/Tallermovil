package com.example.tallerrick

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.example.tallerrick.model.Character
import com.example.tallerrick.ui.theme.TallerRickTheme
import com.example.tallerrick.ui.theme.Screens.CharacterDetailScreen
import com.example.tallerrick.ui.theme.Screens.CharacterListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TallerRickTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    var selectedCharacter by remember { mutableStateOf<Character?>(null) }

    if (selectedCharacter == null) {
        CharacterListScreen(
            onCharacterClick = { character ->
                selectedCharacter = character
            }
        )
    } else {
        CharacterDetailScreen(
            character = selectedCharacter!!,
            onBack = {
                selectedCharacter = null
            }
        )
    }
}
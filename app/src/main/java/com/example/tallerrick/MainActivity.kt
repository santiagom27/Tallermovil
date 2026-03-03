package com.example.tallerrick

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tallerrick.ui.CharacterViewModel
import com.example.tallerrick.ui.theme.TallerRickTheme
import com.example.tallerrick.ui.theme.Screens.CharacterDetailScreen
import com.example.tallerrick.ui.theme.Screens.CharacterListScreen

sealed class Route {
    data object List : Route()
    data object Detail : Route()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            var isDarkMode by rememberSaveable { mutableStateOf(false) }

            TallerRickTheme(
                darkTheme = isDarkMode,
                dynamicColor = false
            ) {
                AppNavigation(
                    isDarkMode = isDarkMode,
                    onToggleDarkMode = { isDarkMode = !isDarkMode }
                )
            }
        }
    }
}

@Composable
fun AppNavigation(
    isDarkMode: Boolean,
    onToggleDarkMode: () -> Unit,
    vm: CharacterViewModel = viewModel()
) {
    var route by remember { mutableStateOf<Route>(Route.List) }

    LaunchedEffect(Unit) {
        vm.loadCharactersOnce()
    }

    when (route) {
        Route.List -> {
            val state = vm.uiState

            CharacterListScreen(
                characters = state.characters,
                isLoading = state.isLoading,
                error = state.error,
                onRetry = { vm.retry() },
                isDarkMode = isDarkMode,
                onToggleDarkMode = onToggleDarkMode,
                onCharacterClick = { character ->
                    vm.selectCharacter(character)
                    route = Route.Detail
                }
            )
        }

        Route.Detail -> {
            val character = vm.selectedCharacter
            if (character != null) {
                CharacterDetailScreen(
                    character = character,
                    onBack = {
                        vm.clearSelection()
                        route = Route.List
                    }
                )
            } else {
                route = Route.List
            }
        }
    }
}
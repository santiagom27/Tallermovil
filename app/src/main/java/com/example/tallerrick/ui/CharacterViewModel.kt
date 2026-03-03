package com.example.tallerrick.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tallerrick.model.Character
import com.example.tallerrick.network.CharacterRepository
import kotlinx.coroutines.launch

data class CharacterUiState(
    val characters: List<Character> = emptyList(),
    val totalCount: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null
)

class CharacterViewModel : ViewModel() {

    var uiState by mutableStateOf(CharacterUiState(isLoading = true))
        private set

    var selectedCharacter: Character? by mutableStateOf(null)
        private set

    private var hasLoaded = false

    fun loadCharactersOnce() {
        if (hasLoaded) return
        hasLoaded = true

        uiState = uiState.copy(isLoading = true, error = null)

        viewModelScope.launch {
            try {
                val response = CharacterRepository.getCharacters()
                uiState = uiState.copy(
                    characters = response.results,
                    totalCount = response.info.count,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = e.message ?: "Error cargando personajes"
                )
            }
        }
    }

    fun retry() {
        hasLoaded = false
        loadCharactersOnce()
    }

    fun selectCharacter(character: Character) {
        selectedCharacter = character
    }

    fun clearSelection() {
        selectedCharacter = null
    }
}
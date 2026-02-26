package com.example.tallerrick.ui.theme.Screens
import com.example.tallerrick.ui.theme.Components.CharacterItem

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.tallerrick.model.Character
import com.example.tallerrick.network.CharacterRepository
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListScreen(
    onCharacterClick: (Character) -> Unit
) {
    var characters by remember { mutableStateOf<List<Character>>(emptyList()) }
    var totalCount by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        val response = CharacterRepository.getCharacters()
        characters = response.results
        totalCount = response.info.count
        Log.d("CharacterList", "Cantidad de personajes: ${response.results.size}")
    }

    LazyColumn {
        stickyHeader {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Total de personajes: $totalCount",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }

        items(characters) { character ->
            CharacterItem(
                character = character,
                onClick = { onCharacterClick(character) }
            )
        }
    }
}
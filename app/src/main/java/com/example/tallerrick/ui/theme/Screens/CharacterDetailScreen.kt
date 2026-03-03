package com.example.tallerrick.ui.theme.Screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.tallerrick.model.Character

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    character: Character,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    // LÓGICA DEL BONO: Animación corregida
    val infiniteTransition = rememberInfiniteTransition(label = "gradiente")
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2000f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "offset"
    )

    // ESTO ES LO QUE ARREGLA EL ERROR: Creamos el Brush aquí afuera
    val miGradiente = Brush.linearGradient(
        colors = listOf(Color.Cyan, Color.Magenta, Color.Yellow),
        start = Offset(offset, offset),
        end = Offset(offset + 600f, offset + 600f)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(character.name) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(miGradiente, alpha = 0.1f) // Ya no dará error aquí
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = character.image,
                    contentDescription = character.name,
                    modifier = Modifier
                        .size(200.dp)
                        .border(5.dp, miGradiente, CircleShape)
                        .padding(5.dp)
                        .clip(CircleShape)
                        .clickable {
                            val intent = Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.parse("tel:${character.id}")
                            }
                            context.startActivity(intent)
                        }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = character.name,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(brush = miGradiente)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        DetailRow("Estado", character.status)
                        DetailRow("Especie", character.species)
                        DetailRow("Género", character.gender)
                        DetailRow("Origen", character.origin.name)
                        DetailRow("Ubicación", character.location.name)
                        DetailRow("ID", character.id.toString())
                    }
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontWeight = FontWeight.Bold)
        Text(text = value)
    }
    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
}
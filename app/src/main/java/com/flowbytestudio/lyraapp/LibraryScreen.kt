package com.flowbytestudio.lyraapp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen() {
    val surfaceColor = Color(0xFF141317)
    val primaryColor = Color(0xFF7C4DFF)
    val surfaceVariant = Color(0xFF1C1B1F)

    Scaffold(
        containerColor = surfaceColor,
        topBar = {
            TopAppBar(
                title = { Text("LyraApp", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = Color.White) },
                actions = { IconButton(onClick = { /* Settings */ }) { Icon(Icons.Default.Settings, null, tint = Color.White) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Create Playlist */ },
                containerColor = Color(0xFF00C4B4),
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Create")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).padding(horizontal = 16.dp)) {
            // Tabs - SuggestionChip fixed with correct defaults
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                // Seçili durum simülasyonu için özel renkler ve border
                SuggestionChip(
                    onClick = {},
                    label = { Text("Playlists") },
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = primaryColor.copy(alpha = 0.1f),
                        labelColor = primaryColor
                    ),
                    // Material 3'te border parametresi SuggestionChip için opsiyoneldir.
                    // Hata almamak için SuggestionChipDefaults.suggestionChipBorder kullanıyoruz.
                    border = SuggestionChipDefaults.suggestionChipBorder(
                        enabled = true,
                        borderColor = primaryColor,
                        borderWidth = 1.dp
                    )
                )

                SuggestionChip(
                    onClick = {},
                    label = { Text("Artists") },
                    colors = SuggestionChipDefaults.suggestionChipColors(labelColor = Color.White)
                )

                SuggestionChip(
                    onClick = {},
                    label = { Text("Albums") },
                    colors = SuggestionChipDefaults.suggestionChipColors(labelColor = Color.White)
                )
            }

            // ... (Geri kalan kod yapısı korunmuştur)
            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                color = surfaceVariant,
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth().height(80.dp),
                onClick = { /* Action */ }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 16.dp)) {
                    Surface(color = primaryColor.copy(alpha = 0.2f), shape = RoundedCornerShape(12.dp), modifier = Modifier.size(48.dp)) {
                        Box(contentAlignment = Alignment.Center) { Icon(Icons.Default.Add, null, tint = primaryColor) }
                    }
                    Column(modifier = Modifier.padding(start = 16.dp)) {
                        Text("Create New Playlist", color = Color.White, fontWeight = FontWeight.Bold)
                        Text("START BUILDING YOUR SOUND", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                item {
                    PlaylistLargeCard("Hyper Reality", "42 tracks • Last updated 2h ago")
                }
            }
        }
    }
}

@Composable
fun PlaylistLargeCard(title: String, subtitle: String) {
    Surface(
        color = Color(0xFF1C1B1F),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.fillMaxWidth().height(200.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.align(Alignment.BottomStart).padding(20.dp)) {
                Text(title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = Color.White)
                Text(subtitle, color = Color.LightGray, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

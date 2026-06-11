
/*
package com.flowbytestudio.lyraapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NowPlayingScreen() {
    // Theme colors from LyraApp Design System
    val surfaceColor = Color(0xFF141317)
    val primaryColor = Color(0xFF7C4DFF)
    val onSurfaceVariant = Color(0xFFCAC4D0)

    Scaffold(
        containerColor = surfaceColor,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "NOW PLAYING",
                            style = MaterialTheme.typography.labelSmall,
                            color = onSurfaceVariant
                        )
                        Text(
                            "LyraApp",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { /* Back */ }) {
                        Icon(Icons.Default.KeyboardArrowDown, "Minimize", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = { /* More options */ }) {
                        Icon(Icons.Default.MoreVert, "More", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Album Artwork
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(24.dp))
            ) {
                // Placeholder for Image(painter = painterResource(id = R.drawable.album_art), ...)
                Surface(
                    color = Color(0xFF1C1B1F),
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        Icons.Default.MusicNote,
                        contentDescription = null,
                        modifier = Modifier.size(120.dp),
                        tint = primaryColor.copy(alpha = 0.5f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Song Info & Favorite
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Neon Horizon",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        "Aural Fluidity Ensemble",
                        style = MaterialTheme.typography.bodyLarge,
                        color = onSurfaceVariant
                    )
                }
                IconButton(onClick = { /* Toggle favorite */ }) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Progress Slider
            var sliderPosition by remember { mutableStateOf(0.4f) }
            Slider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it },
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = primaryColor,
                    inactiveTrackColor = Color(0xFF3A383D)
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("1:42", style = MaterialTheme.typography.labelSmall, color = onSurfaceVariant)
                Text("3:45", style = MaterialTheme.typography.labelSmall, color = onSurfaceVariant)
            }

            Spacer(modifier = Modifier.weight(1f))

            // Playback Controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* Shuffle */ }) {
                    Icon(Icons.Default.Shuffle, "Shuffle", tint = onSurfaceVariant)
                }
                IconButton(onClick = { /* Previous */ }, modifier = Modifier.size(48.dp)) {
                    Icon(Icons.Default.SkipPrevious, "Previous", tint = Color.White, modifier = Modifier.size(32.dp))
                }

                // Play/Pause Button (Large)
                Surface(
                    onClick = { /* Play/Pause */ },
                    shape = RoundedCornerShape(28.dp),
                    color = primaryColor,
                    modifier = Modifier.size(80.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.PlayArrow, "Play", tint = Color.White, modifier = Modifier.size(40.dp))
                    }
                }

                IconButton(onClick = { /* Next */ }, modifier = Modifier.size(48.dp)) {
                    Icon(Icons.Default.SkipNext, "Next", tint = Color.White, modifier = Modifier.size(32.dp))
                }
                IconButton(onClick = { /* Repeat */ }) {
                    Icon(Icons.Default.Repeat, "Repeat", tint = onSurfaceVariant)
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Bottom Actions (Casting, Lyrics, Queue)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Speaker Output
                Surface(
                    color = Color(0xFF1C1B1F),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.height(40.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Speaker, "Output", tint = onSurfaceVariant, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Living Room Speakers", style = MaterialTheme.typography.labelMedium, color = Color.White)
                    }
                }

                Row {
                    IconButton(onClick = { /* Share */ }) {
                        Icon(Icons.Default.Share, "Share", tint = onSurfaceVariant)
                    }
                    IconButton(onClick = { /* Queue */ }) {
                        Icon(Icons.Default.QueueMusic, "Queue", tint = onSurfaceVariant)
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}


 */
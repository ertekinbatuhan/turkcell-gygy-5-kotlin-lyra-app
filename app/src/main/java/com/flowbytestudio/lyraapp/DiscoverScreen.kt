package com.flowbytestudio.lyraapp

/*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverScreen() {
    val surfaceColor = Color(0xFF141317)
    val primaryColor = Color(0xFF7C4DFF)
    val surfaceVariant = Color(0xFF1C1B1F)

    Scaffold(
        containerColor = surfaceColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "LyraApp",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                actions = {
                    IconButton(onClick = { /* Settings */ }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.White)
                    }
                    IconButton(onClick = { /* Profile */ }) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(Color.Gray)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = {
            LyraBottomNavigation()
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Text(
                    "Good Morning",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }

            // Quick Access Grid (Recent/Pinned)
            item {
                val items = listOf("Night Drive", "Urban Echoes", "Synth Pop", "Soul Sessions")
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.height(140.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    userScrollEnabled = false
                ) {
                    items(items) { title ->
                        QuickAccessCard(title, surfaceVariant)
                    }
                }
            }

            // "Made For You" Section
            item {
                SectionHeader("Made For You")
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(3) { index ->
                        RecommendationCard(
                            title = if (index == 0) "Discover Weekly" else "Daily Mix $index",
                            subtitle = "Tailored for you",
                            surfaceVariant = surfaceVariant
                        )
                    }
                }
            }

            // "Trending Now" Section
            item {
                SectionHeader("Trending Now")
                TrendingSection(primaryColor, surfaceVariant)
            }

            // Spacer for Mini Player
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }

        // Overlay Mini Player
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            MiniPlayer(primaryColor)
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        TextButton(onClick = { /* Show all */ }) {
            Text("Show all", color = Color(0xFFCAC4D0))
        }
    }
}

@Composable
fun QuickAccessCard(title: String, bgColor: Color) {
    Surface(
        color = bgColor,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(Color.DarkGray)
            )
            Text(
                title,
                modifier = Modifier.padding(horizontal = 12.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                maxLines = 1
            )
        }
    }
}

@Composable
fun RecommendationCard(title: String, subtitle: String, surfaceVariant: Color) {
    Column(modifier = Modifier.width(160.dp)) {
        Box(
            modifier = Modifier
                .size(160.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(surfaceVariant)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(title, color = Color.White, fontWeight = FontWeight.Bold)
        Text(subtitle, color = Color(0xFFCAC4D0), style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun TrendingSection(primaryColor: Color, surfaceVariant: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Large Featured Card
        Surface(
            modifier = Modifier.weight(1.5f),
            shape = RoundedCornerShape(24.dp),
            color = surfaceVariant
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // Background Gradient/Image placeholder
                Box(modifier = Modifier.fillMaxSize().background(
                    Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)))
                ))
                Column(
                    modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)
                ) {
                    Surface(color = primaryColor, shape = RoundedCornerShape(4.dp)) {
                        Text(
                            "ALBUM OF THE WEEK",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White
                        )
                    }
                    Text("Cosmic Horizon", color = Color.White, fontWeight = FontWeight.Bold)
                    Text("The Zenith Project", color = Color.LightGray)
                }
            }
        }

        // Vertical stack for smaller items
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Surface(modifier = Modifier.weight(1f).fillMaxWidth(), shape = RoundedCornerShape(16.dp), color = surfaceVariant) {
                Box(contentAlignment = Alignment.Center) { Icon(Icons.Default.TrendingUp, null, tint = Color(0xFF00C4B4)) }
            }
            Surface(modifier = Modifier.weight(1f).fillMaxWidth(), shape = RoundedCornerShape(16.dp), color = primaryColor) {
                Box(contentAlignment = Alignment.Center) { Icon(Icons.Default.Favorite, null, tint = Color.White) }
            }
        }
    }
}

@Composable
fun MiniPlayer(primaryColor: Color) {
    Surface(
        color = Color(0xFF1C1B1F),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 84.dp) // Offset above BottomNav
            .fillMaxWidth()
            .height(64.dp),
        tonalElevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(48.dp).clip(RoundedCornerShape(8.dp)).background(Color.Gray))
            Column(modifier = Modifier.weight(1f).padding(horizontal = 12.dp)) {
                Text("Night Drive", color = Color.White, fontWeight = FontWeight.Bold, maxLines = 1)
                Text("The Nocturnals", color = Color.LightGray, style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = { /* Play/Pause */ }) {
                Surface(shape = CircleShape, color = Color.White.copy(alpha = 0.1f)) {
                    Icon(Icons.Default.PlayArrow, null, tint = Color.White)
                }
            }
            IconButton(onClick = { /* Skip */ }) {
                Icon(Icons.Default.SkipNext, null, tint = Color.White)
            }
        }
    }
}

@Composable
fun LyraBottomNavigation() {
    NavigationBar(containerColor = Color(0xFF141317)) {
        NavigationBarItem(selected = true, onClick = {}, icon = { Icon(Icons.Default.Home, null) }, label = { Text("Home") })
        NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.Search, null) }, label = { Text("Search") })
        NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.LibraryMusic, null) }, label = { Text("Library") })
        NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.Person, null) }, label = { Text("Profile") })
    }
}
*/

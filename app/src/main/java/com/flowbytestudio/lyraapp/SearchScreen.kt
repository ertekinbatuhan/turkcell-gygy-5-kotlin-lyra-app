package com.flowbytestudio.lyraapp

/*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
fun SearchScreen() {
    val surfaceColor = Color(0xFF141317)
    val surfaceVariant = Color(0xFF1C1B1F)

    Scaffold(
        containerColor = surfaceColor,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Search", color = Color.White, fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { /* Settings */ }) {
                        Icon(Icons.Default.Settings, contentDescription = null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            // Search Bar
            SearchBar(
                query = "",
                onQueryChange = {},
                onSearch = {},
                active = false,
                onActiveChange = {},
                placeholder = { Text("Artists, songs, or podcasts") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = { Icon(Icons.Default.Mic, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                colors = SearchBarDefaults.colors(containerColor = surfaceVariant)
            ) {}

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Browse all",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Categories Grid
            val categories = listOf(
                "Pop" to Color(0xFF7C4DFF),
                "Rock" to Color(0xFF00C4B4),
                "Chill" to Color(0xFF6200EE),
                "Focus" to Color(0xFF3A383D),
                "Hip Hop" to Color(0xFF424242),
                "Jazz" to Color(0xFF00796B)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(categories) { (name, color) ->
                    CategoryCard(name, color)
                }
            }
        }
    }
}

@Composable
fun CategoryCard(name: String, color: Color) {
    Surface(
        color = color,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        Box(modifier = Modifier.padding(12.dp)) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            // Optional: Add a tilted image placeholder here to match the design
        }
    }
}
*/
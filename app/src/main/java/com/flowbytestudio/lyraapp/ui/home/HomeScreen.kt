package com.flowbytestudio.lyraapp.ui.home

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.flowbytestudio.lyraapp.data.home.ArtType
import com.flowbytestudio.lyraapp.data.home.QuickAccessItem
import com.flowbytestudio.lyraapp.data.home.RecentlyPlayedItem
import com.flowbytestudio.lyraapp.data.home.RecommendedPlaylist
import com.flowbytestudio.lyraapp.ui.theme.LyraAppTheme

/**
 * Ana sayfa (Home) ekranının stateful sarmalayıcısı.
 */
@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
                is HomeEffect.NavigateToDetail -> {
                    Toast.makeText(context, "Detay ekranı açılıyor: ${effect.title}", Toast.LENGTH_SHORT).show()
                }
                is HomeEffect.ShowProfile -> {
                    Toast.makeText(context, "Profil ekranı açılıyor.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    HomeScreen(
        state = uiState,
        onIntent = viewModel::onIntent,
        modifier = modifier,
    )
}

/**
 * Ana sayfa (Home) ekranının stateless arayüz bileşeni.
 */
@Composable
fun HomeScreen(
    state: HomeUiState,
    onIntent: (HomeIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    LyraAppTheme(darkTheme = state.isDarkMode) {
        val backgroundColor = MaterialTheme.colorScheme.background
        val textColor = MaterialTheme.colorScheme.onBackground
        val subtextColor = MaterialTheme.colorScheme.onSurfaceVariant
        val accentColor = MaterialTheme.colorScheme.primary
        val cardBgColor = MaterialTheme.colorScheme.surfaceVariant

        Scaffold(
            modifier = modifier.fillMaxSize(),
            containerColor = backgroundColor,
            bottomBar = {
                LyraHomeBottomNavigation(
                    selectedRoute = state.selectedNavRoute,
                    onRouteSelected = { onIntent(HomeIntent.BottomNavClicked(it)) }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (state.isLoading && state.quickAccess.isEmpty()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = accentColor
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        item {
                            HomeHeader(
                                greeting = state.greeting,
                                textColor = textColor,
                                subtextColor = subtextColor,
                                isDarkMode = state.isDarkMode,
                                onToggleDarkMode = { onIntent(HomeIntent.ToggleDarkMode) },
                                onProfileClick = { onIntent(HomeIntent.ProfileClicked) }
                            )
                        }

                        item {
                            QuickAccessGrid(
                                items = state.quickAccess,
                                cardBgColor = cardBgColor,
                                textColor = textColor,
                                onItemClick = { onIntent(HomeIntent.QuickAccessClicked(it)) }
                            )
                        }

                        item {
                            RecentlyPlayedSection(
                                items = state.recentlyPlayed,
                                textColor = textColor,
                                subtextColor = subtextColor,
                                accentColor = accentColor,
                                onItemClick = { onIntent(HomeIntent.RecentlyPlayedClicked(it)) },
                                onSeeAllClick = { onIntent(HomeIntent.SeeAllRecentlyPlayed) }
                            )
                        }

                        item {
                            RecommendedPlaylistsSection(
                                items = state.recommendedPlaylists,
                                textColor = textColor,
                                subtextColor = subtextColor,
                                onItemClick = { onIntent(HomeIntent.RecommendedPlaylistClicked(it)) }
                            )
                        }

                        // Bottom padding spacer to avoid content overlap
                        item { Spacer(modifier = Modifier.height(16.dp)) }
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeHeader(
    greeting: String,
    textColor: Color,
    subtextColor: Color,
    isDarkMode: Boolean,
    onToggleDarkMode: () -> Unit,
    onProfileClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = greeting,
                style = MaterialTheme.typography.bodyMedium,
                color = subtextColor,
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Ne dinlemek istersin?",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-0.5).sp
                ),
                color = textColor
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Dark Mode Toggle Moon Button
            IconButton(onClick = onToggleDarkMode) {
                Text(
                    text = if (isDarkMode) "☀️" else "🌙",
                    fontSize = 20.sp
                )
            }

            // User Initials Badge (ZK)
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF8B6F68))
                    .clickable(onClick = onProfileClick),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "ZK",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
private fun QuickAccessGrid(
    items: List<QuickAccessItem>,
    cardBgColor: Color,
    textColor: Color,
    onItemClick: (QuickAccessItem) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val rows = items.chunked(2)
        for (rowItems in rows) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (item in rowItems) {
                    QuickAccessCard(
                        item = item,
                        cardBgColor = cardBgColor,
                        textColor = textColor,
                        onClick = { onItemClick(item) },
                        modifier = Modifier.weight(1f)
                    )
                }
                if (rowItems.size < 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun QuickAccessCard(
    item: QuickAccessItem,
    cardBgColor: Color,
    textColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .height(56.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(cardBgColor)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MusicCoverArt(
            artType = item.artType,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
        )
        Text(
            text = item.title,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = textColor,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
}

@Composable
private fun RecentlyPlayedSection(
    items: List<RecentlyPlayedItem>,
    textColor: Color,
    subtextColor: Color,
    accentColor: Color,
    onItemClick: (RecentlyPlayedItem) -> Unit,
    onSeeAllClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Son çalınanlar",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = textColor
            )
            Text(
                text = "Tümü",
                color = accentColor,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .clickable(onClick = onSeeAllClick)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(end = 16.dp)
        ) {
            items(items) { item ->
                Column(
                    modifier = Modifier
                        .width(130.dp)
                        .clickable { onItemClick(item) }
                ) {
                    MusicCoverArt(
                        artType = item.artType,
                        modifier = Modifier
                            .size(130.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = textColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = item.subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = subtextColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
private fun RecommendedPlaylistsSection(
    items: List<RecommendedPlaylist>,
    textColor: Color,
    subtextColor: Color,
    onItemClick: (RecommendedPlaylist) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Senin için çalma listeleri",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = textColor
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(end = 16.dp)
        ) {
            items(items) { item ->
                Column(
                    modifier = Modifier
                        .width(150.dp)
                        .clickable { onItemClick(item) }
                ) {
                    MusicCoverArt(
                        artType = item.artType,
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = textColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = item.subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = subtextColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

/**
 * Albüm kapaklarını ve gradyan desenlerini dinamik olarak çizen Compose Canvas bileşeni.
 */
@Composable
fun MusicCoverArt(
    artType: ArtType,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            when (artType) {
                ArtType.PURPLE_WAVE -> {
                    // Gece Sürüşü: Mor/Eflatun gradyan + dairesel dalgalar
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF8E8CD8), Color(0xFF514080))
                        )
                    )
                    // Concentric waves
                    val waveColor = Color.White.copy(alpha = 0.15f)
                    val stroke = Stroke(width = 3.dp.toPx())
                    drawCircle(color = waveColor, radius = width * 0.4f, center = Offset(0f, height), style = stroke)
                    drawCircle(color = waveColor, radius = width * 0.6f, center = Offset(0f, height), style = stroke)
                    drawCircle(color = waveColor, radius = width * 0.8f, center = Offset(0f, height), style = stroke)
                    drawCircle(color = waveColor, radius = width * 1.0f, center = Offset(0f, height), style = stroke)
                }
                ArtType.BLUE_TRIANGLE -> {
                    // Sabah Kahvesi: Mavi gradyan + nokta matrisi + büyük üçgen
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF5A75C7), Color(0xFF38468F))
                        )
                    )
                    // Dotted background grid
                    val dotColor = Color.White.copy(alpha = 0.15f)
                    val cols = 8
                    val rows = 8
                    val cellW = width / cols
                    val cellH = height / rows
                    for (i in 1 until cols) {
                        for (j in 1 until rows) {
                            drawCircle(color = dotColor, radius = 2.dp.toPx(), center = Offset(i * cellW, j * cellH))
                        }
                    }
                    // Centered semi-transparent triangle
                    val path = Path().apply {
                        moveTo(width / 2f, height * 0.25f)
                        lineTo(width * 0.25f, height * 0.75f)
                        lineTo(width * 0.75f, height * 0.75f)
                        close()
                    }
                    drawPath(
                        path = path,
                        color = Color.White.copy(alpha = 0.25f)
                    )
                }
                ArtType.ORANGE_CIRCLE -> {
                    // Neon Sokaklar: Turuncu/Kahve gradyan + iç içe daireler
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFFD38D4A), Color(0xFF7A4A28))
                        )
                    )
                    val stroke = Stroke(width = 4.dp.toPx())
                    val circleColor = Color.Black.copy(alpha = 0.1f)
                    drawCircle(color = circleColor, radius = width * 0.2f, center = Offset(width / 2f, height / 2f), style = stroke)
                    drawCircle(color = circleColor, radius = width * 0.35f, center = Offset(width / 2f, height / 2f), style = stroke)
                    drawCircle(color = circleColor, radius = width * 0.5f, center = Offset(width / 2f, height / 2f), style = stroke)
                }
                ArtType.TEAL_TRIANGLE -> {
                    // Odaklan: Turkuaz/Yeşil gradyan + nokta matrisi + büyük üçgen
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF48A9A6), Color(0xFF1D5A58))
                        )
                    )
                    val dotColor = Color.White.copy(alpha = 0.15f)
                    val cols = 8
                    val rows = 8
                    val cellW = width / cols
                    val cellH = height / rows
                    for (i in 1 until cols) {
                        for (j in 1 until rows) {
                            drawCircle(color = dotColor, radius = 2.dp.toPx(), center = Offset(i * cellW, j * cellH))
                        }
                    }
                    val path = Path().apply {
                        moveTo(width / 2f, height * 0.25f)
                        lineTo(width * 0.25f, height * 0.75f)
                        lineTo(width * 0.75f, height * 0.75f)
                        close()
                    }
                    drawPath(
                        path = path,
                        color = Color.White.copy(alpha = 0.2f)
                    )
                }
                ArtType.GREEN_WAVE -> {
                    // Derin Mavi: Yeşil gradyan + dairesel dalgalar
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF7CB868), Color(0xFF3F6D31))
                        )
                    )
                    val waveColor = Color.White.copy(alpha = 0.15f)
                    val stroke = Stroke(width = 3.dp.toPx())
                    drawCircle(color = waveColor, radius = width * 0.3f, center = Offset(width, height), style = stroke)
                    drawCircle(color = waveColor, radius = width * 0.5f, center = Offset(width, height), style = stroke)
                    drawCircle(color = waveColor, radius = width * 0.7f, center = Offset(width, height), style = stroke)
                    drawCircle(color = waveColor, radius = width * 0.9f, center = Offset(width, height), style = stroke)
                }
                ArtType.BLUE_WAVE -> {
                    // Yaz Anıları: Mavi gradyan + dairesel dalgalar
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF468694), Color(0xFF244850))
                        )
                    )
                    val waveColor = Color.White.copy(alpha = 0.15f)
                    val stroke = Stroke(width = 3.dp.toPx())
                    drawCircle(color = waveColor, radius = width * 0.3f, center = Offset(0f, 0f), style = stroke)
                    drawCircle(color = waveColor, radius = width * 0.5f, center = Offset(0f, 0f), style = stroke)
                    drawCircle(color = waveColor, radius = width * 0.7f, center = Offset(0f, 0f), style = stroke)
                    drawCircle(color = waveColor, radius = width * 0.9f, center = Offset(0f, 0f), style = stroke)
                }
                ArtType.LIGHT_BLUE_WAVE -> {
                    // Yıldız Tozu: Açık mavi gradyan + dairesel dalgalar
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF67B0B8), Color(0xFF3C7278))
                        )
                    )
                    val waveColor = Color.White.copy(alpha = 0.15f)
                    val stroke = Stroke(width = 3.dp.toPx())
                    drawCircle(color = waveColor, radius = width * 0.4f, center = Offset(width / 2f, height), style = stroke)
                    drawCircle(color = waveColor, radius = width * 0.6f, center = Offset(width / 2f, height), style = stroke)
                    drawCircle(color = waveColor, radius = width * 0.8f, center = Offset(width / 2f, height), style = stroke)
                }
                ArtType.PURPLE_TRIANGLE -> {
                    // Çalma Listesi 2: Mor gradyan + nokta matrisi + büyük üçgen
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF7763C4), Color(0xFF4C3C8C))
                        )
                    )
                    val dotColor = Color.White.copy(alpha = 0.15f)
                    val cols = 8
                    val rows = 8
                    val cellW = width / cols
                    val cellH = height / rows
                    for (i in 1 until cols) {
                        for (j in 1 until rows) {
                            drawCircle(color = dotColor, radius = 2.dp.toPx(), center = Offset(i * cellW, j * cellH))
                        }
                    }
                    val path = Path().apply {
                        moveTo(width / 2f, height * 0.2f)
                        lineTo(width * 0.2f, height * 0.8f)
                        lineTo(width * 0.8f, height * 0.8f)
                        close()
                    }
                    drawPath(
                        path = path,
                        color = Color.White.copy(alpha = 0.22f)
                    )
                }
                ArtType.DARK_GREEN_DOTS -> {
                    // Çalma Listesi 3: Koyu yeşil gradyan + yoğun nokta matrisi
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF2D6A4F), Color(0xFF1B4332))
                        )
                    )
                    val dotColor = Color.White.copy(alpha = 0.2f)
                    val cols = 10
                    val rows = 10
                    val cellW = width / cols
                    val cellH = height / rows
                    for (i in 1 until cols) {
                        for (j in 1 until rows) {
                            drawCircle(color = dotColor, radius = 2.5.dp.toPx(), center = Offset(i * cellW, j * cellH))
                        }
                    }
                }
            }
        }
    }
}

/**
 * Tasarımdaki gibi pembe tonlu arka plana ve kapsül seçiciye sahip özel alt navigasyon çubuğu.
 */
@Composable
fun LyraHomeBottomNavigation(
    selectedRoute: String,
    onRouteSelected: (String) -> Unit,
) {
    val containerBg = MaterialTheme.colorScheme.surface
    val selectedContainerBg = MaterialTheme.colorScheme.primaryContainer
    val selectedColor = MaterialTheme.colorScheme.primary
    val unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant

    NavigationBar(
        containerColor = containerBg,
        modifier = Modifier.navigationBarsPadding(),
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = selectedRoute == "home",
            onClick = { onRouteSelected("home") },
            icon = {
                Icon(
                    imageVector = if (selectedRoute == "home") Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = null
                )
            },
            label = { Text("Ana sayfa") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = selectedColor,
                selectedTextColor = selectedColor,
                unselectedIconColor = unselectedColor,
                unselectedTextColor = unselectedColor,
                indicatorColor = selectedContainerBg
            )
        )
        NavigationBarItem(
            selected = selectedRoute == "search",
            onClick = { onRouteSelected("search") },
            icon = {
                Icon(
                    imageVector = if (selectedRoute == "search") Icons.Filled.Search else Icons.Outlined.Search,
                    contentDescription = null
                )
            },
            label = { Text("Ara") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = selectedColor,
                selectedTextColor = selectedColor,
                unselectedIconColor = unselectedColor,
                unselectedTextColor = unselectedColor,
                indicatorColor = selectedContainerBg
            )
        )
        NavigationBarItem(
            selected = selectedRoute == "library",
            onClick = { onRouteSelected("library") },
            icon = {
                Icon(
                    imageVector = if (selectedRoute == "library") Icons.Filled.LibraryMusic else Icons.Outlined.LibraryMusic,
                    contentDescription = null
                )
            },
            label = { Text("Kütüphane") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = selectedColor,
                selectedTextColor = selectedColor,
                unselectedIconColor = unselectedColor,
                unselectedTextColor = unselectedColor,
                indicatorColor = selectedContainerBg
            )
        )
        NavigationBarItem(
            selected = selectedRoute == "favorites",
            onClick = { onRouteSelected("favorites") },
            icon = {
                Icon(
                    imageVector = if (selectedRoute == "favorites") Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = null
                )
            },
            label = { Text("Favoriler") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = selectedColor,
                selectedTextColor = selectedColor,
                unselectedIconColor = unselectedColor,
                unselectedTextColor = unselectedColor,
                indicatorColor = selectedContainerBg
            )
        )
        NavigationBarItem(
            selected = selectedRoute == "profile",
            onClick = { onRouteSelected("profile") },
            icon = {
                Icon(
                    imageVector = if (selectedRoute == "profile") Icons.Filled.Person else Icons.Outlined.Person,
                    contentDescription = null
                )
            },
            label = { Text("Profil") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = selectedColor,
                selectedTextColor = selectedColor,
                unselectedIconColor = unselectedColor,
                unselectedTextColor = unselectedColor,
                indicatorColor = selectedContainerBg
            )
        )
    }
}

@Preview(name = "Home Screen - Light Mode", showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenLightPreview() {
    LyraAppTheme(darkTheme = false) {
        HomeScreen(
            state = HomeUiState(
                greeting = "iyi akşamlar",
                quickAccess = listOf(
                    QuickAccessItem("q1", "Gece Sürüşü", ArtType.PURPLE_WAVE),
                    QuickAccessItem("q2", "Sabah Kahvesi", ArtType.BLUE_TRIANGLE),
                    QuickAccessItem("q3", "Neon Sokaklar", ArtType.ORANGE_CIRCLE),
                    QuickAccessItem("q4", "Odaklan", ArtType.TEAL_TRIANGLE),
                    QuickAccessItem("q5", "Derin Mavi", ArtType.GREEN_WAVE),
                    QuickAccessItem("q6", "Yaz Anıları", ArtType.BLUE_WAVE),
                ),
                recentlyPlayed = listOf(
                    RecentlyPlayedItem("r1", "Neon Sokaklar", "Şehir Işıkları", ArtType.ORANGE_CIRCLE),
                    RecentlyPlayedItem("r2", "Derin Mavi", "Okyanus", ArtType.GREEN_WAVE),
                    RecentlyPlayedItem("r3", "Yıldız Tozu", "Polaris", ArtType.LIGHT_BLUE_WAVE),
                ),
                recommendedPlaylists = listOf(
                    RecommendedPlaylist("p1", "Karışık Çalma Listesi 1", "Gece ve yolculuklar için", ArtType.PURPLE_WAVE),
                    RecommendedPlaylist("p2", "Karışık Çalma Listesi 2", "Konsantrasyon ve odaklanma", ArtType.PURPLE_TRIANGLE),
                    RecommendedPlaylist("p3", "Karışık Çalma Listesi 3", "Yaz ve enerji dolu ritimler", ArtType.DARK_GREEN_DOTS),
                )
            ),
            onIntent = {}
        )
    }
}

@Preview(name = "Home Screen - Dark Mode", showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenDarkPreview() {
    LyraAppTheme(darkTheme = true) {
        HomeScreen(
            state = HomeUiState(
                greeting = "iyi akşamlar",
                quickAccess = listOf(
                    QuickAccessItem("q1", "Gece Sürüşü", ArtType.PURPLE_WAVE),
                    QuickAccessItem("q2", "Sabah Kahvesi", ArtType.BLUE_TRIANGLE),
                    QuickAccessItem("q3", "Neon Sokaklar", ArtType.ORANGE_CIRCLE),
                    QuickAccessItem("q4", "Odaklan", ArtType.TEAL_TRIANGLE),
                    QuickAccessItem("q5", "Derin Mavi", ArtType.GREEN_WAVE),
                    QuickAccessItem("q6", "Yaz Anıları", ArtType.BLUE_WAVE),
                ),
                recentlyPlayed = listOf(
                    RecentlyPlayedItem("r1", "Neon Sokaklar", "Şehir Işıkları", ArtType.ORANGE_CIRCLE),
                    RecentlyPlayedItem("r2", "Derin Mavi", "Okyanus", ArtType.GREEN_WAVE),
                    RecentlyPlayedItem("r3", "Yıldız Tozu", "Polaris", ArtType.LIGHT_BLUE_WAVE),
                ),
                recommendedPlaylists = listOf(
                    RecommendedPlaylist("p1", "Karışık Çalma Listesi 1", "Gece ve yolculuklar için", ArtType.PURPLE_WAVE),
                    RecommendedPlaylist("p2", "Karışık Çalma Listesi 2", "Konsantrasyon ve odaklanma", ArtType.PURPLE_TRIANGLE),
                    RecommendedPlaylist("p3", "Karışık Çalma Listesi 3", "Yaz ve enerji dolu ritimler", ArtType.DARK_GREEN_DOTS),
                ),
                isDarkMode = true
            ),
            onIntent = {}
        )
    }
}

package com.flowbytestudio.lyraapp.ui.home

import com.flowbytestudio.lyraapp.data.home.QuickAccessItem
import com.flowbytestudio.lyraapp.data.home.RecentlyPlayedItem
import com.flowbytestudio.lyraapp.data.home.RecommendedPlaylist

/**
 * Ana sayfa (Home) ekranının MVI sözleşmesi.
 */

data class HomeUiState(
    val greeting: String = "",
    val quickAccess: List<QuickAccessItem> = emptyList(),
    val recentlyPlayed: List<RecentlyPlayedItem> = emptyList(),
    val recommendedPlaylists: List<RecommendedPlaylist> = emptyList(),
    val isLoading: Boolean = false,
    val isDarkMode: Boolean = false,
    val selectedNavRoute: String = "home"
)

sealed interface HomeIntent {
    data object LoadData : HomeIntent
    data object ToggleDarkMode : HomeIntent
    data class BottomNavClicked(val route: String) : HomeIntent
    data class QuickAccessClicked(val item: QuickAccessItem) : HomeIntent
    data class RecentlyPlayedClicked(val item: RecentlyPlayedItem) : HomeIntent
    data class RecommendedPlaylistClicked(val item: RecommendedPlaylist) : HomeIntent
    data object SeeAllRecentlyPlayed : HomeIntent
    data object ProfileClicked : HomeIntent
}

sealed interface HomeEffect {
    data class ShowToast(val message: String) : HomeEffect
    data class NavigateToDetail(val id: String, val title: String) : HomeEffect
    data object ShowProfile : HomeEffect
}

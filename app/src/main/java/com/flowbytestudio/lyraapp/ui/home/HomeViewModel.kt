package com.flowbytestudio.lyraapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flowbytestudio.lyraapp.data.home.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Ana sayfa (Home) ekranı için MVI tabanlı ViewModel sınıfı.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _effect = Channel<HomeEffect>(Channel.BUFFERED)
    val effect: Flow<HomeEffect> = _effect.receiveAsFlow()

    init {
        onIntent(HomeIntent.LoadData)
    }

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadData -> loadHomeData()
            is HomeIntent.ToggleDarkMode -> toggleDarkMode()
            is HomeIntent.BottomNavClicked -> updateSelectedRoute(intent.route)
            is HomeIntent.QuickAccessClicked -> handleQuickAccessClick(intent)
            is HomeIntent.RecentlyPlayedClicked -> handleRecentlyPlayedClick(intent)
            is HomeIntent.RecommendedPlaylistClicked -> handleRecommendedPlaylistClick(intent)
            is HomeIntent.SeeAllRecentlyPlayed -> sendEffect(HomeEffect.ShowToast("Son çalınanların tümü listelenecek."))
            is HomeIntent.ProfileClicked -> sendEffect(HomeEffect.ShowProfile)
        }
    }

    private fun loadHomeData() {
        if (_uiState.value.isLoading) return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = homeRepository.getHomeData()
            _uiState.update { it.copy(isLoading = false) }

            result.onSuccess { data ->
                _uiState.update {
                    it.copy(
                        greeting = data.greeting,
                        quickAccess = data.quickAccess,
                        recentlyPlayed = data.recentlyPlayed,
                        recommendedPlaylists = data.recommendedPlaylists
                    )
                }
            }.onFailure { error ->
                sendEffect(HomeEffect.ShowToast(error.message ?: "Veriler yüklenirken bir hata oluştu."))
            }
        }
    }

    private fun toggleDarkMode() {
        _uiState.update { it.copy(isDarkMode = !it.isDarkMode) }
        val currentTheme = if (_uiState.value.isDarkMode) "Karanlık Tema" else "Aydınlık Tema"
        sendEffect(HomeEffect.ShowToast("$currentTheme aktif edildi."))
    }

    private fun updateSelectedRoute(route: String) {
        _uiState.update { it.copy(selectedNavRoute = route) }
        sendEffect(HomeEffect.ShowToast("Navigasyon: $route"))
    }

    private fun handleQuickAccessClick(intent: HomeIntent.QuickAccessClicked) {
        sendEffect(HomeEffect.ShowToast("Oynatılıyor: ${intent.item.title}"))
        sendEffect(HomeEffect.NavigateToDetail(intent.item.id, intent.item.title))
    }

    private fun handleRecentlyPlayedClick(intent: HomeIntent.RecentlyPlayedClicked) {
        sendEffect(HomeEffect.ShowToast("Seçildi: ${intent.item.title}"))
        sendEffect(HomeEffect.NavigateToDetail(intent.item.id, intent.item.title))
    }

    private fun handleRecommendedPlaylistClick(intent: HomeIntent.RecommendedPlaylistClicked) {
        sendEffect(HomeEffect.ShowToast("Açılıyor: ${intent.item.title}"))
        sendEffect(HomeEffect.NavigateToDetail(intent.item.id, intent.item.title))
    }

    private fun sendEffect(effect: HomeEffect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
}

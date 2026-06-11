package com.flowbytestudio.lyraapp.data.home

/**
 * Albüm kapaklarının dinamik Canvas çizimi için kullanılacak görsel tipler.
 */
enum class ArtType {
    PURPLE_WAVE,      // Gece Sürüşü
    BLUE_TRIANGLE,    // Sabah Kahvesi
    ORANGE_CIRCLE,    // Neon Sokaklar
    TEAL_TRIANGLE,    // Odaklan
    GREEN_WAVE,       // Derin Mavi
    BLUE_WAVE,        // Yaz Anıları
    LIGHT_BLUE_WAVE,  // Yıldız Tozu
    PURPLE_TRIANGLE,  // Senin İçin Çalma Listeleri 2
    DARK_GREEN_DOTS   // Senin İçin Çalma Listeleri 3
}

/**
 * Hızlı erişim ızgarasındaki (Quick Access Grid) her bir öğe.
 */
data class QuickAccessItem(
    val id: String,
    val title: String,
    val artType: ArtType,
)

/**
 * Son çalınanlar listesindeki her bir öğe.
 */
data class RecentlyPlayedItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val artType: ArtType,
)

/**
 * Kullanıcıya özel tavsiye edilen çalma listeleri.
 */
data class RecommendedPlaylist(
    val id: String,
    val title: String,
    val subtitle: String,
    val artType: ArtType,
)

/**
 * Ana sayfa içeriğinin tamamını sarmalayan veri modeli.
 */
data class HomeData(
    val greeting: String,
    val quickAccess: List<QuickAccessItem>,
    val recentlyPlayed: List<RecentlyPlayedItem>,
    val recommendedPlaylists: List<RecommendedPlaylist>,
)

package com.flowbytestudio.lyraapp.data.home

import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * [HomeRepository] arayüzünün sahte (stub) implementasyonu.
 *
 * Kullanıcı arayüzünde gösterilmek üzere tasarımdaki verileri taklit eden mock veriler sağlar.
 * 500 ms'lik bir ağ gecikmesi taklit eder.
 */
class FakeHomeRepository @Inject constructor() : HomeRepository {

    override suspend fun getHomeData(): Result<HomeData> {
        delay(NETWORK_DELAY_MS)
        return Result.success(
            HomeData(
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
            )
        )
    }

    private companion object {
        const val NETWORK_DELAY_MS = 500L
    }
}

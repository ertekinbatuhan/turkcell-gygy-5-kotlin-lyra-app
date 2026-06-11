package com.flowbytestudio.lyraapp.data.home

/**
 * Ana sayfa içeriklerini (Hızlı erişim, son çalınanlar ve çalma listeleri) getiren veri deposu arayüzü.
 */
interface HomeRepository {

    /**
     * Ana sayfa için gerekli tüm verileri asenkron olarak döner.
     *
     * @return Başarılıysa [Result.success] içerisinde [HomeData], hata durumunda [Result.failure].
     */
    suspend fun getHomeData(): Result<HomeData>
}

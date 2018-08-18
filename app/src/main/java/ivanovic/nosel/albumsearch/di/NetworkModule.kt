package ivanovic.nosel.albumsearch.di

import dagger.Module
import dagger.Provides
import ivanovic.nosel.albumsearch.data.SearchApi
import ivanovic.nosel.albumsearch.data.iTunesBaseUrl
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
object NetworkModule {
    
    @Provides
    @JvmStatic
    fun providesItunesApi(): SearchApi {
        return Retrofit.Builder()
                .baseUrl(iTunesBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(SearchApi::class.java)
    }
}
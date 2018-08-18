package ivanovic.nosel.albumsearch.data

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

const val iTunesBaseUrl = "https://itunes.apple.com/"

interface SearchApi {

    @GET("search")
    fun search(@Query("term") term: String,
               @Query("country") country: String = "CA",
               @Query("media") media: String = "music",
               @Query("entity") entity: String = "album"): Single<AlbumSearch>
}

data class AlbumSearch(val results: List<AlbumResult>)

data class AlbumResult(val releaseDate: String?,
                  val collectionName: String?, val artworkUrl100: String?,
                  val primaryGenreName: String?,
                  val collectionPrice: Double?, val currency: String?, val copyright: String?)
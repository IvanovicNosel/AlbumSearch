package ivanovic.nosel.albumsearch.domain

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Observable.just
import ivanovic.nosel.albumsearch.data.AlbumRepository
import ivanovic.nosel.albumsearch.data.AlbumResult
import java.net.URLEncoder
import javax.inject.Inject

class AlbumSearchInteractor @Inject constructor(private val albumRepository: AlbumRepository) {
    fun search(searchTerm: String): Observable<List<AlbumResult>> {
        val term = searchTerm.encodeSearchTerm()
        return albumRepository.get(term)
                .flatMap {
                    Log.d("TEST","Interactor.search: $it")
                    fetchIfEmpty(it, term).andThen(just(it))
                }
    }

    private fun fetchIfEmpty(it: List<AlbumResult>, term: String): Completable {
        return if (it.isEmpty()) {
            albumRepository.fetch(term)
        } else {
            Completable.complete()
        }
    }
}

fun String.encodeSearchTerm(): String = URLEncoder.encode(trim().toLowerCase(), "UTF-8")
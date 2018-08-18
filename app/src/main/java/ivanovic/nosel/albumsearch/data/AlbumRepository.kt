package ivanovic.nosel.albumsearch.data

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

interface Repository<in Key, Value> {
    fun get(term: Key): Observable<Value>
    fun fetch(term: Key): Completable
}

class AlbumRepository @Inject
constructor(private val api: SearchApi,
            private val albumStore: AlbumStore)
    : Repository<String, List<AlbumResult>> {

    override fun get(term: String): Observable<List<AlbumResult>> = albumStore.get(term)

    override fun fetch(term: String): Completable {
        return api.search(term)
                .doOnSuccess { Log.d("TEST", "AlbumRepository fetch: $it") }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map { it.results }
                .filter { it.isNotEmpty() }
                .doOnSuccess {
                    Log.d("TEST", "AlbumRepository saving: $term")
                    albumStore.save(term, it)
                }
                .ignoreElement()
    }
}


package ivanovic.nosel.albumsearch.data

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
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
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map { it.results }
                .filter { it.isNotEmpty() }
                .doOnSuccess {albumStore.save(term, it)}
                .ignoreElement()
    }
}


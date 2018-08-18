package ivanovic.nosel.albumsearch.data

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

interface Store<Key, Value> {
    fun save(key: Key, value: Value)
    fun get(key: Key): Observable<Value>
}

interface AlbumStore : Store<String, List<AlbumResult>>

class InMemoryAlbumStore @Inject constructor() : AlbumStore {

    override fun get(key: String): Observable<List<AlbumResult>> {
        return Observable.defer { getCachedValueOrDefault(key) }
                .observeOn(Schedulers.computation())
    }

    private val publishSubject: PublishSubject<List<AlbumResult>> = PublishSubject.create()

    private val cache = HashMap<String, List<AlbumResult>>()
    override fun save(key: String, value: List<AlbumResult>) {
        cache[key] = value
        publishSubject.onNext(value)
    }

    private fun getCachedValueOrDefault(key: String): Observable<List<AlbumResult>> {
        val list = if (!cache.containsKey(key)) {
            emptyList()
        } else {
            cache[key]
        }
        return publishSubject.startWith(list)
    }
}
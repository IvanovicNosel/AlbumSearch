package ivanovic.nosel.albumsearch.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers.io
import ivanovic.nosel.albumsearch.BuildConfig
import ivanovic.nosel.albumsearch.data.AlbumResult
import ivanovic.nosel.albumsearch.domain.AlbumSearchInteractor
import javax.inject.Inject

class AlbumListViewModel @Inject
constructor(private val searchInteractor: AlbumSearchInteractor)
    : ViewModel() {

    private val disposables = CompositeDisposable()
    val albumListData = MutableLiveData<List<Album>>()

    fun bindQueryObservable(queryObservable: Observable<String>) {
        disposables.add(queryObservable
                .flatMap {
                    searchInteractor.search(it)
                }
                .map {
                    it.toAlbumList()
                }
                .subscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    albumListData.postValue(it)
                }, {
                    if (BuildConfig.DEBUG) Log.e("AlbumListViewModel", it.message, it)
                })
        )
    }

    override fun onCleared() {
        disposables.clear()
    }
}

const val NO_DATA = "No data available"

fun List<AlbumResult>.toAlbumList(): List<Album> {
    return map { it.toAlbum() }
}

fun AlbumResult.toAlbum(): Album {
    val formatString: (data: String?) -> String = { it ?: NO_DATA }
    val formatPrice: (data: Double?) -> String? = { if (it != null) "$$it" else it }
    return Album(AlbumSummary(
            title = formatString.invoke(collectionName),
            artWorkUrl = formatString.invoke(artworkUrl100),
            date = formatString.invoke(releaseDate)),
            AlbumDetail(
                    genre = formatString.invoke(primaryGenreName),
                    price = formatString.invoke(formatPrice.invoke(collectionPrice)),
                    currency = formatString.invoke(currency),
                    copyright = formatString.invoke(copyright)
            )
    )
}

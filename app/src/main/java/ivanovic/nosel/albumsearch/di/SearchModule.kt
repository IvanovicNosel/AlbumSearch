package ivanovic.nosel.albumsearch.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import ivanovic.nosel.albumsearch.data.AlbumResult
import ivanovic.nosel.albumsearch.data.AlbumStore
import ivanovic.nosel.albumsearch.data.InMemoryAlbumStore
import ivanovic.nosel.albumsearch.data.Store
import ivanovic.nosel.albumsearch.domain.AlbumSearchInteractor
import ivanovic.nosel.albumsearch.ui.AlbumListViewModel

@Module
interface SearchModule {

    @Binds
    fun bindsAlbumStore(store: InMemoryAlbumStore): AlbumStore

    @Module
    object ProvidesModule {
        @Provides
        @JvmStatic
        fun providesSearchViewModel(albumRepository: AlbumSearchInteractor): AlbumListViewModel {
            return AlbumListViewModel(albumRepository)
        }

    }
}

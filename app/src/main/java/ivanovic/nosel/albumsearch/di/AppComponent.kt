package ivanovic.nosel.albumsearch.di

import android.app.Application
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import ivanovic.nosel.albumsearch.AlbumSearchApp
import dagger.BindsInstance

@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBindings::class,
    NetworkModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }

    fun inject(app: AlbumSearchApp)
}
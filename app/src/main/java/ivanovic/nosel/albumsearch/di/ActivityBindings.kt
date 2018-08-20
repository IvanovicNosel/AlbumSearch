package ivanovic.nosel.albumsearch.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ivanovic.nosel.albumsearch.ui.SearchActivity

@Module
interface ActivityBindings {

    @ContributesAndroidInjector(modules = [SearchModule::class])
    fun bindsSearchActivity(): SearchActivity
}
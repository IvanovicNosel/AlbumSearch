package ivanovic.nosel.albumsearch

import android.app.Activity
import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import ivanovic.nosel.albumsearch.di.DaggerAppComponent
import javax.inject.Inject

class AlbumSearchApp : Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        DaggerAppComponent.builder().application(this).build().inject(this)
    }
}

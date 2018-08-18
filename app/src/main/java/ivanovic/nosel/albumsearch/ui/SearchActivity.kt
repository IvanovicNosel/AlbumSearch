package ivanovic.nosel.albumsearch.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import dagger.android.AndroidInjection
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import ivanovic.nosel.albumsearch.R
import javax.inject.Inject

class SearchActivity : AppCompatActivity(), Observer<List<Album>> {

    @Inject
    lateinit var albumListViewModel: AlbumListViewModel

    private val adapter = AlbumAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_search)

        val recyclerView = findViewById<RecyclerView>(R.id.search_results)
        recyclerView.adapter = adapter

        albumListViewModel.albumListData.observe(this, this)
    }

    override fun onChanged(updatedList: List<Album>?) {
        if (updatedList != null && updatedList.isNotEmpty()) {
            adapter.albumList = updatedList
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val actionView = searchItem?.actionView

        if (actionView is SearchView) {
            setupSearchView(actionView)
        }

        return true
    }

    private fun setupSearchView(actionView: SearchView) {
        val queryObservable = Observable
                .create { emitter: ObservableEmitter<String> ->
                    actionView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            if (query != null) {
                                emitter.onNext(query)
                            }
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean = false
                    })
                }
        albumListViewModel.bindQueryObservable(queryObservable)
    }
}


package ivanovic.nosel.albumsearch.ui

import android.net.Uri

data class Album(val summary: AlbumSummary, val detail: AlbumDetail)
data class AlbumSummary(val title: String, val artWorkUrl: String, val date: String)
data class AlbumDetail(val genre: String, val price: String, val currency: String, val copyright: String)
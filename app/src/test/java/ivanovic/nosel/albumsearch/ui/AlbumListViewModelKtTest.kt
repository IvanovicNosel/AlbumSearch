package ivanovic.nosel.albumsearch.ui

import ivanovic.nosel.albumsearch.data.AlbumResult
import org.junit.Assert.assertEquals
import org.junit.Test

class AlbumListViewModelKtTest {


    @Test
    fun shouldSetNoDataStringWhenPropertyIsNull() {

        val input = AlbumResult(releaseDate = null, copyright = null, currency = null,
                artworkUrl100 = null, collectionName = null, collectionPrice = null,
                primaryGenreName = null)

        val expected = Album(
                AlbumSummary(NO_DATA, NO_DATA, NO_DATA),
                AlbumDetail(NO_DATA, NO_DATA, NO_DATA, NO_DATA))

        val actual = input.toAlbum()

        assertEquals(expected, actual)

    }
}
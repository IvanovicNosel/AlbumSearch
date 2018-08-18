package ivanovic.nosel.albumsearch.domain

import org.junit.Assert.*
import org.junit.Test

class SearchInteractorKtTest {

    @Test
    fun shouldReplaceSpacesWithPlusSigns() {
        val input = "Michael Jackson"
        val expected = "michael+jackson"
        val actual = input.encodeSearchTerm()
        assertEquals(expected, actual)
    }
}
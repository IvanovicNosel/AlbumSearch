package ivanovic.nosel.albumsearch.data

import io.reactivex.Single
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class AlbumRepositoryTest {

    @Mock
    private
    lateinit var api: SearchApi

    private val store = InMemoryAlbumStore()

    private lateinit var repository: AlbumRepository

    @Before
    fun setUp() {
        repository = AlbumRepository(api, store)

    }

    @Test
    fun get() {
        //Arrange
        val element = AlbumResult("", "", "", "", 0.0, "", "")
        val results = listOf(element)
        `when`(api.search(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Single.just(AlbumSearch(results)))

        //Act
        val testObserver = repository.get("test").test()

        //Assert
        testObserver
                .awaitDone(1, TimeUnit.MILLISECONDS)
                .assertNotComplete()
                .assertValueCount(1)

    }
}
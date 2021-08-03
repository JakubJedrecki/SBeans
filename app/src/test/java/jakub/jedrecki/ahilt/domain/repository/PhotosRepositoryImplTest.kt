package jakub.jedrecki.ahilt.domain.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import jakub.jedrecki.ahilt.domain.model.PhotoItem
import jakub.jedrecki.ahilt.network.RetrofitService
import jakub.jedrecki.ahilt.util.PhotosMock
import jakub.jedrecki.ahilt.util.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class PhotosRepositoryImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    private val dispatcher = TestCoroutineDispatcher()

    @RelaxedMockK
    private lateinit var retrofitService: RetrofitService

    private lateinit var photosRepository: PhotosRepository

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)
        photosRepository = PhotosRepositoryImpl(retrofitService)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getPhotos success`() = runBlockingTest {
        coEvery { retrofitService.getPhotosList() } returns Response.success(PhotosMock.photosList)

        val outcome = photosRepository.getPhotos()

        Truth.assertThat(outcome.status).isEqualTo(Status.SUCCESS)
        Truth.assertThat(outcome.data).isNotNull()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getPhotos error`() = runBlockingTest {
        val mockResponse = mockk<Response<List<PhotoItem>>>(relaxed = true)
        coEvery { retrofitService.getPhotosList() } returns mockResponse

        val outcome = photosRepository.getPhotos()

        Truth.assertThat(outcome.status).isEqualTo(Status.ERROR)
        Truth.assertThat(outcome.data).isNull()
    }
}
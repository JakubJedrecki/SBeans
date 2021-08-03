package jakub.jedrecki.ahilt.ui.photos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import jakub.jedrecki.ahilt.domain.errors.GetPhotosError
import jakub.jedrecki.ahilt.domain.repository.PhotosRepository
import jakub.jedrecki.ahilt.domain.repository.PhotosRepositoryImpl
import jakub.jedrecki.ahilt.util.IdResourceString
import jakub.jedrecki.ahilt.util.Outcome
import jakub.jedrecki.ahilt.util.PhotosMock
import jakub.jedrecki.ahilt.util.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PhotosViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    private val dispatcher = TestCoroutineDispatcher()

    @RelaxedMockK
    private lateinit var photosRepository: PhotosRepository

    private lateinit var photosViewModel: PhotosViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)
        photosViewModel = PhotosViewModel(photosRepository)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getPhotos error`() = runBlockingTest {
        coEvery { photosRepository.getPhotos() } returns Outcome.error(GetPhotosError.DataError(5))

        photosViewModel.getPhotos()

        val photos = photosViewModel.photos.getOrAwaitValue()
        val errorMsg = photosViewModel.snackBarMsg.getOrAwaitValue()

        Truth.assertThat(photos.size).isEqualTo(0)
        Truth.assertThat(errorMsg).isNotNull()
        Truth.assertThat(errorMsg).isInstanceOf(IdResourceString::class.java)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getPhotos success`() = runBlockingTest {
        coEvery { photosRepository.getPhotos() } returns Outcome.success(PhotosMock.photosList)

        photosViewModel.getPhotos()

        val photos = photosViewModel.photos.getOrAwaitValue()

        Truth.assertThat(photos).isNotNull()
        Truth.assertThat(photos).isNotEmpty()
        Truth.assertThat(photos.size).isEqualTo(4)
    }
}
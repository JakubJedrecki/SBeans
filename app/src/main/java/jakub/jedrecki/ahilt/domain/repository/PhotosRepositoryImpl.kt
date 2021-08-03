package jakub.jedrecki.ahilt.domain.repository

import jakub.jedrecki.ahilt.R
import jakub.jedrecki.ahilt.domain.errors.GetPhotosError
import jakub.jedrecki.ahilt.domain.model.PhotoItem
import jakub.jedrecki.ahilt.network.RetrofitService
import jakub.jedrecki.ahilt.util.Outcome
import java.net.UnknownHostException
import javax.inject.Inject

class PhotosRepositoryImpl @Inject constructor(private val retrofitService: RetrofitService) :
    PhotosRepository {

    override suspend fun getPhotos(): Outcome<List<PhotoItem>, GetPhotosError> {
        return try {
            val response = retrofitService.getPhotosList()

            if (response.isSuccessful && response.body() != null) {
                Outcome.success(response.body()!!)
            } else {
                Outcome.error(GetPhotosError.DataError(R.string.error_photos_data))
            }
        } catch (e: UnknownHostException) {
            e.printStackTrace()
            Outcome.error(GetPhotosError.NetworkError(R.string.error_network))
        } catch (exception: Exception) {
            exception.printStackTrace()
            Outcome.error(GetPhotosError.OtherError(R.string.error_photos_data))
        }
    }
}
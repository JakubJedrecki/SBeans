package jakub.jedrecki.ahilt.domain.repository

import jakub.jedrecki.ahilt.domain.errors.GetPhotosError
import jakub.jedrecki.ahilt.domain.model.PhotoItem
import jakub.jedrecki.ahilt.util.Outcome

interface PhotosRepository {
    suspend fun getPhotos(): Outcome<List<PhotoItem>, GetPhotosError>
}
package jakub.jedrecki.ahilt.network

import jakub.jedrecki.ahilt.domain.model.PhotoItem
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitService {

    @GET("photos")
    suspend fun getPhotosList(): Response<List<PhotoItem>>
}
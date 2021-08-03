package jakub.jedrecki.ahilt.ui.photos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakub.jedrecki.ahilt.domain.errors.GetPhotosError
import jakub.jedrecki.ahilt.domain.model.PhotoItem
import jakub.jedrecki.ahilt.domain.repository.PhotosRepository
import jakub.jedrecki.ahilt.util.IdResourceString
import jakub.jedrecki.ahilt.util.ResourceString
import jakub.jedrecki.ahilt.util.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val photosRepository: PhotosRepository
) : ViewModel() {

    private val _snackBarMsg: MutableLiveData<ResourceString> = MutableLiveData()
    private val _photos: MutableLiveData<List<PhotoItem>> = MutableLiveData(emptyList())

    val snackBarMsg: LiveData<ResourceString> = _snackBarMsg
    val photos: LiveData<List<PhotoItem>> = _photos

    fun getPhotos() {
        viewModelScope.launch {
            val outcome = photosRepository.getPhotos()

            when (outcome.status) {
                Status.SUCCESS -> {
                    _photos.postValue(outcome.data)
                }
                Status.ERROR -> {
                    when (outcome.error) {
                        is GetPhotosError.NetworkError -> {
//                            Log.e("TAG", "GetPhotos_NetworkError: ${outcome.error}")
                            _snackBarMsg.postValue(IdResourceString(outcome.error.messageResource))
                        }
                        is GetPhotosError.AuthenticationError -> {
//                            Log.e("TAG", "GetPhotos_AuthError: ${outcome.error}")
                            _snackBarMsg.postValue(IdResourceString(outcome.error.messageResource))
                        }
                        is GetPhotosError.DataError -> {
//                            Log.e("TAG", "GetPhotos_DataError: ${outcome.error}")
                            _snackBarMsg.postValue(IdResourceString(outcome.error.messageResource))
                        }
                    }
                }
            }
        }
    }
}
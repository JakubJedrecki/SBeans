package jakub.jedrecki.ahilt.domain.errors

sealed class GetPhotosError {
    class AuthenticationError(val messageResource: Int): GetPhotosError()
    class NetworkError(val messageResource: Int): GetPhotosError()
    class OtherError(val messageResource: Int): GetPhotosError()
    class DataError(val messageResource: Int): GetPhotosError()
}

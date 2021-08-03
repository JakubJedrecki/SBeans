package jakub.jedrecki.ahilt.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakub.jedrecki.ahilt.domain.repository.PhotosRepository
import jakub.jedrecki.ahilt.domain.repository.PhotosRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindsPhotosRepository(photosRepositoryImpl: PhotosRepositoryImpl): PhotosRepository
}
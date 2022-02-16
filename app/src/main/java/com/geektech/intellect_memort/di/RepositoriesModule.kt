package com.geektech.intellect_memort.di

import com.geektech.intellect_memort.data.repositories.PictureRepositoryImpl
import com.geektech.intellect_memort.domain.repositories.PictureRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Provides
    fun providePictureRepository(
        fireStore: FirebaseFirestore,
    ): PictureRepository =
        PictureRepositoryImpl(fireStore)
}
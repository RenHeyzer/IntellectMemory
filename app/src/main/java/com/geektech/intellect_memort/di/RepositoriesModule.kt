package com.geektech.intellect_memort.di

import com.geektech.intellect_memort.data.repositories.CreateStudentsRepositoryImpl
import com.geektech.intellect_memort.data.repositories.SignRepositoryImpl
import com.geektech.intellect_memort.domain.repositories.CreateStudentsRepository
import com.geektech.intellect_memort.domain.repositories.SignRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Provides
    fun provideSignInRepository(fireStore: FirebaseFirestore): SignRepository =
        SignRepositoryImpl(fireStore)

    @Provides
    fun provideCreateStudentsRepository(fireStore: FirebaseFirestore): CreateStudentsRepository =
        CreateStudentsRepositoryImpl(fireStore)
}
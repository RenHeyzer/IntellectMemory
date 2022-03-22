package com.geektech.intellect_memort.di

import com.geektech.intellect_memort.data.repositories.*
import com.geektech.intellect_memort.domain.repositories.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun provideRandomNumbersRepository(
        repositoryImpl: RandomNumbersRepositoryImpl,
    ): RandomNumbersRepository

    @Binds
    abstract fun provideAnswerRandomNumbersRepository(
        repositoryImpl: AnswerRandomNumbersRepositoryImpl,
    ): AnswerRandomNumbersRepository

    @Binds
    abstract fun provideSignInRepository(repositoryImpl: SignRepositoryImpl): SignRepository

    @Binds
    abstract fun provideCreateStudentsRepository(
        repositoryImpl: CreateStudentsRepositoryImpl,
    ): CreateStudentsRepository

    @Binds
    abstract fun providePictureRepository(
        repositoryImpl: PictureRepositoryImpl
    ): PictureRepository
}
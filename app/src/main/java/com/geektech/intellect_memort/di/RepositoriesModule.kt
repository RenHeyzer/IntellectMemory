package com.geektech.intellect_memort.di

import com.geektech.intellect_memort.data.repositories.AnswerRandomNumbersRepositoryImpl
import com.geektech.intellect_memort.data.repositories.CreateStudentsRepositoryImpl
import com.geektech.intellect_memort.data.repositories.PictureRepositoryImpl
import com.geektech.intellect_memort.data.repositories.RandomNumbersRepositoryImpl
import com.geektech.intellect_memort.data.repositories.SignRepositoryImpl
import com.geektech.intellect_memort.domain.repositories.AnswerRandomNumbersRepository
import com.geektech.intellect_memort.domain.repositories.CreateStudentsRepository
import com.geektech.intellect_memort.domain.repositories.PictureRepository
import com.geektech.intellect_memort.domain.repositories.RandomNumbersRepository
import com.geektech.intellect_memort.domain.repositories.SignRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.geektech.intellect_memort.data.repositories.AnswerRandomNumbersRepositoryImpl
import com.geektech.intellect_memort.data.repositories.CreateStudentsRepositoryImpl
import com.geektech.intellect_memort.data.repositories.RandomNumbersRepositoryImpl
import com.geektech.intellect_memort.data.repositories.SignRepositoryImpl
import com.geektech.intellect_memort.domain.repositories.AnswerRandomNumbersRepository
import com.geektech.intellect_memort.domain.repositories.CreateStudentsRepository
import com.geektech.intellect_memort.domain.repositories.RandomNumbersRepository
import com.geektech.intellect_memort.domain.repositories.SignRepository

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
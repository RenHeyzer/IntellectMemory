package com.geektech.intellect_memory.di

import com.geektech.intellect_memory.data.repositories.*
import com.geektech.intellect_memory.domain.repositories.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun provideRandomNumbersRepository(
        repositoryImpl: NumbersRepositoryImpl,
    ): NumbersRepository

    @Binds
    abstract fun provideAnswerRandomNumbersRepository(
        repositoryImpl: AnswerNumbersRepositoryImpl,
    ): AnswerRandomRepository

    @Binds
    abstract fun provideSignInRepository(
        repositoryImpl: SignRepositoryImpl,
    ): SignRepository

    @Binds
    abstract fun provideCreateStudentsRepository(
        repositoryImpl: CreateStudentsRepositoryImpl,
    ): CreateStudentsRepository

    @Binds
    abstract fun provideCardsRepository(
        repositoryImpl: CardsRepositoryImpl
    ): CardsRepository

    @Binds
    abstract fun providePictureRepository(
        repositoryImpl: PictureRepositoryImpl,
    ): PictureRepository

    @Binds
    abstract fun provideResultsRepository(
        repositoryImpl: ResultsRepositoryImpl,
    ): ResultsRepository
}
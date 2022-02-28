package com.geektech.intellect_memort.domain.usecases

import com.geektech.intellect_memort.domain.repositories.SignRepository
import javax.inject.Inject

class SignInStudentUseCase @Inject constructor(
    private val repository: SignRepository,
) {

    operator fun invoke() = repository.signInStudent()
}
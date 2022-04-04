package com.geektech.intellect_memort.domain.usecases

import com.geektech.intellect_memort.domain.repositories.SignRepository
import javax.inject.Inject

class SignInAdminUseCase @Inject constructor(
    private val repository: SignRepository,
) {

    operator fun invoke() = repository.signInAdmin()
}
package com.geektech.intellect_memory.domain.usecases

import com.geektech.intellect_memory.domain.repositories.SignRepository
import javax.inject.Inject

class SignInAdminUseCase @Inject constructor(
    private val repository: SignRepository,
) {

    operator fun invoke() = repository.signInAdmin()
}
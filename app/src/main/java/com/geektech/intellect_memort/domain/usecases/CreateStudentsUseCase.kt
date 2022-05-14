package com.geektech.intellect_memort.domain.usecases

import com.geektech.intellect_memort.domain.repositories.CreateStudentsRepository
import javax.inject.Inject

class CreateStudentsUseCase @Inject constructor(
    private val repository: CreateStudentsRepository,
) {

    suspend fun execute(student: HashMap<String, Any>, id: String) = repository.createStudent(student, id)
}
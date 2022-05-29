package com.geektech.intellect_memort.domain.repositories

interface CreateStudentsRepository {

    suspend fun createStudent(student: HashMap<String, Any>, id: String): Boolean
}
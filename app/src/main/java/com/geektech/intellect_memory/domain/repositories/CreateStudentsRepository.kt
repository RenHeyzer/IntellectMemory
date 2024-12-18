package com.geektech.intellect_memory.domain.repositories

interface CreateStudentsRepository {

    suspend fun createStudent(student: HashMap<String, Any>, id: String): Boolean
}
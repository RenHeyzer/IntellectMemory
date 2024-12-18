package com.geektech.intellect_memory.domain.repositories

import com.geektech.intellect_memory.common.resource.Resource
import com.geektech.intellect_memory.domain.models.AdminModel
import com.geektech.intellect_memory.domain.models.StudentsModel
import kotlinx.coroutines.flow.Flow

interface SignRepository {

    fun signInAdmin(): Flow<Resource<List<AdminModel?>>>

    fun signInStudent(): Flow<Resource<List<StudentsModel?>>>
}
package com.geektech.intellect_memort.domain.repositories

import com.geektech.intellect_memort.common.resource.Resource
import com.geektech.intellect_memort.domain.models.AdminModel
import com.geektech.intellect_memort.domain.models.StudentsModel
import kotlinx.coroutines.flow.Flow

interface SignRepository {

    fun signInAdmin(): Flow<Resource<List<AdminModel?>>>

    fun signInStudent(): Flow<Resource<List<StudentsModel?>>>
}
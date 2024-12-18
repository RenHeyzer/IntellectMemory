package com.geektech.intellect_memory.data.repositories

import com.geektech.intellect_memory.common.base.BaseRepository
import com.geektech.intellect_memory.common.constants.Constants
import com.geektech.intellect_memory.domain.models.AdminModel
import com.geektech.intellect_memory.domain.models.StudentsModel
import com.geektech.intellect_memory.domain.repositories.SignRepository
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class SignRepositoryImpl @Inject constructor(
    val fireStore: FirebaseFirestore,
) : BaseRepository(), SignRepository {

    private val adminCollection = fireStore.collection(Constants.COLLECTION_ADMINS)
    private val studentsCollection = fireStore.collection(Constants.COLLECTION_STUDENTS)

    override fun signInAdmin() = doRequest {
        fetchList<AdminModel>(adminCollection)
    }

    override fun signInStudent() = doRequest {
        fetchList<StudentsModel>(studentsCollection)
    }
}
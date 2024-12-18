package com.geektech.intellect_memory.data.repositories

import com.geektech.intellect_memory.common.base.BaseRepository
import com.geektech.intellect_memory.common.constants.Constants
import com.geektech.intellect_memory.domain.repositories.CreateStudentsRepository
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class CreateStudentsRepositoryImpl @Inject constructor(
    val fireStore: FirebaseFirestore,
) : BaseRepository(), CreateStudentsRepository {

    private val studentsCollection = fireStore.collection(Constants.COLLECTION_STUDENTS)

    override suspend fun createStudent(student: HashMap<String, Any>, id: String) = addDocument(
        studentsCollection, student, id
    )
}
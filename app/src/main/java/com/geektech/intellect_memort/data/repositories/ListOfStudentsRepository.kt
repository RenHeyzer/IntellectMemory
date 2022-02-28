package com.geektech.intellect_memort.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.geektech.intellect_memort.common.base.BaseRepository
import com.geektech.intellect_memort.common.constants.Constants
import com.geektech.intellect_memort.data.repositories.pagingsources.ListOfStudentsPagingSource
import com.geektech.intellect_memort.domain.models.StudentsModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListOfStudentsRepository @Inject constructor(
    val fireStore: FirebaseFirestore,
) : BaseRepository() {

    val studentsCollection = fireStore.collection(Constants.COLLECTION_STUDENTS)
        .orderBy("points", Query.Direction.DESCENDING)
    private val nextStudentsCollection = fireStore.collection(Constants.COLLECTION_STUDENTS)
        .orderBy("points", Query.Direction.DESCENDING)

    fun getListOfStudents(): Flow<PagingData<StudentsModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1,
                prefetchDistance = 1,
                enablePlaceholders = true,
                initialLoadSize = 2,
                maxSize = Int.MAX_VALUE,
                jumpThreshold = Int.MIN_VALUE
            ),
            pagingSourceFactory = {
                ListOfStudentsPagingSource(
                    studentsCollection,
                    nextStudentsCollection
                )
            }
        ).flow
    }
}
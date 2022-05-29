package com.geektech.intellect_memort.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.geektech.intellect_memort.common.base.BaseRepository
import com.geektech.intellect_memort.common.constants.Constants
import com.geektech.intellect_memort.data.repositories.pagingsources.ListOfStudentsPagingSource
import com.geektech.intellect_memort.domain.models.StudentsModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListOfStudentsRepository @Inject constructor(
    val fireStore: FirebaseFirestore,
) : BaseRepository() {

    val studentsCollection = fireStore.collection(Constants.COLLECTION_STUDENTS)
    private val nextStudentsCollection = fireStore.collection(Constants.COLLECTION_STUDENTS)

    private val queryStudentsCollection =
        fireStore.collection(Constants.COLLECTION_STUDENTS)
    private val queryNextStudentsCollection =
        fireStore.collection(Constants.COLLECTION_STUDENTS)

    private fun notEmptySchoolField(
        school: String?,
        collectionReference: CollectionReference,
    ): Query {
        return if (!school.isNullOrEmpty()) {
            collectionReference.whereEqualTo("branch", school)
                .orderBy("points", Query.Direction.DESCENDING)
        } else {
            collectionReference
                .orderBy("points", Query.Direction.DESCENDING)
        }
    }

    private fun notEmptySchoolFieldBySearch(
        school: String?,
        fullName: String,
        collectionReference: CollectionReference,
    ): Query {
        return if (!school.isNullOrEmpty()) {
            collectionReference.whereEqualTo("branch", school)
                .orderBy("fullName", Query.Direction.DESCENDING)
                .whereGreaterThanOrEqualTo("fullName", fullName)
                .whereLessThanOrEqualTo("fullName", "$fullName\uf8ff")
        } else {
            collectionReference
                .orderBy("fullName", Query.Direction.DESCENDING)
                .whereGreaterThanOrEqualTo("fullName", fullName)
                .whereLessThanOrEqualTo("fullName", "$fullName\uf8ff")
        }
    }

    fun getListOfStudents(school: String?): Flow<PagingData<StudentsModel>> {
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
                    notEmptySchoolField(school, studentsCollection),
                    notEmptySchoolField(school, nextStudentsCollection)
                )
            }
        ).flow
    }

    fun searchStudents(fullName: String, school: String?): Flow<PagingData<StudentsModel>> {
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
                    notEmptySchoolFieldBySearch(school, fullName, queryStudentsCollection),
                    notEmptySchoolFieldBySearch(school, fullName, queryNextStudentsCollection)
                )
            }
        ).flow
    }
}
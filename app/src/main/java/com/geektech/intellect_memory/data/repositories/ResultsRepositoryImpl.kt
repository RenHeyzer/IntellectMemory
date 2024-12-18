package com.geektech.intellect_memory.data.repositories

import android.util.Log
import com.geektech.intellect_memory.common.base.BaseRepository
import com.geektech.intellect_memory.common.constants.Constants
import com.geektech.intellect_memory.data.local.sharedpreferences.PreferencesHelper
import com.geektech.intellect_memory.domain.repositories.ResultsRepository
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class ResultsRepositoryImpl @Inject constructor(
    val fireStore: FirebaseFirestore,
    private val preferences: PreferencesHelper
) : BaseRepository(), ResultsRepository {

    private val studentsCollection = fireStore.collection(Constants.COLLECTION_STUDENTS)

    override suspend fun passResults(points: Int) {
        var pointsField = getDocument(
            studentsCollection,
            preferences.userId
        ).getLong("points")

        pointsField = pointsField?.plus(points)
        Log.e("points", "$pointsField")
        pointsField?.let {
            updateDocument(
                studentsCollection, hashMapOf(
                    "points" to it,
                ), preferences.userId
            )
        }
    }
}
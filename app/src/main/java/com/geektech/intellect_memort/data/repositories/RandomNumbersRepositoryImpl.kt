package com.geektech.intellect_memort.data.repositories

import com.geektech.intellect_memort.common.base.BaseRepository
import com.geektech.intellect_memort.domain.repositories.RandomNumbersRepository
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class RandomNumbersRepositoryImpl @Inject constructor(
    fireStore: FirebaseFirestore,
) : BaseRepository(), RandomNumbersRepository {
    private val collectionReference = fireStore.collection("randomNumbers")

    override suspend fun addRandomNumbers(item: HashMap<String, Any>) = addDocument(
        collectionReference, item
    )
}
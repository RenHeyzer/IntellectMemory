package com.geektech.intellect_memort.data.repositories

import com.geektech.intellect_memort.common.base.BaseRepository
import com.geektech.intellect_memort.domain.repositories.RandomNumbersRepository
import com.geektech.intellect_memort.presentation.models.RandomNumbersModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class RandomNumbersRepositoryImpl @Inject constructor(
    fireStore: FirebaseFirestore,
) : BaseRepository(), RandomNumbersRepository {
    private val list = ArrayList<RandomNumbersModel>()

    private val collectionReference = fireStore.collection("numbers")

    override fun generateRandomNumbers() = doRequest {
        val randomList = fetchList<RandomNumbersModel>(collectionReference)
        list.addAll(randomList)
        return@doRequest list
    }

    override suspend fun uploadRandomNumbers(quantity: Int) {
        val random = List(quantity) { Random().nextInt(9) }
        val index = 0
        var id = 1
        random.forEach {
            id++
            addDocument(collectionReference,
                hashMapOf("numbers" to it, "row" to index),
                id.toString())
        }
    }

    override suspend fun deleteDocuments() {
        collectionReference.apply {
            val docList = get().await().documents
            docList.forEach {
                document(it.id).delete()
                list.clear()
            }
        }
    }
}

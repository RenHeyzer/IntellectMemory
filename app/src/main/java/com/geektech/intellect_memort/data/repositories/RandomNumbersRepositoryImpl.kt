package com.geektech.intellect_memort.data.repositories

import com.geektech.intellect_memort.common.base.BaseRepository
import com.geektech.intellect_memort.domain.repositories.RandomNumbersRepository
import com.geektech.intellect_memort.presentation.models.RandomNumbersModel
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import javax.inject.Inject
import kotlin.jvm.internal.Intrinsics

class RandomNumbersRepositoryImpl @Inject constructor(
    fireStore: FirebaseFirestore,
) : BaseRepository(), RandomNumbersRepository {
    val list = ArrayList<RandomNumbersModel>()

    private val collectionReference = fireStore.collection("randomNumbers")

    override  fun generateRandomNumbers(quantity: Int) = doRequest {
        val random = List(quantity) { Random().nextInt(9) }
        val index = 0
        random.forEach {
            list.addAll(listOf(RandomNumbersModel(it, index)))
        }
        return@doRequest list
    }
}

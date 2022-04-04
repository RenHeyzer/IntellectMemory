package com.geektech.intellect_memort.data.repositories

import android.util.Log
import androidx.annotation.WorkerThread
import com.geektech.intellect_memort.common.base.BaseRepository
import com.geektech.intellect_memort.common.constants.Constants
import com.geektech.intellect_memort.common.extension.correctNumber
import com.geektech.intellect_memort.data.local.room.daos.NumbersDao
import com.geektech.intellect_memort.domain.models.NumbersModel
import com.geektech.intellect_memort.domain.repositories.NumbersRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class NumbersRepositoryImpl @Inject constructor(
    val fireStore: FirebaseFirestore,
    private val dao: NumbersDao,
) : BaseRepository(), NumbersRepository {

    private val randomNumbersCollection =
        fireStore.collection(Constants.NUMBERS_COLLECTION_REFERENCE)
    private val binaryNumbersCollection =
        fireStore.collection(Constants.BINARY_NUMBERS_COLLECTION_REFERENCE)

    override fun generateRandomNumbers(quantitynumber: Int) = doRequest {
        val randomList: MutableList<NumbersModel> = ArrayList()
        fetchList<NumbersModel>(randomNumbersCollection).shuffled().forEach {
            if (randomList.size < quantitynumber) {
                randomList.add(it)
            }
        }
        Log.e("sizing", "repository: ${randomList.size}")
        return@doRequest randomList
    }

    override fun generateBinaryNumbers(quantitynumber: Int) = doRequest {
        val randomList: MutableList<NumbersModel> = ArrayList()
        fetchList<NumbersModel>(binaryNumbersCollection).shuffled().forEach {
            if (randomList.size <= quantitynumber) {
                randomList.add(it)
            }
        }
        return@doRequest randomList
    }

    override suspend fun uploadRandomNumbers(quantity: Int) {
        val random = List(quantity) { Random().nextInt(2) }
        random.forEach {
            val id: Int = Random().nextInt(9000 - 1000) + 1000
            addDocument(binaryNumbersCollection, hashMapOf("id" to id, "number" to it))
        }
    }

    override fun getAllRandomNumbers() = dao.getAllRandomNumbers()

    @WorkerThread
    override suspend fun insertAllRandomNumbers(numbers: List<NumbersModel>) =
        withContext(Dispatchers.IO) {
            dao.insertAllRandomNumbers(numbers)
        }

    @WorkerThread
    override suspend fun deleteAllRandomNumbers() = withContext(Dispatchers.IO) {
        dao.deleteAllRandomNumbers()
    }
}

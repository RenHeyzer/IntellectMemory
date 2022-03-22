package com.geektech.intellect_memort.data.repositories

import android.util.Log
import androidx.annotation.WorkerThread
import com.geektech.intellect_memort.common.base.BaseRepository
import com.geektech.intellect_memort.common.constants.Constants
import com.geektech.intellect_memort.data.local.room.daos.RandomNumbersDao
import com.geektech.intellect_memort.data.local.sharedpreferences.PreferencesHelper
import com.geektech.intellect_memort.domain.models.RandomNumbersModel
import com.geektech.intellect_memort.domain.repositories.RandomNumbersRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class RandomNumbersRepositoryImpl @Inject constructor(
    val fireStore: FirebaseFirestore,
    private val preferences: PreferencesHelper,
    private val dao: RandomNumbersDao,
) : BaseRepository(), RandomNumbersRepository {

    private val numbersCollection = fireStore.collection(Constants.NUMBERS_COLLECTION_REFERENCE)

    override fun generateRandomNumbers(quantitynumber: Int) = doRequest {
        val randomList: MutableList<RandomNumbersModel> = ArrayList()
        var order = 1
        fetchList<RandomNumbersModel>(numbersCollection).shuffled().forEach {
            if (randomList.size <= quantitynumber) {
                randomList.add(it)
                Log.e("ranom", "number: $it, ${order++}")
                Log.e("ranom", "randomList size: ${randomList.size}")
            }
        }
        return@doRequest randomList
    }

    override suspend fun uploadRandomNumbers(quantity: Int) {
        val random = List(quantity) { Random().nextInt(10) }
        random.forEach {
            val id: Int = Random().nextInt(9000 - 1000) + 1000
            addDocument(numbersCollection, hashMapOf("id" to id, "number" to it))
        }
    }

//    override suspend fun deleteDocuments() {
//        val batch = fireStore.batch()
//        numbersCollection.document(preferences.userId.toString())
//            .collection(Constants.CHILD_NUMBER_REFERENCE)
//            .get()
//            .await()
//            .documents
//            .forEach {
//                batch.delete(it.reference)
//            }
//        batch.commit()
//    }

    override fun getAllRandomNumbers() = dao.getAllRandomNumbers()

    @WorkerThread
    override suspend fun insertAllRandomNumbers(numbers: List<RandomNumbersModel>) =
        withContext(Dispatchers.IO) {
            dao.insertAllRandomNumbers(numbers)
        }

    @WorkerThread
    override suspend fun deleteAllRandomNumbers() = withContext(Dispatchers.IO) {
        dao.deleteAllRandomNumbers()
    }
}

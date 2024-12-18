package com.geektech.intellect_memory.common.base

import com.geektech.intellect_memory.common.resource.Resource
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

abstract class BaseRepository {

    protected fun <T> doRequest(request: suspend () -> T) = flow {
        emit(Resource.Loading())
        emit(Resource.Success(data = request()))
    }.catch { message ->
        emit(
            Resource.Error(
                data = null, message = message.localizedMessage ?: "Error Occurred!"
            )
        )
    }

    suspend inline fun <reified T> fetchList(
        collection: CollectionReference,
    ) = collection.get().await().documents.mapNotNull { doc ->
        doc.toObject(T::class.java)
    }

    suspend inline fun <reified T> fetchSortedList(
        orderCollection: Query,
    ) = orderCollection.get().await().documents.mapNotNull { query ->
        query.toObject(T::class.java)
    }


    suspend fun addDocument(
        collection: CollectionReference,
        hashMap: HashMap<String, Any>,
        title: String? = null,
    ): Boolean {
        return try {
            if (title != null) {
                collection
                    .document(title)
                    .set(hashMap)
                    .await()
            } else {
                collection
                    .document()
                    .set(hashMap)
                    .await()
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun updateDocument(
        collection: CollectionReference,
        hashMap: HashMap<String, Any>,
        title: String? = null,
    ): Boolean {
        return try {
            if (title != null) {
                collection
                    .document(title)
                    .update(hashMap)
                    .await()
            } else {
                collection
                    .document()
                    .update(hashMap)
                    .await()
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getDocument(collection: CollectionReference, id: String?): DocumentSnapshot =
        if (id != null) {
            collection
                .document(id)
                .get()
                .await()
        } else {
            collection
                .document()
                .get()
                .await()
        }

    suspend fun addChildDocument(
        mainCollection: CollectionReference,
        childCollection: String,
        hashMap: HashMap<String, Any>,
        id: String? = null,
    ): Boolean {
        return try {
            if (id != null) {
                mainCollection
                    .document(id)
                    .collection(childCollection)
                    .document()
                    .set(hashMap)
                    .await()
            } else {
                mainCollection
                    .document()
                    .collection(childCollection)
                    .document()
                    .set(hashMap)
                    .await()
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend inline fun <reified T> fetchListByQuery(collection: Query) =
        collection.get().await().documents.mapNotNull { doc ->
            doc.toObject(T::class.java)
        }
}
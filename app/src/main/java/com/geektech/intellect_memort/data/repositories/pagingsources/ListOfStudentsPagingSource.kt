package com.geektech.intellect_memort.data.repositories.pagingsources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.geektech.intellect_memort.domain.models.StudentsModel
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import java.io.IOException

class ListOfStudentsPagingSource(
    private val query: Query,
    private val nextQuery: Query,
) : PagingSource<QuerySnapshot, StudentsModel>() {

    override fun getRefreshKey(state: PagingState<QuerySnapshot, StudentsModel>): QuerySnapshot? {
        return null
    }

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, StudentsModel> {
        return try {
            val currentPage = params.key ?: query.limit(5).get().await()
            val lastVisibleMessage = currentPage.documents[currentPage.size() - 1]
            val nextPage = nextQuery.startAfter(lastVisibleMessage).limit(5).get().await()
            LoadResult.Page(
                data = currentPage.toObjects(StudentsModel::class.java),
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (http: HttpException) {
            LoadResult.Error(http)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}
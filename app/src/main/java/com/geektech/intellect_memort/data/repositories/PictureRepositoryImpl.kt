package com.geektech.intellect_memort.data.repositories

import com.geektech.intellect_memort.common.base.BaseRepository
import com.geektech.intellect_memort.common.utils.Constants
import com.geektech.intellect_memort.domain.models.PictureImageModel
import com.geektech.intellect_memort.domain.repositories.PictureRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import javax.inject.Inject

class PictureRepositoryImpl @Inject constructor(
    firebaseFirestore: FirebaseFirestore,
) : BaseRepository(), PictureRepository {

    private val imagesCollection =
        firebaseFirestore.collection(Constants.IMAGES_COLLECTION).orderBy(
            "id", Query.Direction.ASCENDING
        )

    override fun fetchImagesDefault() = doRequest {
        fetchSortedList<PictureImageModel>(imagesCollection)
    }
}




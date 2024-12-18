package com.geektech.intellect_memory.data.repositories

import com.geektech.intellect_memory.common.base.BaseRepository
import com.geektech.intellect_memory.common.utils.Constants
import com.geektech.intellect_memory.domain.models.PictureImageModel
import com.geektech.intellect_memory.domain.repositories.PictureRepository
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




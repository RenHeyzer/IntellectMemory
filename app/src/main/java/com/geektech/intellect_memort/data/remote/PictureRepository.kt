package com.geektech.intellect_memort.data.remote

import com.geektech.intellect_memort.common.base.BaseRepository
import com.geektech.intellect_memort.domain.model.PictureImageModel
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject

class PictureRepository @Inject constructor(
    fireStorage: FirebaseStorage,
) : BaseRepository() {

    private val images = fireStorage.reference




    fun fetchImagesDefault(): ArrayList<PictureImageModel> {
        val imagesList = ArrayList<PictureImageModel>()

        images.child("images").listAll().addOnSuccessListener { result ->
            result.items.forEach {
                it.downloadUrl.addOnSuccessListener { uri ->
                    let {
                        val fullLink =
                            "https://firebasestorage.googleapis.com${uri.encodedPath}?alt=media&token=${it}"
                        imagesList.add(PictureImageModel("name", fullLink))
                    }
                }
            }
        }
        return imagesList
    }
}




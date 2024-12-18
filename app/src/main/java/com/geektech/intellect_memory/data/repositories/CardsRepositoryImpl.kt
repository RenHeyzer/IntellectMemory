package com.geektech.intellect_memory.data.repositories


import com.geektech.intellect_memory.common.base.BaseRepository
import com.geektech.intellect_memory.common.constants.Constants
import com.geektech.intellect_memory.domain.models.CardsModel
import com.geektech.intellect_memory.domain.repositories.CardsRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import javax.inject.Inject


class CardsRepositoryImpl @Inject constructor(
    fireStore: FirebaseFirestore,
) : BaseRepository(), CardsRepository {
    private val imagesCollection =
        fireStore.collection(Constants.CARDS_COLLECTION)
    private val cardsCollection = fireStore.collection(Constants.CARDS_COLLECTION)
        .document("cards")

    override fun fetchImageOfCards(
        typeClover: String?,
        typeBrick: String?,
        typePiqui: String?,
        typeRedHeard: String?,
    ) = doRequest {
        fetchListByQuery<CardsModel>(imagesCollection.whereIn("type",
            listOf(typeClover, typeBrick, typePiqui, typeRedHeard)))
    }

    override fun fetchImageBySorted(
        typeClover: String?,
        typeBrick: String?,
        typePiqui: String?,
        typeRedHeard: String?,
    ) = doRequest {
        fetchListByQuery<CardsModel>(
            imagesCollection.whereIn("type",
                listOf(typeBrick, typeClover, typePiqui, typeRedHeard)
            ).orderBy("id", Query.Direction.ASCENDING)
        )
    }

}

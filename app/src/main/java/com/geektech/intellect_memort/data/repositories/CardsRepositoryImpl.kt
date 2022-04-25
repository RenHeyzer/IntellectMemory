package com.geektech.intellect_memort.data.repositories


import com.geektech.intellect_memort.common.base.BaseRepository
import com.geektech.intellect_memort.common.constants.Constants
import com.geektech.intellect_memort.domain.models.CardsModel
import com.geektech.intellect_memort.domain.repositories.CardsRepository
import com.google.firebase.firestore.FirebaseFirestore
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
}

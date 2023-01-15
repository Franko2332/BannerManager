package ru.kh.bannermanager.data.collectionsrepo

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.rxjava3.core.Single
import ru.kh.bannermanager.domain.entity.PromotionEntity
import ru.kh.bannermanager.domain.repo.Repo

class DocumentsRepo() : Repo {
    private val COLLECTION_NAME = "promotions"
    private var data: ArrayList<PromotionEntity> = ArrayList()

    override fun getObservableDocuments(): Single<ArrayList<PromotionEntity>> =
        Single.create { emitter ->
            FirebaseFirestore.getInstance().collection(COLLECTION_NAME).get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val ent: PromotionEntity = document.toObject(PromotionEntity::class.java)
                        data.add(ent)
                    }
                    emitter.onSuccess(data)
                }.addOnFailureListener { error -> Log.e("FIRESTORE LOAD ERROR", error.toString())
                    emitter.onError(error) }
        }

    override fun deletePromotion() {
    }

    override fun addDocument(document: PromotionEntity) {

    }
}
package ru.kh.bannermanager.data.collectionsrepo

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.kh.bannermanager.domain.entity.PromotionEntity
import ru.kh.bannermanager.domain.repo.Repo

class PromotionsRepo() : Repo {
    private val COLLECTION_NAME = "promotions"

    override fun getPromotions(): Single<ArrayList<PromotionEntity>> =
        Single.create { emitter ->
            FirebaseFirestore.getInstance().collection(COLLECTION_NAME).get()
                .addOnSuccessListener { result ->
                    val data: ArrayList<PromotionEntity> = ArrayList()
                    for (document in result) {
                        val ent: PromotionEntity = document.toObject(PromotionEntity::class.java)
                        data.add(ent)
                    }
                    emitter.onSuccess(data)
                }.addOnFailureListener { error ->
                    Log.e("FIRESTORE LOAD ERROR", error.toString())
                    emitter.onError(error)
                }
        }

    override fun deletePromotion(id: String) {
        FirebaseFirestore.getInstance().collection(COLLECTION_NAME).document(id.toString()).delete()
            .addOnSuccessListener {
                Log.v("FIRESTORE", "DELETE IS SUCCESS")
            }.addOnFailureListener { Log.e("ERROR", it.toString()) }
    }

    override fun addPromotion(document: PromotionEntity): Completable = Completable.create { emitter ->
        FirebaseFirestore.getInstance().collection(COLLECTION_NAME).document(document.title.toString())
            .set(document).addOnSuccessListener {
                emitter.onComplete()
            }.addOnFailureListener { emitter.onError(it) }
    }

}

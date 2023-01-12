package ru.kh.bannermanager.data.collectionsrepo

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import ru.kh.bannermanager.domain.entity.PromotionEntity
import ru.kh.bannermanager.domain.repo.Repo

class DocumentsRepo(private val firebaseFirestore: FirebaseFirestore) : Repo {
    private val COLLECTION_NAME = "promotions"
    private var data: ArrayList<PromotionEntity> = ArrayList()

    override fun getData(): ArrayList<PromotionEntity> {
            firebaseFirestore.collection(COLLECTION_NAME).get().addOnSuccessListener { result ->
                for (document in result) {
                    val ent: PromotionEntity = document.toObject(PromotionEntity::class.java)
                    data.add(ent)
                }
            }.addOnFailureListener { error -> Log.e("FIRESTORE LOAD ERROR", error.toString()) }
        Log.e("DATAA", data.size.toString())
        return data
    }

    override fun deletePromotion() {
    }

    override fun addDocument(document: PromotionEntity) {

    }
}
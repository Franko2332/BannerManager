package ru.kh.bannermanager.presentation.collection

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import ru.kh.bannermanager.domain.appstate.AppState
import ru.kh.bannermanager.domain.entity.PromotionEntity

class DocumentsViewModel : ViewModel() {
    private val appStateLiveData = MutableLiveData<AppState>()

    fun getData(): MutableLiveData<AppState> {
        appStateLiveData.postValue(AppState.Loading)
        val data = ArrayList<PromotionEntity>()
        FirebaseFirestore.getInstance().collection("promotions").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val ent: PromotionEntity = document.toObject(PromotionEntity::class.java)
                    data.add(ent)
                }
                appStateLiveData.postValue(AppState.Success(data))
            }.addOnFailureListener { error ->
                Log.e("FIRESTORE LOAD ERROR", error.toString())
                appStateLiveData.postValue(AppState.Error(error))
            }
        return appStateLiveData
    }

    fun addDocument(document: PromotionEntity){

    }
}
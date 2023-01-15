package ru.kh.bannermanager.presentation.adddocument

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import ru.kh.bannermanager.domain.appstate.AddDocumentAppState
import ru.kh.bannermanager.domain.appstate.DownloadDocumentsAppState
import ru.kh.bannermanager.domain.entity.PromotionEntity

class AddDocumentViewModel : ViewModel() {
    private val documentLiveData = MutableLiveData<DownloadDocumentsAppState>()

    fun addDocument(document: PromotionEntity) {
        documentLiveData.postValue(AddDocumentAppState.Loading)
        FirebaseFirestore.getInstance().collection("promotions").document().set(document)
            .addOnSuccessListener {
                documentLiveData.postValue(AddDocumentAppState.Success)
            }
            .addOnFailureListener { AddDocumentAppState.Error(error = it) }
    }
}
package ru.kh.bannermanager.presentation.collection

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject
import ru.kh.bannermanager.domain.appstate.DownloadDocumentsAppState
import ru.kh.bannermanager.domain.entity.PromotionEntity
import ru.kh.bannermanager.domain.repo.Repo
import java.lang.IllegalStateException

class DocumentsViewModel(private val repo: Repo) {

    private val appStateData: Observable<DownloadDocumentsAppState> = BehaviorSubject.create()

    fun getData(): Observable<DownloadDocumentsAppState> {

        appStateData.subject().onNext(DownloadDocumentsAppState.Loading)
        repo.getObservableDocuments().subscribeBy (onError = {appStateData.subject().
        onNext(DownloadDocumentsAppState.Error(it))},
            onSuccess = {appStateData.subject().onNext(DownloadDocumentsAppState.Success(it))})

        return appStateData
    }

    /*fun addDocument(document: PromotionEntity){
        appStateLiveData.postValue(DownloadDocumentsAppState.Loading)
        FirebaseFirestore.getInstance().collection("promotions").document().set(document).addOnFailureListener {

        }.addOnSuccessListener {  }
    }*/

    private fun <T> Observable<T>.subject(): Subject<T> = this as? Subject<T>
        ?: throw IllegalStateException("It is not Subject")
}
package ru.kh.bannermanager.presentation.promotions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject
import ru.kh.bannermanager.domain.appstate.DownloadPromotionsAppState
import ru.kh.bannermanager.domain.repo.Repo
import java.lang.IllegalStateException

class PromotionsViewModel(private val repo: Repo){

    private val promotionsLiveData = MutableLiveData<DownloadPromotionsAppState>()


    fun getData(): LiveData<DownloadPromotionsAppState> {
        promotionsLiveData.postValue(DownloadPromotionsAppState.Loading)
        repo.getPromotions().subscribeBy(
            onError = {promotionsLiveData.postValue(DownloadPromotionsAppState.Error(it))},
            onSuccess = {promotionsLiveData.postValue(DownloadPromotionsAppState.Success(it))})
        return promotionsLiveData
    }

    fun deleteDocument(id: String) {
        repo.deletePromotion(id)
    }


    private fun <T> Observable<T>.subject(): Subject<T> = this as? Subject<T>
        ?: throw IllegalStateException("It is not Subject")
}
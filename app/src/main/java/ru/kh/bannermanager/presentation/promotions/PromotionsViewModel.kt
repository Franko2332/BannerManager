package ru.kh.bannermanager.presentation.promotions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.kotlin.subscribeBy
import ru.kh.bannermanager.domain.appstate.DownloadPromotionsAppState
import ru.kh.bannermanager.domain.repo.Repo

class PromotionsViewModel(private val repo: Repo) {

    private val promotionsLiveData = MutableLiveData<DownloadPromotionsAppState>()


    fun getData(): LiveData<DownloadPromotionsAppState> {
        promotionsLiveData.postValue(DownloadPromotionsAppState.Loading)
        repo.getPromotions().subscribeBy(
            onError = { promotionsLiveData.postValue(DownloadPromotionsAppState.Error(it)) },
            onSuccess = { promotionsLiveData.postValue(DownloadPromotionsAppState.Success(it)) })
        return promotionsLiveData
    }

    fun deleteDocument(id: String): Completable = repo.deletePromotion(id)

}
package ru.kh.bannermanager.presentation.addpromotion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.kotlin.subscribeBy
import ru.kh.bannermanager.domain.appstate.AddPromotionAppState
import ru.kh.bannermanager.domain.entity.PromotionEntity
import ru.kh.bannermanager.domain.repo.Repo

class AddPromotionViewModel(private val repo: Repo) {
    private val addPromotionLiveData = MutableLiveData<AddPromotionAppState>()

    fun addPromotion(document: PromotionEntity): LiveData<AddPromotionAppState> {
        addPromotionLiveData.postValue(AddPromotionAppState.Loading)
        repo.addPromotion(document).subscribeBy(
            onComplete = { addPromotionLiveData.postValue(AddPromotionAppState.Success) },
            onError = {addPromotionLiveData.postValue(AddPromotionAppState.Error(it))}
        )
        return addPromotionLiveData
    }


}
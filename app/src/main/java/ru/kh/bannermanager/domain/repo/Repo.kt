package ru.kh.bannermanager.domain.repo

import io.reactivex.rxjava3.core.Single
import ru.kh.bannermanager.domain.entity.PromotionEntity

interface Repo {
    fun getObservableDocuments(): Single<ArrayList<PromotionEntity>>
    fun deletePromotion()
    fun addDocument(document: PromotionEntity)
}
package ru.kh.bannermanager.domain.repo

import ru.kh.bannermanager.domain.entity.PromotionEntity

interface Repo {
    fun getData(): ArrayList<PromotionEntity>
    fun deletePromotion()
    fun addDocument(document: PromotionEntity)
}